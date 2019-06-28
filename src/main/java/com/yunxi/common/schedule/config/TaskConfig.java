package com.yunxi.common.schedule.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yunxi.common.schedule.TaskManager;
import com.yunxi.common.schedule.concurrent.execute.Executer;
import com.yunxi.common.schedule.concurrent.load.Loader;
import com.yunxi.common.schedule.concurrent.split.Splitor;
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

        String taskNameS = getName();
        if (taskNameS == null) {
            throw new IllegalArgumentException("任务名称不能为空！");
        }
        task.setName(taskNameS);

        Splitor taskSplitorS = getSplitor();
        if (taskSplitorS == null) {
            throw new IllegalArgumentException("任务拆分器未配置！");
        }
        task.setSplitor(taskSplitorS);

        Loader taskLoaderS = getLoader();
        if (taskLoaderS == null) {
            throw new IllegalArgumentException("任务加载器未配置！");
        }
        task.setLoader(taskLoaderS);

        Executer taskExecuterS = getExecuter();
        if (taskExecuterS == null) {
            throw new IllegalArgumentException("任务执行器未配置！");
        }
        task.setExecuter(taskExecuterS);

        task.setAsync(isAsync());

        if (isAsync()) {
            ThreadPoolTaskExecutor taskThreadPoolS = getThreadPool();
            if (taskThreadPoolS == null) {
                throw new IllegalArgumentException("任务异步执行线程池未配置！");
            }
            task.setThreadPool(taskThreadPoolS);
        }

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