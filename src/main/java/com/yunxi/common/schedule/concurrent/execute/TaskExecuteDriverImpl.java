package com.yunxi.common.schedule.concurrent.execute;

import com.yunxi.common.schedule.model.Task;

/**
 * 子任务执行驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskExecuteDriverImpl.java, v 0.1 2019年6月21日 下午2:04:32 leukony Exp $
 */
public class TaskExecuteDriverImpl implements TaskExecuteDriver {

    /** 
     * @see com.yunxi.common.schedule.concurrent.execute.TaskExecuteDriver#execute(com.alipay.sofa.platform.schedule.model.Task, java.lang.String)
     */
    @Override
    public void execute(Task task, final String businessKey) {
        final Executer executer = task.getExecuter();
        if (executer == null) {
            // TODO LOG
            return;
        }

        try {
            if (task.isAsync()) {
                // 异步执行
                if (task.getThreadPool() == null) {
                    // TODO LOG 
                    return;
                }
                task.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            executer.execute(businessKey);
                        } catch (Exception e) {
                            // TODO LOG
                        }
                    }
                });
            } else {
                // 同步执行
                executer.execute(businessKey);
            }
        } catch (Exception e) {
            // TODO LOG
        }
    }
}