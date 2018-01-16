package com.crw.common.utils.date;

import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void test() {
        String dateStr = "2018-01-16 10:09:56";
        Date date = DateUtil.StringToDate(dateStr);
        System.out.println(date);// Tue Jan 16 10:09:56 CST 2018

        Date date1 = DateUtil.addDay(date, 1);
        System.out.println(date1);// Wed Jan 17 10:09:56 CST 2018

        String dateStr1 = DateUtil.addDay(dateStr, 2);
        System.out.println(dateStr1); // 2018-01-18 10:09:56

        Date date2 = DateUtil.addMinute(date, 1);
        System.out.println(date2); // Tue Jan 16 10:10:56 CST 2018

        String dateStr2 = DateUtil.addMinute(dateStr, 2);
        System.out.println(dateStr2); // 2018-01-16 10:11:56

        String dateStr3 = DateUtil.DateToString(date, "y-M-d");
        String dateStr4 = DateUtil.DateToString(date, DatePattern.MM_DD_HH_MM_CN);
        String dateStr5 = DateUtil.DateToString(date, DatePattern.YYYY_MM_DD_HH_MM_SS_EN);
        System.out.println(dateStr3); // 2018-1-16
        System.out.println(dateStr4); // 01月16日 10:09
        System.out.println(dateStr5); // 2018/01/16 10:09:56

        String date3 = DateUtil.getDate(new Date(1516069599527l));
        System.out.println(date3); // 2018-01-16
        String date4 = DateUtil.getDate("2018-01-16 10:18:56");
        System.out.println(date4); // 2018-01-16

        DatePattern datePattern = DateUtil.getDatePattern("2018年1月16日");
        System.out.println(datePattern.toString());// YYYY_MM_DD_CN

        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getDay(date);
        int hour = DateUtil.getHour(date);
        int minute = DateUtil.getMinute(date);
        int second = DateUtil.getSecond(date);
        System.out.println(year);// 2018
        System.out.println(month);// 1
        System.out.println(day);// 16
        System.out.println(hour);// 10
        System.out.println(minute);// 9
        System.out.println(second);// 56

        int intervalDays = DateUtil.getIntervalDays(date, DateUtil.addDay(date, 3));
        System.out.println(intervalDays);// 3
        int intervalDays1 = DateUtil.getIntervalDays("2018/01/31", "2019-01-31");
        System.out.println(intervalDays1);// 365

        String dateStr6 = DateUtil.StringToString("2018-01-16 15:56:14", DatePattern.YYYY_MM_DD_HH_MM_SS_CN);
        System.out.println(dateStr6); //2018年01月16日 15:56:14

        Week week = DateUtil.getWeek("2018-01-18 15:56:14");
        System.out.println(week + " " + week.toChinese()); // THURSDAY 星期四
        Week week1 = DateUtil.getWeek(date);
        System.out.println(week1 + " " + week1.toChinese()); // TUESDAY 星期二

        SimpleLunarCalendar simpleLunarCalendar = DateUtil.getSimpleLunarCalendar(date);
        System.out.println(simpleLunarCalendar.getAnimalString()); // 鸡
        System.out.println(simpleLunarCalendar.getDateString()); // 二零一七年十一月卅十日
        System.out.println(simpleLunarCalendar.getDay()); // 30
        System.out.println(simpleLunarCalendar.getDayString()); // 卅十
        System.out.println(simpleLunarCalendar.getMaxDayInMonth()); // 30
        System.out.println(simpleLunarCalendar.getMonth()); // 11
        System.out.println(simpleLunarCalendar.getMonthString());// 十一
        System.out.println(simpleLunarCalendar.getYear()); // 2017
        System.out.println(simpleLunarCalendar.getYearString()); // 二零一七

        Month monthEnum = DateUtil.getMonthEnum("2018-03-26");
        System.out.println(monthEnum + " " + monthEnum.toChinese()); // MARCH 三月
        Season season = DateUtil.getSeason(date);
        System.out.println(season + " " + season.toChinese()); // SPRING 春


        String dateStrX = "2018-03-06";
        String dateStrY = "2018-03-04";
        String dateStrZ = "2022-03-05";
        String dateX = DateUtil.getDate(dateStrX);
        String dateY = DateUtil.getDate(dateStrY);
        String dateZ = DateUtil.getDate(dateStrZ);
        System.out.println(DateUtil.getIntervalMonths(dateX, dateZ, false)); // 47
        System.out.println(DateUtil.getIntervalMonths(dateY, dateZ, false)); // 48
        System.out.println(DateUtil.getIntervalMonths(dateStrX, dateStrZ, false)); // 47
        System.out.println(DateUtil.getIntervalMonths(dateStrY, dateStrZ, false)); // 48
        System.out.println(DateUtil.getIntervalYears(dateX, dateZ, false)); // 3
        System.out.println(DateUtil.getIntervalYears(dateY, dateZ, false)); // 4
        System.out.println(DateUtil.getIntervalYears(dateStrX, dateStrZ, false)); // 3
        System.out.println(DateUtil.getIntervalYears(dateStrY, dateStrZ, false)); // 4
    }
}
