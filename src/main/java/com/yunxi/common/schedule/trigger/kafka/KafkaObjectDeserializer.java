package com.yunxi.common.schedule.trigger.kafka;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * 基于kafka的对象反序列化
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaObjectDeserializer.java, v 0.1 2019年6月26日 下午2:26:24 leukony Exp $
 */
public class KafkaObjectDeserializer implements Deserializer<Object> {

    /** 
     * @see org.apache.kafka.common.serialization.Deserializer#configure(java.util.Map, boolean)
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /** 
     * @see org.apache.kafka.common.serialization.Deserializer#deserialize(java.lang.String, byte[])
     */
    @Override
    public Object deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to object", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /** 
     * @see org.apache.kafka.common.serialization.Deserializer#close()
     */
    @Override
    public void close() {
    }
}