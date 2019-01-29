package cn.serializable;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *  SPI 方式调用
 */
public class RunSeriaizer {
    public static void main(String[] args) {
        ServiceLoader<Serializer> serializers = ServiceLoader.load(Serializer.class);
        Iterator<Serializer> iterator = serializers.iterator();
        System.out.println("classPath:"+System.getProperty("java.class.path"));
        while(iterator.hasNext()){
            Serializer<String> serializer = iterator.next();

            byte[] bytes = serializer.serialize("序列化");
            System.out.println("args = [" + bytes.length + "]");
            String s = serializer.deserialize(bytes, String.class);
            System.out.println("["+s+"]");
        }
    }


}
