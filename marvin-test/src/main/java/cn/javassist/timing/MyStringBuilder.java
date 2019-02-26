package cn.javassist.timing;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-13 16:53
 **/
public class MyStringBuilder {
    /**
     * 糟糕的设计，只是举个例子
     * @param length
     * @return
     */
    private String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }
    public static void main(String[] argv) {
        MyStringBuilder inst = new MyStringBuilder();
        for (int i = 0; i < argv.length; i++) {
            String result = inst.buildString(Integer.parseInt(argv[i]));
            System.out.println("Constructed string of length " +
                    result.length());
        }
    }
}
