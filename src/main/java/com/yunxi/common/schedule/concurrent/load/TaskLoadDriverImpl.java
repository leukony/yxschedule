package com.yunxi.common.schedule.concurrent.load;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunxi.common.schedule.dispatch.TaskDispatcher;
import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务加载驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskLoadDriverImpl.java, v 0.1 2019年6月21日 下午1:37:02 leukony Exp $
 */
public class TaskLoadDriverImpl implements TaskLoadDriver {

    /** 日志 */
    private static final Log LOGGER = LogFactory.getLog(TaskLoadDriver.class);

    /** 定时任务分发器 */
    private TaskDispatcher   taskDispatcher;

    /** 
     * @see com.yunxi.common.schedule.concurrent.load.TaskLoadDriver#load(com.yunxi.common.schedule.model.Task, com.yunxi.common.schedule.model.TaskItem)
     */
    @Override
    public void load(Task task, TaskItem item) {
        Loader loader = task.getLoader();
        if (loader == null) {
            LOGGER.warn("任务加载:未配置加载器~" + task.getName());
            return;
        }

        List<String> businessKeys = null;
        try {
            businessKeys = loader.load(item);
        } catch (Exception e) {
            LOGGER.error("任务加载:任务加载异常~" + task.getName() + ",任务项~" + item, e);
            return;
        }

        int businessKeySize = businessKeys == null ? 0 : businessKeys.size();

        LOGGER.info("任务加载:加载完成~" + task.getName() + ",任务项~" + item + ",大小~" + businessKeySize);

        if (businessKeySize > 0) {
            for (String businessKey : businessKeys) {
                taskDispatcher.dispatchToExecuter(task.getName(), businessKey);
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