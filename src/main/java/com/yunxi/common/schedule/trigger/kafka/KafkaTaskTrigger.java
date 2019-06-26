package com.yunxi.common.schedule.trigger.kafka;

import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_EXECUTE;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_LOAD;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_SPLIT;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_BUSSINESS_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ITEM_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_NAME_KEY;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.yunxi.common.schedule.TaskManager;
import com.yunxi.common.schedule.model.TaskItem;
import com.yunxi.common.schedule.trigger.TaskTrigger;

/**
 * 基于kafka的定时任务触发器.
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaTaskTrigger.java, v 0.1 2019年6月20日 下午1:56:08 leukony Exp $
 */
public class KafkaTaskTrigger implements TaskTrigger, MessageListener<String, Map<Object, Object>> {

    /** 定时任务服务  */
    private TaskManager taskManager;

    /** 
     * @see com.yunxi.common.schedule.trigger.TaskTrigger#registerTask(java.lang.String, java.lang.String)
     */
    @Override
    public void registerTask(String groupId, String taskName) {
        // ignore
    }

    /** 
     * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
     */
    @Override
    public void onMessage(ConsumerRecord<String, Map<Object, Object>> data) {
        Map<Object, Object> taskMsg = data.value();

        String action = (String) taskMsg.get(TASK_MSG_ACTION_KEY);
        String taskName = (String) taskMsg.get(TASK_MSG_NAME_KEY);

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
                taskManager.doExecute(taskName, businessKey);
                break;
            default:
                taskManager.doSplit(taskName);
                break;
        }
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