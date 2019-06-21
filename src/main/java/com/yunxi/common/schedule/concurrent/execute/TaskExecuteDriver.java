package com.yunxi.common.schedule.concurrent.execute;

import com.yunxi.common.schedule.model.Task;

/**
 * 子任务执行驱动器
 * <p>分发第三层驱动:子任务业务逻辑执行</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskExecuteDriver.java, v 0.1 2019年6月21日 下午2:04:18 leukony Exp $
 */
public interface TaskExecuteDriver {

    /**
     * 子任务业务逻辑执行
     * 
     * @param task 任务
     * @param businessKey 子任务业务标识
     */
    void execute(Task task, String businessKey);
}