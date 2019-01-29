package cn.serializable;

public interface Serializer<T> {
    /**
     * The max buffer size for a {@link Serializer} to cached.
     */
    public static final int MAX_CACHED_BUF_SIZE = 256 * 1024;

    /**
     * The default buffer size for a {@link Serializer}.
     */
    public static final int DEFAULT_BUF_SIZE = 512;

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize
     * @return the equivalent binary data
     */
    <T> byte[] serialize(T t);

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    <T> T deserialize(byte[] bytes,Class<T> cls);
}
