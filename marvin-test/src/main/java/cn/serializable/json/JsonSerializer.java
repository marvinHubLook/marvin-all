package cn.serializable.json;

import cn.serializable.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class JsonSerializer<T> implements Serializer<T> {
    /***
     *
     * public static final String toJSONString(Object object)
     * public static final T parseObject(String text, Class clazz);
     */
    @Override
    public <T> byte[] serialize(T t) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes=new byte[0];
        try {
           bytes = objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> cls) {
        ObjectMapper objectMapper = new ObjectMapper();
        T t=null;
        try {
            t=objectMapper.readValue(bytes,cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
