package cn.serializable;

import java.util.ServiceLoader;

public class SerializerHolder {
    private static final  ServiceLoader<Serializer> serializers = ServiceLoader.load(Serializer.class);

    public static Serializer serializerImpl() {
        return serializers.iterator().next();
    }
}
