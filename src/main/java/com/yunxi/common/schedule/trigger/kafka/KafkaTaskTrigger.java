package com.yunxi.common.schedule.trigger.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.yunxi.common.schedule.trigger.TaskTrigger;

/**
 * 基于kafka的定时任务触发器.
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaTaskTrigger.java, v 0.1 2019年6月20日 下午1:56:08 leukony Exp $
 */
public class KafkaTaskTrigger implements TaskTrigger, MessageListener<String, String> {

    /** 
     * @see com.yunxi.common.schedule.trigger.TaskTrigger#registerTask(java.lang.String, java.lang.String)
     */
    @Override
    public void registerTask(String groupId, String taskName) {
    }

    /** 
     * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
    }
}