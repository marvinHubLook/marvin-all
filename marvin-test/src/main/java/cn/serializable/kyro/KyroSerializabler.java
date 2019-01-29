package cn.serializable.kyro;

import cn.serializable.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KyroSerializabler<T> implements Serializer<T> {

    @Override
    public <T> byte[] serialize(T t) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(t.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, t);
        output.flush();
        output.close();
        byte[] bt = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> cls) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(cls, new JavaSerializer());
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        T t = (T) kryo.readClassAndObject(input);
        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
