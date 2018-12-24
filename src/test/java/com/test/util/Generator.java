package com.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator {

    public static String getCurrentTimestamp() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static Double getOrderPrice() {
        final String currentTime = getCurrentTimestamp();
        return Double.valueOf(currentTime.substring(currentTime.length() - 3));
    }
}
