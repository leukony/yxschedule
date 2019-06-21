package com.yunxi.common.schedule.concurrent.load;

import java.util.List;

import com.yunxi.common.schedule.model.TaskItem;

/**
 * 子任务加载
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Loader.java, v 0.1 2019年6月21日 下午1:38:27 leukony Exp $
 */
public interface Loader {

    /**
     * 子任务数据加载
     * 
     * @param item 子任务数据
     * @return
     */
    List<String> load(TaskItem item);
}