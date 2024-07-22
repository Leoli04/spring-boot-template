package com.leoli04.springboottemplate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日期处理工具类
 * @Author: LeoLi04
 * @CreateDate: 2024/7/22 6:57
 * @Version: 1.0
 */
public class DateUtil {

    /**
     * The constant YYYYMM.
     */
    public final static String YYYYMM = "yyyyMM";
    /**
     * The constant YYYYMMDD.
     */
    public final static String YYYYMMDD = "yyyyMMdd";
    /**
     * The constant YYYYMMDD2.
     */
    public final static String YYYYMMDD2 = "yyyy/MM/dd";
    /**
     * The constant YYYYMMDD3.
     */
    public final static String YYYYMMDD3 = "yyyy-MM-dd";
    /**
     * The constant YYYYMMDD4.
     */
    public final static String YYYYMMDD4 = "yyyy年M月d日";
    /**
     * The constant MMDD.
     */
    public final static String MMDD = "MM/dd";
    /**
     * The constant HHMM.
     */
    public final static String HHMM = "HH:mm";
    /**
     * The constant HHMMSS.
     */
    public final static String HHMMSS = "HH:mm:ss";
    /**
     * The constant HHMMSSSSS.
     */
    public final static String HHMMSSSSS = "HH:mm:ss.SSS";
    /**
     * The constant HHMMSS2.
     */
    public final static String HHMMSS2 = "hh:mm:ss a";
    /**
     * The constant HHMMSSSSS2.
     */
    public final static String HHMMSSSSS2 = "hh:mm:ss.SSS a";
    /**
     * The constant YYYYMMDDHH.
     */
    public final static String YYYYMMDDHH = "yyyyMMdd HH";
    /**
     * The constant YYYYMMDDHHMM.
     */
    public final static String YYYYMMDDHHMM = "yyyyMMdd HH:mm";
    /**
     * The constant YYYYMMDDHHMM2.
     */
    public final static String YYYYMMDDHHMM2 = "yyyy/MM/dd HH:mm";
    /**
     * The constant YYYYMMDDHHMM3.
     */
    public final static String YYYYMMDDHHMM3 = "yyyyMMddHHmm";
    /**
     * The constant YYYYMMDDHHMM4.
     */
    public final static String YYYYMMDDHHMM4 = "yyyy年M月d日 HH:mm";
    /**
     * The constant YYYYMMDDHHMMSS.
     */
    public final static String YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS2.
     */
    public final static String YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS3.
     */
    public final static String YYYYMMDDHHMMSS3 = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS4.
     */
    public final static String YYYYMMDDHHMMSS4 = "yyyyMMddHHmmss";
    /**
     * The constant YYYYMMDDHHMMSSMS.
     */
    public final static String YYYYMMDDHHMMSSMS = "yyyyMMdd HH:mm:ss.SSS";
    /**
     * The constant YYYYMMDDHHMMSSMS2.
     */
    public final static String YYYYMMDDHHMMSSMS2 = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * The constant ONE_DATE_MI.
     */
    public static int ONE_DATE_MI = 1000 * 60 * 60 * 24;
    /**
     * The constant ONE_YEAR.
     */
    public static int ONE_YEAR = 365;

    private static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 格式化类型
     * @return 格式化字符串 string
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return "";
        }

        SimpleDateFormat simpleDateFordmat = new SimpleDateFormat(pattern);
        return simpleDateFordmat.format(date);
    }
}
