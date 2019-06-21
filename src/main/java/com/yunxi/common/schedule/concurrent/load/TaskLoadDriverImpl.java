package com.yunxi.common.schedule.concurrent.load;

import java.util.List;

import com.yunxi.common.schedule.dispatch.TaskDispatcher;
import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务加载驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskLoadDriverImpl.java, v 0.1 2019年6月21日 下午1:37:02 leukony Exp $
 */
public class TaskLoadDriverImpl implements TaskLoadDriver {

    /** 定时任务分发器 */
    private TaskDispatcher taskDispatcher;

    /** 
     * @see com.yunxi.common.schedule.concurrent.load.TaskLoadDriver#load(com.yunxi.common.schedule.model.Task, com.yunxi.common.schedule.model.TaskItem)
     */
    @Override
    public void load(Task task, TaskItem item) {
        Loader loader = task.getLoader();
        if (loader == null) {
            // TODO LOG
            return;
        }

        List<String> businessKeys = null;
        try {
            businessKeys = loader.load(item);
        } catch (Exception e) {
            // TODO LOG
            return;
        }

        if (businessKeys == null || businessKeys.isEmpty()) {
            // TODO LOG
            return;
        }

        for (String businessKey : businessKeys) {
            dispatchToExecuter(task.getName(), businessKey);
        }
    }

    /***
     * 分发子任务加载的数据去执行
     * @param taskName
     * @param businessKey
     */
    private void dispatchToExecuter(String taskName, String businessKey) {
        try {
            taskDispatcher.dispatchToExecuter(taskName, businessKey);
        } catch (Exception e) {
            // TODO LOG
        }
    }

    /**
      * Setter method for property <tt>taskDispatcher</tt>.
      * 
      * @param taskDispatcher value to be assigned to property taskDispatcher
      */
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
    }
}