package com.gs.common.util.format.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间操作工具类
 * Created by gs
 * on 2018/10/1.
 */
public class DateUtil {

    /**
     * 获取当前的时间戳
     * @return
     */
    public static Timestamp currentTimestamp () {

        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取指定格式时间
     * @return
     */
    public static String getFormatTime(String reg){
        SimpleDateFormat sdf = new SimpleDateFormat(reg);
        return sdf.format(new Date());
    }

    /**
     * 将字符串转换为时间 字符串格式为 2017-10-01
     * @param s
     * @return
     */
    public static Date getDateFromString(String s){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parse = null;
        try {
             parse = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 根据传入日期 返回格式为 yyyy-MM-dd HH:mm:ss 的时间
     * @param date
     * @return
     */
    public static String getFormatTimeFromDate(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  simpleDateFormat.format(date);
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取每月的开始时间
     * @param date
     * @return
     */
    private static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }

    /**
     * 根据传入日期来获取一个月的开始时间  返回string
     * @param date
     * @return
     */
    public static String getMonthStartStr(Date date){
        return sdf.format(getMonthStart(date));
    }

    /**
     * 根据传入时间获取一个月月末时间  返回string
     * @param date
     * @return
     */
    public static String getMonthEndStr(Date date){
        return sdf.format(getMonthEnd(date));
    }
    private static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }

    /**
     * 根据传入时间获取与  当前时间差  返回毫秒值
     * @param date
     * @return
     */
    public static Long getCurTimes(Date date) {
        long diff = date.getTime() - new Date().getTime();//返回相差的毫秒数
       // long days = diff / (1000 * 60 * 60 * 24);//返回相差的天数
        return diff;
    }

    /**
     * 获取下一天
     * @param date
     * @return
     */
    private static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 将字符串转化为日期
     * @param time
     * @return
     */
    public static Date paraseStringToDate(String time ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date=null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //日期转字符串
    public static String paraseDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(date == null){
            return "";
        }
        String string = sdf.format(date);
        return string;
    }


    //根据传入的日期获取所在月份所有日期
    public static List<String> getAllDaysMonthByDate(Date d){
        List<String> lst=new ArrayList<String>();
        Date date = getMonthStart(d);
        Date monthEnd = getMonthEnd(d);
        while (!date.after(monthEnd)) {

            lst.add(sdf.format(date));
            date = getNext(date);
        }
        return lst;
    }
    //根据传入的日期获取所在月份所有日期   没有参数
    public static List<String>getAllDaysMonth(){
        List<String> lst=new ArrayList();
        Date d = new Date();

        Date date = getMonthStart(d);
        Date monthEnd = getMonthEnd(d);
        while (!date.after(monthEnd)) {
            lst.add(sdf.format(date));
            date = getNext(date);
        }
        return lst;
    }


    //将日期进行加减运算, int n
    public static String addDay(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            //cd.add(Calendar.DATE, n);//增加一天
            cd.add(Calendar.MONTH, 1);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    //获取当天开始时间00.00
    public static Date startTime(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    //获取今天最后一秒 23:59:59
    public static Date endTime(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    /**
     * 获取当日24点 到 当前时间的 时间差（秒）
     * @return
     */
    public static int getTimeStampToMidNight(){
        //获取当前时间 (秒)
        int now_time = (int)(System.currentTimeMillis()/1000);
        //获取当日24点时间（秒）
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,24);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
         int to_24 = (int) (calendar.getTimeInMillis()/1000);
         return to_24 - now_time;
    }

    /**
     * 此函数实现：给定日期和经过天数，算出结果日期
     * 其中sDate为指定日期，iDate为多少时间段（可以是 年、月、日...  具体根据iCal来确定）
     * iCal为某种时间段例如  月：Calendar.MONTH（具体可查询api中Calendar类）
     * sStr为日期格式 例如："yyyyMMdd"（具体可查询api中Calendar类）
     * @param sDate
     * @param iDate
     * @param iCal
     * @param sStr
     * @return
     */
    public String getNextDate(String sDate, int iDate,int iCal, String sStr){
        String sNextDate = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(sStr);
        Date date = null;
        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(iCal, iDate);
        sNextDate = formatter.format(calendar.getTime());
        return sNextDate ;
    }

    /**
     * 返回两个日期之间相差几天
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;

    }

}
