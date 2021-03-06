package com.yunxi.common.schedule.dispatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务分发器基类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AbstractTaskDispatcher.java, v 0.1 2019年6月24日 上午9:49:04 leukony Exp $
 */
public abstract class AbstractTaskDispatcher implements TaskDispatcher {

    /** 日志 */
    protected static final Log LOGGER = LogFactory.getLog(TaskDispatcher.class);

    /** 
     * @see com.yunxi.common.schedule.dispatch.TaskDispatcher#dispatchToSplitor(java.lang.String)
     */
    @Override
    public void dispatchToSplitor(String taskName) {
        try {
            doDispatchToSplitor(taskName);
        } catch (Exception e) {
            LOGGER.error("任务分发: 分发任务拆分异常~" + taskName, e);
        }
    }

    /** 
     * @see com.yunxi.common.schedule.dispatch.TaskDispatcher#dispatchToLoader(java.lang.String, com.yunxi.common.schedule.model.TaskItem)
     */
    @Override
    public void dispatchToLoader(String taskName, TaskItem item) {
        try {
            doDispatchToLoader(taskName, item);
        } catch (Exception e) {
            LOGGER.error("任务分发: 分发任务加载异常~" + taskName + ",任务项~" + item, e);
        }
    }

    /** 
     * @see com.yunxi.common.schedule.dispatch.TaskDispatcher#dispatchToExecuter(java.lang.String, java.lang.String)
     */
    @Override
    public void dispatchToExecuter(String taskName, String businessKey) {
        try {
            doDispatchToExecuter(taskName, businessKey);
        } catch (Exception e) {
            LOGGER.error("任务分发: 分发任务执行异常~" + taskName + ",标识~" + businessKey, e);
        }
    }

    /**
     * 定时任务拆分
     * 
     * @param taskName 任务名称
     */
    public abstract void doDispatchToSplitor(String taskName);

    /**
     * 子任务加载
     * 
     * @param taskName 任务名称
     * @param item 子任务加载项
     */
    public abstract void doDispatchToLoader(String taskName, TaskItem item);

    /**
     * 子任务执行
     * 
     * @param taskName 任务名称
     * @param businessKey 子任务业务标识
     */
    public abstract void doDispatchToExecuter(String taskName, String businessKey);
}