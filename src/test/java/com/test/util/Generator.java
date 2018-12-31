package com.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator {

    TEST_TRAVIS_CI_BUILD

    public static String getCurrentTimestamp() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static Double getOrderPrice() {
        final String currentTime = getCurrentTimestamp();
        final Double base = Double.valueOf(currentTime.substring(currentTime.length() - 3));
        final Double offset = Math.random() * 500.;
        return Double.valueOf(Math.round(base + offset));
    }
}
