package com.yunxi.common.schedule.concurrent.split;

import com.yunxi.common.schedule.model.Task;

/**
 * 定时任务拆分驱动器
 * <p>分发第一层驱动:将主任务拆分成若干个子任务</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskSplitDriver.java, v 0.1 2019年6月21日 上午10:35:50 leukony Exp $
 */
public interface TaskSplitDriver {

    /**
     * 拆分定时任务
     * 
     * @param task 任务
     */
    void split(Task task);
}