package com.yunxi.common.schedule.dispatch.kafka;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * 基于kafka的对象序列化
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: KafkaObjectSerializer.java, v 0.1 2019年6月26日 下午2:21:03 leukony Exp $
 */
public class KafkaObjectSerializer implements Serializer<Object> {

    /** 
     * @see org.apache.kafka.common.serialization.Serializer#configure(java.util.Map, boolean)
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /** 
     * @see org.apache.kafka.common.serialization.Serializer#serialize(java.lang.String, java.lang.Object)
     */
    @Override
    public byte[] serialize(String topic, Object data) {
        if (data == null) {
            throw new SerializationException("Error when serializing null to byte[]");
        }

        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing object to byte[]", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /** 
     * @see org.apache.kafka.common.serialization.Serializer#close()
     */
    @Override
    public void close() {
    }
}