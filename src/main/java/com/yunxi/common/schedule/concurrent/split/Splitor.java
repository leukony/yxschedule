package com.yunxi.common.schedule.concurrent.split;

import java.util.List;

import com.yunxi.common.schedule.model.TaskItem;

/**
 * 定时任务拆分
 * <p>分发第一层:将主任务拆分成若干个子任务</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Splitor.java, v 0.1 2019年6月21日 上午10:44:05 leukony Exp $
 */
public interface Splitor {

    /**
     * 拆分定时任务
     * 
     * @return
     */
    List<TaskItem> split();
}