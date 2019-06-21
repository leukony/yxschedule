package com.yunxi.common.schedule.concurrent.split;

import java.util.List;

import com.yunxi.common.schedule.dispatch.TaskDispatcher;
import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务拆分驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskSplitDriverImpl.java, v 0.1 2019年6月21日 上午10:41:29 leukony Exp $
 */
public class TaskSplitDriverImpl implements TaskSplitDriver {

    /** 定时任务分发器 */
    private TaskDispatcher taskDispatcher;

    /** 
     * @see com.yunxi.common.schedule.concurrent.split.TaskSplitDriver#split(com.alipay.sofa.platform.schedule.model.Task)
     */
    @Override
    public void split(Task task) {
        Splitor splitor = task.getSplitor();
        if (splitor == null) {
            // TODO LOG
            return;
        }

        List<TaskItem> taskItemList = null;
        try {
            taskItemList = splitor.split();
        } catch (Exception e) {
            // TODO LOG
            return;
        }

        if (taskItemList == null || taskItemList.isEmpty()) {
            // TODO LOG
            return;
        }

        for (TaskItem item : taskItemList) {
            dispatchToLoader(task.getName(), item);
        }
    }

    /***
     * 分发定时任务拆分出子任务
     * @param taskName
     * @param item
     */
    private void dispatchToLoader(String taskName, TaskItem item) {
        try {
            taskDispatcher.dispatchToLoader(taskName, item);
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