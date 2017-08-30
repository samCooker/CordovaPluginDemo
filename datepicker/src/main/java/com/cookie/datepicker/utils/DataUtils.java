package com.cookie.datepicker.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 数组操作工具类
 * 
 * Utils of data operation
 *
 * @author AigeStudio 2015-07-22
 */
public final class DataUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    /**
     * 一维数组转换为二维数组
     *
     * @param src    ...
     * @param row    ...
     * @param column ...
     * @return ...
     */
    public static String[][] arraysConvert(String[] src, int row, int column) {
        String[][] tmp = new String[row][column];
        for (int i = 0; i < row; i++) {
            tmp[i] = new String[column];
            System.arraycopy(src, i * column, tmp[i], 0, column);
        }
        return tmp;
    }

    /**
     * 返回一天的小时数，默认24小时制
     * @param is12Hour
     * @return
     */
    public static String[] hoursArray(boolean is12Hour){
        int hourCount=24;
        if(is12Hour){
            hourCount=12;
        }
        String[] hoursArr = new String[hourCount];

        for(int i = 0;i<hourCount;i++){
            hoursArr[i]=i+1+"";
        }

        return hoursArr;
    }

    public static String[] minutesArray(){
        int minusCount=60;
        String[] minusArr = new String[minusCount];

        for(int i = 0;i<minusCount;i++){
            minusArr[i]=i+1+"";
        }

        return minusArr;
    }
}
