package com.yunxi.common.schedule.concurrent.load;

import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务加载驱动器
 * <p>分发第二层驱动:拆分的子任务数据加载</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskSplitDriver.java, v 0.1 2019年6月21日 上午10:35:50 leukony Exp $
 */
public interface TaskLoadDriver {

    /**
     * 子任务数据加载
     * 
     * @param task 任务
     * @param item 子任务数据
     */
    void load(Task task, TaskItem item);
}