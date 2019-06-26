package com.yunxi.common.schedule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.yunxi.common.schedule.concurrent.execute.TaskExecuteDriver;
import com.yunxi.common.schedule.concurrent.load.TaskLoadDriver;
import com.yunxi.common.schedule.concurrent.split.TaskSplitDriver;
import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务服务
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskManager.java, v 0.1 2019年6月24日 下午2:22:46 leukony Exp $
 */
public class TaskManager {

    /** 定时任务集合 */
    private final Map<String, Task> tasks = new ConcurrentHashMap<String, Task>();

    /** 定时任务拆分驱动器 */
    private TaskSplitDriver         taskSplitDriver;

    /** 定时任务加载驱动器 */
    private TaskLoadDriver          taskLoadDriver;

    /** 子任务执行驱动器 */
    private TaskExecuteDriver       taskExecuteDriver;

    /**
     * 注册定时任务
     * 
     * @param task 定时任务
     */
    public void register(Task task) {
        tasks.put(task.getName(), task);
    }

    /**
     * 分发到第一层: 定时任务拆分
     * 
     * @param taskName 任务名称
     */
    public void doSplit(String taskName) {
        Task task = getTaskByName(taskName);
        if (task != null) {
            taskSplitDriver.split(task);
        }
    }

    /**
     * 分发到第二层: 子任务载入
     * 
     * @param taskName 任务名称
     * @param item 子任务加载项
     */
    public void doLoad(String taskName, TaskItem item) {
        Task task = getTaskByName(taskName);
        if (task != null) {
            taskLoadDriver.load(task, item);
        }
    }

    /**
     * 分发到第三层: 子任务执行
     * 
     * @param taskName 任务名称
     * @param businessKey 子任务业务标识
     */
    public void doExecute(String taskName, String businessKey) {
        Task task = getTaskByName(taskName);
        if (task != null) {
            taskExecuteDriver.execute(task, businessKey);
        }
    }

    /**
     * 获取任务详情
     * 
     * @param taskName 任务名称
     * @return
     */
    public Task getTaskByName(String taskName) {
        if (StringUtils.isNotBlank(taskName)) {
            Task task = tasks.get(taskName);
            if (task == null) {
                // TODO LOG
            }
            return task;
        }
        return null;
    }

    /**
      * Setter method for property <tt>taskSplitDriver</tt>.
      * 
      * @param taskSplitDriver value to be assigned to property taskSplitDriver
      */
    public void setTaskSplitDriver(TaskSplitDriver taskSplitDriver) {
        this.taskSplitDriver = taskSplitDriver;
    }

    /**
      * Setter method for property <tt>taskLoadDriver</tt>.
      * 
      * @param taskLoadDriver value to be assigned to property taskLoadDriver
      */
    public void setTaskLoadDriver(TaskLoadDriver taskLoadDriver) {
        this.taskLoadDriver = taskLoadDriver;
    }

    /**
      * Setter method for property <tt>taskExecuteDriver</tt>.
      * 
      * @param taskExecuteDriver value to be assigned to property taskExecuteDriver
      */
    public void setTaskExecuteDriver(TaskExecuteDriver taskExecuteDriver) {
        this.taskExecuteDriver = taskExecuteDriver;
    }
}