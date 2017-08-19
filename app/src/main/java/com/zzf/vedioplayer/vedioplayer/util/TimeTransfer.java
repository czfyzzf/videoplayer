package com.zzf.vedioplayer.vedioplayer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzf on 2017/8/18.
 */

public class TimeTransfer {
    /**
     * 2      * 把long 转换成 日期 再转换成String类型
     * 3
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
