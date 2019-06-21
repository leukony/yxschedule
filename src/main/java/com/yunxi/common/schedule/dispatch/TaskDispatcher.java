package com.yunxi.common.schedule.dispatch;

import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务分发器
 * <p>定时任务被触发后会分三层分发(触发 => 拆分 -> 载入 -> 执行), 该接口负责将每一层的子任务分发到下一层</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskDispatcher.java, v 0.1 2019年6月21日 上午11:15:16 leukony Exp $
 */
public interface TaskDispatcher {

    /**
     * 分发到第一层: 定时任务拆分
     * 
     * @param taskName 任务名称
     */
    void dispatchToSplitor(String taskName);

    /**
     * 分发到第二层: 子任务加载
     * 
     * @param taskName 任务名称
     * @param item 子任务加载项
     */
    void dispatchToLoader(String taskName, TaskItem item);

    /**
     * 分发到第三层: 子任务执行
     * 
     * @param taskName 任务名称
     * @param businessKey 子任务业务标识
     */
    void dispatchToExecuter(String taskName, String businessKey);
}