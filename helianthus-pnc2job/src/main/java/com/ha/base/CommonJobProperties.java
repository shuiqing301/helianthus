package com.ha.base;

/**
 * User: shuiqing
 * DateTime: 17/6/27 下午5:25
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class CommonJobProperties {

    /**
     * The executing job id
     */
    public static final String JOB_ID = "import.job.id";

    /**
     * The executing flow id
     */
    public static final String FLOW_ID = "import.flow.flowid";

    /**
     * The numerical project id identifier.
     */
    public static final String PROJECT_NAME = "import.flow.projectname";

    /**
     * The following are Common properties that can be set in a job file
     */

    /**
     * The type of job that will be executed. Examples: command, java, etc.
     */
    public static final String JOB_TYPE = "type";

    /**
     * Force a node to be a root node in a flow, even if there are other jobs
     * dependent on it.
     */
    public static final String ROOT_NODE = "root.node";

    /**
     * Comma delimited list of job names which are dependencies
     */
    public static final String DEPENDENCIES = "dependencies";

    /**
     * The version of the project the flow is running. This may change if a forced
     * hotspot occurs.
     */
    public static final String PROJECT_VERSION = "import.flow.projectversion";

    /**
     * The nested flow id path
     */
    public static final String NESTED_FLOW_PATH = "import.flow.nested.path";

    /**
     * The split by node depends
     */
    public static final String NODE_DEPNEDS_SPLIT = ";";

}
