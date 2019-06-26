package com.yunxi.common.schedule.dispatch.kafka;

import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_EXECUTE;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ACTION_LOAD;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_BUSSINESS_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_ITEM_KEY;
import static com.yunxi.common.schedule.TaskConstants.TASK_MSG_NAME_KEY;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;

import com.yunxi.common.schedule.dispatch.AbstractTaskDispatcher;
import com.yunxi.common.schedule.model.TaskItem;
import com.yunxi.common.schedule.model.TaskMessgae;

/**
 * 基于kafka的定时任务分发器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaTaskDispatcher.java, v 0.1 2019年6月21日 下午8:23:42 leukony Exp $
 */
public class KafkaTaskDispatcher extends AbstractTaskDispatcher {

    /** Kakfa消息模板 */
    private KafkaTemplate<String, Map<Object, Object>> kafkaTemplate;

    /** 
     * @see com.yunxi.common.schedule.dispatch.AbstractTaskDispatcher#doDispatchToSplitor(java.lang.String)
     */
    @Override
    public void doDispatchToSplitor(String taskName) {
        // ignore
    }

    /** 
     * @see com.yunxi.common.schedule.dispatch.AbstractTaskDispatcher#doDispatchToLoader(java.lang.String, com.yunxi.common.schedule.model.TaskItem)
     */
    @Override
    public void doDispatchToLoader(String taskName, TaskItem item) {
        Map<Object, Object> datas = new HashMap<Object, Object>();
        datas.put(TASK_MSG_ACTION_KEY, TASK_MSG_ACTION_LOAD);
        datas.put(TASK_MSG_NAME_KEY, taskName);
        datas.put(TASK_MSG_ITEM_KEY, item);

        TaskMessgae taskMsg = new TaskMessgae();
        taskMsg.setTopic(taskName);
        taskMsg.setData(datas);

        sendMessage(taskMsg);
    }

    /** 
     * @see com.yunxi.common.schedule.dispatch.AbstractTaskDispatcher#doDispatchToExecuter(java.lang.String, java.lang.String)
     */
    @Override
    public void doDispatchToExecuter(String taskName, String businessKey) {
        Map<Object, Object> datas = new HashMap<Object, Object>();
        datas.put(TASK_MSG_ACTION_KEY, TASK_MSG_ACTION_EXECUTE);
        datas.put(TASK_MSG_NAME_KEY, taskName);
        datas.put(TASK_MSG_BUSSINESS_KEY, businessKey);

        TaskMessgae taskMsg = new TaskMessgae();
        taskMsg.setTopic(taskName);
        taskMsg.setData(datas);

        sendMessage(taskMsg);
    }

    /**
     * 发送kafka消息
     * @param taskMsg
     */
    private void sendMessage(TaskMessgae taskMsg) {
        kafkaTemplate.send(taskMsg.getTopic(), taskMsg.getData());
    }

    /**
      * Setter method for property <tt>kafkaTemplate</tt>.
      * 
      * @param kafkaTemplate value to be assigned to property kafkaTemplate
      */
    public void setKafkaTemplate(KafkaTemplate<String, Map<Object, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}