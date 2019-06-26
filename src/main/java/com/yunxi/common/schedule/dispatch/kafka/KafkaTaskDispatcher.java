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

/**
 * 基于kafka的定时任务分发器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaTaskDispatcher.java, v 0.1 2019年6月21日 下午8:23:42 leukony Exp $
 */
public class KafkaTaskDispatcher extends AbstractTaskDispatcher {

    /** Kafka消息队列 */
    private String                                     topic;

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
        sendMessage(datas);
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
        sendMessage(datas);
    }

    /**
     * 发送kafka消息
     * @param msgData
     */
    private void sendMessage(Map<Object, Object> msgData) {
        kafkaTemplate.send(topic, msgData);
    }

    /**
      * Setter method for property <tt>topic</tt>.
      * 
      * @param topic value to be assigned to property topic
      */
    public void setTopic(String topic) {
        this.topic = topic;
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