package com.ha.listenter;

import com.ha.base.AdminConstants;
import com.ha.config.EmailConfig;
import com.ha.config.HelianthusConfig;
import com.ha.mail.base.EmailHelper;
import com.ha.mail.service.IEmailSenderService;
import com.ha.quartz.domain.AbstractExecutableJob;
import com.ha.quartz.domain.ScheduleJobInfo;
import com.ha.quartz.domain.SimpleExecutableJob;
import com.ha.quartz.service.QuartzScheduleService;
import com.ha.system.domain.FileStore;
import com.ha.system.domain.FileType;
import com.ha.system.service.IFileStoreService;
import com.ha.util.FileUtil;
import org.simplejavamail.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.activation.FileDataSource;
import javax.mail.Message;
import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * application初始化执行器
 * User: shuiqing
 * DateTime: 17/3/27 下午3:10
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
@Order(value = AdminConstants.STARTUP_RUNNER_INIT)
public class HelianthusInitRunner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(HelianthusInitRunner.class);

    @Autowired
    private QuartzScheduleService quartzScheduleService;

    @Override
    public void run(String... args) throws Exception {

        LOG.info(">>>>>>>>>>>>>>>Init ->> 服务启动执行，执行初始化配置等操作--START <<<<<<<<<<<<<");

        LOG.info(">>>>>>>>>>>>>>>Init HelianthusConifg start<<<<<<<<<<<<<<<<<<<<<<<<");
        HelianthusConfig.loadConfig();
        LOG.info("HdfsAddr" + HelianthusConfig.getHdfsAddr());
        LOG.info(">>>>>>>>>>>>>>>Init HelianthusConifg end<<<<<<<<<<<<<<<<<<<<<<<<<<");

       /* ScheduleJobInfo scheduleJobInfo = new ScheduleJobInfo("data_import","dataWork","0/5 22 * * * ?");
        SimpleExecutableJob simpleExecutableJob = new SimpleExecutableJob(scheduleJobInfo);
        quartzScheduleService.addJob(simpleExecutableJob);*/

        /*quartzScheduleService.deleteJob("data_import","dataWork");*/

        /*List<ScheduleJobInfo> scheduleJobInfoList = quartzScheduleService.getScheduledJobList();
        for(ScheduleJobInfo sji:scheduleJobInfoList){
            System.out.println(sji.toString());
        }*/

        /*FileStore fileStore = new FileStore();
        fileStore.setFileType(FileType.IMAGE.getValue());
        fileStore.setUploadTime(Calendar.getInstance());
        fileStore.setCid("thumbsup");
        fileStore.setContent(FileUtil.file2byte("/Users/gemingming/Downloads/88E58PICixT_1024.jpg"));

        iFileStoreService.addOrUpdateFileStore(fileStore);*/

        LOG.info(">>>>>>>>>>>>>>>Init ->> 服务启动执行，执行初始化配置等操作--END <<<<<<<<<<<<<");
    }
}
