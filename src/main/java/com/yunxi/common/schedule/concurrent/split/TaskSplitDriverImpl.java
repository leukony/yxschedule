package com.yunxi.common.schedule.concurrent.split;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunxi.common.schedule.dispatch.TaskDispatcher;
import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务拆分驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskSplitDriverImpl.java, v 0.1 2019年6月21日 上午10:41:29 leukony Exp $
 */
public class TaskSplitDriverImpl implements TaskSplitDriver {

    /** 日志 */
    private static final Log LOGGER = LogFactory.getLog(TaskSplitDriver.class);

    /** 定时任务分发器 */
    private TaskDispatcher   taskDispatcher;

    /** 
     * @see com.yunxi.common.schedule.concurrent.split.TaskSplitDriver#split(com.alipay.sofa.platform.schedule.model.Task)
     */
    @Override
    public void split(Task task) {
        Splitor splitor = task.getSplitor();
        if (splitor == null) {
            LOGGER.warn("任务拆分: 未配置拆分器~" + task.getName());
            return;
        }

        List<TaskItem> taskItemList = null;
        try {
            taskItemList = splitor.split();
        } catch (Exception e) {
            LOGGER.error("任务拆分: 任务拆分异常~" + task.getName(), e);
            return;
        }

        int taskItemSize = taskItemList == null ? 0 : taskItemList.size();

        LOGGER.info("任务拆分: 拆分完成~" + task.getName() + ",大小~" + taskItemSize);

        if (taskItemSize > 0) {
            for (TaskItem item : taskItemList) {
                taskDispatcher.dispatchToLoader(task.getName(), item);
            }
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