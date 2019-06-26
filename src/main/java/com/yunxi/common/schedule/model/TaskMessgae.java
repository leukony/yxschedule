package com.yunxi.common.schedule.model;

import java.util.Map;

/**
 * 定时任务消息
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TaskMessgae.java, v 0.1 2019年6月24日 下午1:47:12 leukony Exp $
 */
public class TaskMessgae {

    /** 消息队列  */
    private String              topic;

    /** 消息参数 */
    private Map<Object, Object> data;

    /**
      * Getter method for property <tt>topic</tt>.
      * 
      * @return property value of topic
      */
    public String getTopic() {
        return topic;
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
      * Getter method for property <tt>data</tt>.
      * 
      * @return property value of data
      */
    public Map<Object, Object> getData() {
        return data;
    }

    /**
      * Setter method for property <tt>data</tt>.
      * 
      * @param data value to be assigned to property data
      */
    public void setData(Map<Object, Object> data) {
        this.data = data;
    }
}