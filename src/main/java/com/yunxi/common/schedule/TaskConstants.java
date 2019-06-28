package com.yunxi.common.schedule;

/**
 * 定时任务常量
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskConstants.java, v 0.1 2019年6月24日 下午2:02:00 leukony Exp $
 */
public class TaskConstants {

    /** 定时任务消息Tracer的KEY */
    public static final String TASK_MSG_TRACER_KEY     = "TASK_TRACER";

    /** 定时任务消息动作的KEY */
    public static final String TASK_MSG_ACTION_KEY     = "TASK_ACTION";

    /** 定时任务消息名称的KEY */
    public static final String TASK_MSG_NAME_KEY       = "TASK_NAME";

    /** 定时任务消息任务加载项的KEY */
    public static final String TASK_MSG_ITEM_KEY       = "TASK_ITEM";

    /** 定时任务消息任务唯一标识的KEY */
    public static final String TASK_MSG_BUSSINESS_KEY  = "TASK_KEY";

    /** 定时任务Split消息动作 */
    public static final String TASK_MSG_ACTION_SPLIT   = "SPLIT";

    /** 定时任务Load消息动作 */
    public static final String TASK_MSG_ACTION_LOAD    = "LOAD";

    /** 定时任务Execute消息动作 */
    public static final String TASK_MSG_ACTION_EXECUTE = "EXECUTE";
}