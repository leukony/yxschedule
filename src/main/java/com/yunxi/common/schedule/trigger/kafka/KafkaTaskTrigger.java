package com.yunxi.common.schedule.trigger.kafka;

import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_EXECUTE;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_LOAD;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_SPLIT;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_BUSSINESS_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ITEM_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_NAME_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_TRACER_KEY;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.yunxi.common.lang.enums.RpcCode;
import com.yunxi.common.schedule.TaskManager;
import com.yunxi.common.schedule.model.TaskItem;
import com.yunxi.common.schedule.trigger.TaskTrigger;
import com.yunxi.common.tracer.TracerFactory;
import com.yunxi.common.tracer.constants.TracerConstants;
import com.yunxi.common.tracer.context.SchedulerContext;
import com.yunxi.common.tracer.tracer.SchedulerTracer;

/**
 * 基于kafka的定时任务触发器.
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaTaskTrigger.java, v 0.1 2019年6月20日 下午1:56:08 leukony Exp $
 */
public class KafkaTaskTrigger implements TaskTrigger, MessageListener<String, Map<Object, Object>> {

    /** 日志 */
    protected static final Log LOGGER = LogFactory.getLog(TaskTrigger.class);

    /** 应用名 */
    private String             appName;

    /** 定时任务服务  */
    private TaskManager        taskManager;

    /** 
     * @see com.yunxi.common.schedule.trigger.TaskTrigger#registerTask(java.lang.String, java.lang.String[])
     */
    @Override
    public void registerTask(String groupId, String[] taskNames) {
        // ignore
    }

    /** 
     * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(ConsumerRecord<String, Map<Object, Object>> data) {
        Map<Object, Object> taskMsg = data.value();

        String action = (String) taskMsg.get(TASK_MSG_ACTION_KEY);
        String taskName = (String) taskMsg.get(TASK_MSG_NAME_KEY);

        Map<String, String> tracerContext = null;
        Object tracerContextObj = taskMsg.get(TASK_MSG_TRACER_KEY);
        if (tracerContextObj != null) {
            tracerContext = (Map<String, String>) tracerContextObj;
        }

        boolean hasException = false;
        SchedulerTracer schedulerTracer = null;
        try {
            // 1、从工厂中获取HttpTracer
            schedulerTracer = TracerFactory.getSchedulerReceiveTracer();

            // 2、将请求中的Tracer参数设置到上下文中
            if (tracerContext != null) {
                String traceId = tracerContext.get(TracerConstants.TRACE_ID);
                String rpcId = tracerContext.get(TracerConstants.RPC_ID);
                if (traceId != null && rpcId != null) {
                    schedulerTracer.setContext(tracerContext);
                }
            }

            // 3、开始处理调度,调用startProcess
            SchedulerContext schedulerContext = schedulerTracer.startProcess();

            // 4、获取请求参数并设置到Tracer上下文中
            if (schedulerContext != null) {
                schedulerContext.setCurrentApp(appName);
                schedulerContext.setTaskName(taskName);
                schedulerContext.setTaskAction(action);
            }

            // 5、进行处理调度,对分发任务进行处理
            switch (action) {
                case TASK_MSG_ACTION_SPLIT:
                    taskManager.doSplit(taskName);
                    break;
                case TASK_MSG_ACTION_LOAD:
                    TaskItem item = (TaskItem) taskMsg.get(TASK_MSG_ITEM_KEY);
                    taskManager.doLoad(taskName, item);
                    break;
                case TASK_MSG_ACTION_EXECUTE:
                    String businessKey = (String) taskMsg.get(TASK_MSG_BUSSINESS_KEY);
                    schedulerContext.setBusinessKey(businessKey);
                    taskManager.doExecute(taskName, businessKey);
                    break;
                default:
                    taskManager.doSplit(taskName);
                    break;
            }
        } catch (Exception e) {
            hasException = true;
            LOGGER.error("任务触发: 处理异常~" + taskName + ",动作~" + action, e);
        } finally {
            // 6、结束处理调度，打印Trace日志
            if (schedulerTracer != null) {
                if (hasException) {
                    schedulerTracer.finishProcess(RpcCode.RPC_BIZ_FAILED.getCode());
                } else {
                    schedulerTracer.finishProcess(RpcCode.RPC_SUCCESS.getCode());
                }
            }
        }
    }

    /**
      * Setter method for property <tt>appName</tt>.
      * 
      * @param appName value to be assigned to property appName
      */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
      * Setter method for property <tt>taskManager</tt>.
      * 
      * @param taskManager value to be assigned to property taskManager
      */
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
}