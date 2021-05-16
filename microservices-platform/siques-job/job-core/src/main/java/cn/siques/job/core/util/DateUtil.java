package cn.siques.job.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author : heshenghao
 * @date : 15:58 2021/5/16
 */
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected synchronized SimpleDateFormat initialValue() {
            //return super.initialValue();
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }

    public static Date parse(String textDate) throws ParseException {
        return threadLocal.get().parse(textDate);
    }
}