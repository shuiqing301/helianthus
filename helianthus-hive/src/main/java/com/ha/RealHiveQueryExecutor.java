package com.ha;

import com.ha.exception.HiveQueryExecutionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.cli.CliDriver;
import org.apache.hadoop.hive.cli.CliSessionState;
import org.apache.hadoop.hive.cli.OptionsProcessor;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.shims.ShimLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintStream;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVEAUXJARS;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTORECONNECTURLKEY;

/**
 * User: shuiqing
 * DateTime: 17/8/15 上午11:14
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class RealHiveQueryExecutor implements HiveQueryExecutor {
    private final static Logger LOG = LoggerFactory
            .getLogger(RealHiveQueryExecutor.class);
    private final CliDriver cli;
    private final CliSessionState ss;

    public RealHiveQueryExecutor(HiveConf hiveConf, CliSessionState ss,
                                 CliDriver cli) throws Exception {
        LOG.info("HiveConf = " + hiveConf);
        LOG.info("According to the conf, we're talking to the Hive hosted at: "
                + HiveConf.getVar(hiveConf, METASTORECONNECTURLKEY));

        // Expand out the hive aux jars since there was no shell script to do it
        // for us
        String orig = HiveConf.getVar(hiveConf, HIVEAUXJARS);
        String expanded = HiveUtils.expandHiveAuxJarsPath(orig);
        if (orig == null || orig.equals(expanded)) {
            LOG.info("Hive aux jars variable not expanded");
        } else {
            LOG.info("Expanded aux jars variable from [" + orig + "] to [" + expanded
                    + "]");
            HiveConf.setVar(hiveConf, HIVEAUXJARS, expanded);
        }

        OptionsProcessor op = new OptionsProcessor();

        if (!op.process_stage1(new String[] {})) {
            throw new IllegalArgumentException("Can't process empty args?!?");
        }

        if (!ShimLoader.getHadoopShims().usesJobShell()) {
            // hadoop-20 and above - we need to augment classpath using hiveconf
            // components
            // see also: code in ExecDriver.java
            ClassLoader loader = hiveConf.getClassLoader();
            String auxJars = HiveConf.getVar(hiveConf, HiveConf.ConfVars.HIVEAUXJARS);
            LOG.info("Got auxJars = " + auxJars);

            if (StringUtils.isNotBlank(auxJars)) {
                loader =
                        Utilities.addToClassPath(loader, StringUtils.split(auxJars, ","));
            }
            hiveConf.setClassLoader(loader);
            Thread.currentThread().setContextClassLoader(loader);
        }

        this.ss = ss;
        LOG.info("SessionState = " + ss);
        ss.out = System.out;
        ss.err = System.err;
        ss.in = System.in;

        if (!op.process_stage2(ss)) {
            throw new IllegalArgumentException(
                    "Can't process arguments from session state");
        }
        this.cli = cli;
        LOG.info("Cli = " + cli);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void executeQuery(String q) throws HiveQueryExecutionException {
        LOG.info("Executing query: " + q);

        int returnCode = cli.processLine(q);
        if (returnCode != 0) {
            LOG.warn("Got exception " + returnCode + " from line: " + q);
            throw new HiveQueryExecutionException(returnCode, q);
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public void setOut(PrintStream out) {
        ss.out = out;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setIn(InputStream in) {
        ss.in = in;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setErr(PrintStream err) {
        ss.err = err;
    }
}
