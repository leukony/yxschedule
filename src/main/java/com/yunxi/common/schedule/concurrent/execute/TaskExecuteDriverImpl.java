package com.yunxi.common.schedule.concurrent.execute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunxi.common.schedule.model.Task;
import com.yunxi.common.tracer.concurrent.TracerRunnable;

/**
 * 子任务执行驱动器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskExecuteDriverImpl.java, v 0.1 2019年6月21日 下午2:04:32 leukony Exp $
 */
public class TaskExecuteDriverImpl implements TaskExecuteDriver {

    /** 日志 */
    private static final Log LOGGER = LogFactory.getLog(TaskExecuteDriver.class);

    /** 
     * @see com.yunxi.common.schedule.concurrent.execute.TaskExecuteDriver#execute(com.alipay.sofa.platform.schedule.model.Task, java.lang.String)
     */
    @Override
    public void execute(Task task, final String businessKey) {
        final Executer executer = task.getExecuter();
        if (executer == null) {
            LOGGER.warn("任务执行:未配置执行器~" + task.getName());
            return;
        }

        try {
            if (task.isAsync()) {
                // 异步执行
                if (task.getThreadPool() == null) {
                    LOGGER.warn("任务执行:异步任务未配置线程池~" + task.getName());
                    return;
                }
                task.getThreadPool().execute(new TracerRunnable() {

                    @Override
                    public void doRun() {
                        try {
                            executer.execute(businessKey);
                            LOGGER.info("任务执行:任务执行完成,标识~" + businessKey);
                        } catch (Exception e) {
                            LOGGER.error("任务执行:任务执行异常,标识~" + businessKey, e);
                        }
                    }
                });
            } else {
                // 同步执行
                executer.execute(businessKey);
                LOGGER.info("任务执行:任务执行完成,标识~" + businessKey);
            }
        } catch (Exception e) {
            LOGGER.error("任务执行:任务执行异常,标识~" + businessKey, e);
        }
    }
}