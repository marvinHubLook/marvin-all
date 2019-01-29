package cn.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/6/22 9:20
 **/
public class DateFormatTest {
    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("--before--"+Thread.currentThread().getName());
                    this.join(2000);
                    System.out.println("--after--"+Thread.currentThread().getName());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    System.out.println(this.getName() + ":" + DateUtils.parse("2018-06-20 01:18:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new TestSimpleDateFormatThreadSafe().start();
        }
    }


    static class DateUtils {
        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static String formatDate(Date date) throws ParseException {
            return sdf.format(date);
        }

        public static Date parse(String strDate) throws ParseException {
            return sdf.parse(strDate);
        }
    }
}
