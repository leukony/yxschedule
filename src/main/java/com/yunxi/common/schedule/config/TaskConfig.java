package com.yunxi.common.schedule.config;

import org.springframework.beans.factory.InitializingBean;

import com.yunxi.common.schedule.TaskManager;
import com.yunxi.common.schedule.model.Task;

/**
 * 定时任务配置
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskConfig.java, v 0.1 2019年6月26日 下午4:30:25 leukony Exp $
 */
public class TaskConfig extends Task implements InitializingBean {

    /** 定时任务服务  */
    private TaskManager taskManager;

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Task task = new Task();
        task.setName(getName());
        task.setSplitor(getSplitor());
        task.setLoader(getLoader());
        task.setExecuter(getExecuter());
        task.setAsync(isAsync());
        task.setThreadPool(getThreadPool());
        taskManager.register(task);
    }

    /**
      * Setter method for property <tt>taskManager</tt>.
      * 
      * @param taskManager value to be assigned to property taskManager
      */
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
}