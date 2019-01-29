package cn.lang.unsafe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sun.misc.Unsafe;

/**
 * {@link UnsafeUtils} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see UnsafeUtilTest
 * @since 1.0.0
 */
public class UnsafeUtilTest {


    private Model model;
    @Before
    public void setUp() throws Exception {
        model = new Model();
        System.out.println("---longValue--"+UnsafeUtils.LONG_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "longValue"));
        System.out.println("---intValue--"+UnsafeUtils.INT_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "intValue"));
        System.out.println("---shortValue--"+UnsafeUtils.SHORT_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "shortValue"));
        System.out.println("---byteValue--"+UnsafeUtils.BYTE_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "byteValue"));
        System.out.println("---booleanValue--"+UnsafeUtils.BOOLEAN_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "booleanValue"));
        System.out.println("---floatValue--"+UnsafeUtils.FLOAT_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "floatValue"));
        System.out.println("---doubleValue--"+UnsafeUtils.DOUBLE_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "doubleValue"));
        System.out.println("---charValue--"+UnsafeUtils.CHAR_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "charValue"));
        System.out.println("---stringValue--"+UnsafeUtils.OBJECT_ARRAY_INDEX_SCALE+"--"+UnsafeUtils.getObjectFieldOffset(model, "stringValue"));
        System.out.println();
        System.out.println("---longArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "longArrayValue"));
        System.out.println("---intArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "intArrayValue"));
        System.out.println("---shortArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "shortArrayValue"));
        System.out.println("---byteArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "byteArrayValue"));
        System.out.println("---booleanArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "booleanArrayValue"));
        System.out.println("---doubleArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "doubleArrayValue"));
        System.out.println("---floatArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "floatArrayValue"));
        System.out.println("---charArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "charArrayValue"));
        System.out.println("---objectArrayValue----"+UnsafeUtils.getObjectFieldOffset(model, "objectArrayValue"));
        
    }
    /**
     * arrayBaseOffset : 数组第一个元素的偏移地址
     * arrayIndexScale : 数组中元素的增量地址
     */
    @Test
    public void testStaticInit() throws Exception {
        Assert.assertNotNull(UnsafeUtils.unsafe);
        Unsafe unsafe = UnsafeUtils.unsafe;
        Assert.assertEquals(UnsafeUtils.LONG_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(long[].class));
        Assert.assertEquals(UnsafeUtils.INT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(int[].class));
        Assert.assertEquals(UnsafeUtils.SHORT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(short[].class));
        Assert.assertEquals(UnsafeUtils.BYTE_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(byte[].class));
        Assert.assertEquals(UnsafeUtils.BOOLEAN_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(boolean[].class));
        Assert.assertEquals(UnsafeUtils.DOUBLE_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(double[].class));
        Assert.assertEquals(UnsafeUtils.FLOAT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(float[].class));
        Assert.assertEquals(UnsafeUtils.CHAR_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(char[].class));
        Assert.assertEquals(UnsafeUtils.OBJECT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(Object[].class));


        Assert.assertEquals(UnsafeUtils.LONG_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(long[].class));
        Assert.assertEquals(UnsafeUtils.INT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(int[].class));
        Assert.assertEquals(UnsafeUtils.SHORT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(short[].class));
        Assert.assertEquals(UnsafeUtils.BYTE_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(byte[].class));
        Assert.assertEquals(UnsafeUtils.BOOLEAN_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(boolean[].class));
        Assert.assertEquals(UnsafeUtils.DOUBLE_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(double[].class));
        Assert.assertEquals(UnsafeUtils.FLOAT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(float[].class));
        Assert.assertEquals(UnsafeUtils.CHAR_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(char[].class));
        Assert.assertEquals(UnsafeUtils.OBJECT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(Object[].class));
        
      /*  System.out.println("---LONG_ARRAY_INDEX_SCALE---"+UnsafeUtils.LONG_ARRAY_INDEX_SCALE);
        System.out.println("---INT_ARRAY_INDEX_SCALE---"+UnsafeUtils.INT_ARRAY_INDEX_SCALE);
        System.out.println("---SHORT_ARRAY_INDEX_SCALE---"+UnsafeUtils.SHORT_ARRAY_INDEX_SCALE);
        System.out.println("---BYTE_ARRAY_INDEX_SCALE---"+UnsafeUtils.BYTE_ARRAY_INDEX_SCALE);
        System.out.println("---BOOLEAN_ARRAY_INDEX_SCALE---"+UnsafeUtils.BOOLEAN_ARRAY_INDEX_SCALE);
        System.out.println("---DOUBLE_ARRAY_INDEX_SCALE---"+UnsafeUtils.DOUBLE_ARRAY_INDEX_SCALE);
        System.out.println("---FLOAT_ARRAY_INDEX_SCALE---"+UnsafeUtils.FLOAT_ARRAY_INDEX_SCALE);
        System.out.println("---CHAR_ARRAY_INDEX_SCALE---"+UnsafeUtils.CHAR_ARRAY_INDEX_SCALE);
        System.out.println("---OBJECT_ARRAY_INDEX_SCALE---"+UnsafeUtils.OBJECT_ARRAY_INDEX_SCALE);*/
        

    }
    
    @Test
    public void testPutLongAndGetLong() throws Exception {
        String fieldName = "longValue";
        long value = Long.MAX_VALUE;
        UnsafeUtils.putLong(model, fieldName, value);
        long returnValue = UnsafeUtils.getLong(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);

        value = Long.MIN_VALUE;
        UnsafeUtils.putLongVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getLongVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);

        value = Long.MAX_VALUE;
        UnsafeUtils.putOrderedLong(model, fieldName, value);
        returnValue = UnsafeUtils.getLongVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);
    }
    @Test
    public void testPutIntAndGetInt() throws Exception {
        String fieldName = "intValue";
        int value = 123;
        UnsafeUtils.putInt(model, fieldName, value);
        int returnValue = UnsafeUtils.getInt(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtils.putIntVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getIntVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);

        value = Integer.MIN_VALUE;
        UnsafeUtils.putOrderedInt(model, fieldName, value);
        returnValue = UnsafeUtils.getIntVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);
    }
    @Test
    public void testPutShortAndGetShort() throws Exception {
        String fieldName = "shortValue";
        short value = Short.MAX_VALUE;
        UnsafeUtils.putShort(model, fieldName, value);
        short returnValue = UnsafeUtils.getShort(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortValue, returnValue);

        UnsafeUtils.putShortVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getShortVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortValue, returnValue);
    }
    @Test
    public void testPutByteAndGetByte() throws Exception {
        String fieldName = "byteValue";
        byte value = Byte.MAX_VALUE;
        UnsafeUtils.putByte(model, fieldName, value);
        byte returnValue = UnsafeUtils.getByte(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteValue, returnValue);

        UnsafeUtils.putByteVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getByteVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteValue, returnValue);
    }
    @Test
    public void testPutBooleanAndGetBoolean() throws Exception {
        String fieldName = "booleanValue";
        boolean value = Boolean.TRUE;
        UnsafeUtils.putBoolean(model, fieldName, value);
        boolean returnValue = UnsafeUtils.getBoolean(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanValue, returnValue);

        UnsafeUtils.putBooleanVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getBooleanVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanValue, returnValue);
    }
    @Test
    public void testPutDoubleAndGetDouble() throws Exception {
        String fieldName = "doubleValue";
        double value = Double.MAX_VALUE;
        UnsafeUtils.putDouble(model, fieldName, value);
        double returnValue = UnsafeUtils.getDouble(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleValue, returnValue);

        UnsafeUtils.putDoubleVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getDoubleVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleValue, returnValue);
    }
    @Test
    public void testPutFloatAndGetFloat() throws Exception {
        String fieldName = "floatValue";
        float value = Float.MAX_VALUE;
        UnsafeUtils.putFloat(model, fieldName, value);
        float returnValue = UnsafeUtils.getFloat(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatValue, returnValue);

        UnsafeUtils.putFloatVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getFloatVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatValue, returnValue);
    }
    @Test
    public void testPutCharAndGetChar() throws Exception {
        String fieldName = "charValue";
        char value = '@';
        UnsafeUtils.putChar(model, fieldName, value);
        char returnValue = UnsafeUtils.getChar(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charValue, returnValue);

        UnsafeUtils.putCharVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getCharVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charValue, returnValue);

    }
    @Test
    public void testPutObjectAndGetObject() throws Exception {
        String fieldName = "stringValue";
        Object value = "Test text";
        UnsafeUtils.putObject(model, fieldName, value);
        Object returnValue = UnsafeUtils.getObject(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);

        value = "AAAAAAAAAAAA";
        UnsafeUtils.putObjectVolatile(model, fieldName, value);
        returnValue = UnsafeUtils.getObjectVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);

        value = "BBBBBB";
        UnsafeUtils.putOrderedObject(model, fieldName, value);
        returnValue = UnsafeUtils.getObject(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);
    }
    @Test
    public void testPutLongIntoArrayVolatileAndGetLongFromArrayVolatile() throws Exception {
        String fieldName = "longArrayValue";
        long value = 123;
        int index = 2;
        UnsafeUtils.putLongIntoArrayVolatile(model, fieldName, index, value);
        long returnValue = UnsafeUtils.getLongFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longArrayValue[index], returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtils.putOrderedLongIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtils.getLongFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longArrayValue[index], returnValue);
    }
    @Test
    public void testPutIntIntoArrayVolatileAndGetIntFromArrayVolatile() throws Exception {
        String fieldName = "intArrayValue";
        int value = 123;
        int index = 1;
        UnsafeUtils.putIntIntoArrayVolatile(model, fieldName, index, value);
        int returnValue = UnsafeUtils.getIntFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intArrayValue[index], returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtils.putOrderedIntIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtils.getIntFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intArrayValue[index], returnValue);
    }
    @Test
    public void testPutShortIntoArrayVolatileAndGetShortFromArrayVolatile() throws Exception {
        String fieldName = "shortArrayValue";
        short value = 123;
        int index = 5;
        UnsafeUtils.putShortIntoArrayVolatile(model, fieldName, index, value);
        short returnValue = UnsafeUtils.getShortFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortArrayValue[index], returnValue);
    }
    @Test
    public void testPutByteIntoArrayVolatileAndGetByteFromArrayVolatile() throws Exception {
        String fieldName = "byteArrayValue";
        byte value = 123;
        int index = 5;
        UnsafeUtils.putByteIntoArrayVolatile(model, fieldName, index, value);
        byte returnValue = UnsafeUtils.getByteFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteArrayValue[index], returnValue);
    }
    @Test
    public void testPutBooleanIntoArrayVolatileAndGetBooleanFromArrayVolatile() throws Exception {
        String fieldName = "booleanArrayValue";
        boolean value = Boolean.TRUE;
        int index = 3;
        UnsafeUtils.putBooleanIntoArrayVolatile(model, fieldName, index, value);
        boolean returnValue = UnsafeUtils.getBooleanFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanArrayValue[index], returnValue);
    }
    @Test
    public void testPutDoubleIntoArrayVolatileAndGetDoubleFromArrayVolatile() throws Exception {
        String fieldName = "doubleArrayValue";
        double value = Double.MAX_VALUE;
        int index = 8;
        UnsafeUtils.putDoubleIntoArrayVolatile(model, fieldName, index, value);
        double returnValue = UnsafeUtils.getDoubleFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleArrayValue[index], returnValue);
    }
    @Test
    public void testPutFloatIntoArrayVolatileAndGetFloatFromArrayVolatile() throws Exception {
        String fieldName = "floatArrayValue";
        float value = Float.MAX_VALUE;
        int index = 7;
        UnsafeUtils.putFloatIntoArrayVolatile(model, fieldName, index, value);
        float returnValue = UnsafeUtils.getFloatFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatArrayValue[index], returnValue);
    }
    @Test
    public void testPutCharIntoArrayVolatileAndGetCharFromArrayVolatile() throws Exception {
        String fieldName = "charArrayValue";
        char value = '@';
        int index = 9;
        UnsafeUtils.putCharIntoArrayVolatile(model, fieldName, index, value);
        char returnValue = UnsafeUtils.getCharFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charArrayValue[index], returnValue);
    }
    @Test
    public void testPutObjectIntoArrayVolatileAndGetObjectFromArrayVolatile() throws Exception {
        String fieldName = "objectArrayValue";
        Object value = "Test";
        int index = 5;
        UnsafeUtils.putObjectIntoArrayVolatile(model, fieldName, index, value);
        Object returnValue = UnsafeUtils.getObjectFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.objectArrayValue[index], returnValue);

        UnsafeUtils.putOrderedObjectIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtils.getObjectFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.objectArrayValue[index], returnValue);

    }

    /**
     * 测试当存储更高范围类型的值，然后获取
     */
    @Test
    public void tesPutOnInvalidTypeValue() throws Exception {
        String fieldName = "intValue";
        int value = Integer.MAX_VALUE;
        int index = 1;
        IllegalArgumentException exception;

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtils.putLong(model, fieldName, value);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtils.putShort(model, fieldName, (short) 1);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtils.putChar(model, fieldName, (char) 1);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);


    }

    @Test
    public void testOnObjectIsNull() throws Exception {
        String fieldName = "aaaa";
        Object value = "value";

        NullPointerException exception = null;
        try {
            UnsafeUtils.putObject(null, fieldName, value);
        } catch (NullPointerException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }
    @Test
    public void testOnIllegalArgumentException() throws Exception {
        String fieldName = "aaaa";
        Object value = "value";

        NullPointerException exception = null;
        try {
            UnsafeUtils.putObject(model, fieldName, value);
        } catch (NullPointerException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    private static class Model {
        private long longValue;
        private int intValue;
        private short shortValue;
        private byte byteValue;
        private boolean booleanValue;
        private float floatValue;
        private double doubleValue;
        private char charValue;
        private String stringValue;
        private long[] longArrayValue = new long[10];
        private int[] intArrayValue = new int[10];
        private short[] shortArrayValue = new short[10];
        private byte[] byteArrayValue = new byte[10];
        private boolean[] booleanArrayValue = new boolean[10];
        private double[] doubleArrayValue = new double[10];
        private float[] floatArrayValue = new float[10];
        private char[] charArrayValue = new char[10];
        private Object[] objectArrayValue = new Object[10];
    }

}
