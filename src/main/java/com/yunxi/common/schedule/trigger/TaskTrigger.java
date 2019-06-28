package com.yunxi.common.schedule.trigger;

/**
 * 定时任务触发器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskTrigger.java, v 0.1 2019年6月20日 下午1:52:20 leukony Exp $
 */
public interface TaskTrigger {

    /**
     * 注册定时任务
     * @param groupId  系统集群名称
     * @param taskNames 定时任务名称
     */
    void registerTask(String groupId, String[] taskNames);
}