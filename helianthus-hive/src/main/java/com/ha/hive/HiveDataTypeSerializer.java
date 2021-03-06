package com.ha.hive;

import com.google.common.collect.Maps;
import com.ha.hive.datatype.*;
import com.ha.util.BytesSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * hive 数据类型序列化工具
 * User: shuiqing
 * DateTime: 17/6/15 下午4:49
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public abstract class HiveDataTypeSerializer<T> implements BytesSerializer<T>, java.io.Serializable {

    final static Map<String, Class<?>> implementations = Maps.newHashMap();
    protected transient ThreadLocal current = new ThreadLocal();
    static {
        implementations.put("char", StringSerializer.class);
        implementations.put("varchar", StringSerializer.class);
        implementations.put("decimal", BigDecimalSerializer.class);
        implementations.put("double", DoubleSerializer.class);
        implementations.put("float", DoubleSerializer.class);
        implementations.put("bigint", LongSerializer.class);
        implementations.put("long", LongSerializer.class);
        implementations.put("integer", LongSerializer.class);
        implementations.put("int", LongSerializer.class);
        implementations.put("tinyint", LongSerializer.class);
        implementations.put("smallint", LongSerializer.class);
        implementations.put("int4", Int4Serializer.class);
        implementations.put("long8", Long8Serializer.class);
        implementations.put("boolean", BooleanSerializer.class);
        implementations.put("date", DateTimeSerializer.class);
        implementations.put("datetime", DateTimeSerializer.class);
        implementations.put("timestamp", DateTimeSerializer.class);
    }

    public static void register(String dataTypeName, Class<? extends HiveDataTypeSerializer<?>> impl) {
        implementations.put(dataTypeName, impl);
    }

    public static HiveDataTypeSerializer<?> create(String dataType) {
        return create(HiveDataType.getType(dataType));
    }

    public static HiveDataTypeSerializer<?> create(HiveDataType type) {
        Class<?> clz = implementations.get(type.getName());
        if (clz == null)
            throw new RuntimeException("No DataTypeSerializer for type " + type);

        try {
            return (HiveDataTypeSerializer<?>) clz.getConstructor(HiveDataType.class).newInstance(type);
        } catch (Exception e) {
            throw new RuntimeException(e); // never happen
        }
    }

    /** Peek into buffer and return the length of serialization which is previously written by this.serialize().
     *  The current position of input buffer is guaranteed to be at the beginning of the serialization.
     *  The implementation must not alter the buffer position by its return. */
    abstract public int peekLength(ByteBuffer in);

    /** Return the max number of bytes to the longest possible serialization */
    abstract public int maxLength();

    /** Get an estimate of the average size in bytes of this kind of serialized data */
    abstract public int getStorageBytesEstimate();

    /** An optional convenient method that converts a string to this data type (for dimensions) */
    public T valueOf(String str) {
        throw new UnsupportedOperationException();
    }

    /** Convert from obj to string */
    public String toString(T value) {
        if (value == null)
            return "NULL";
        else
            return value.toString();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        current = new ThreadLocal();
    }
}
