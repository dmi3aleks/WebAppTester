package com.test.util;

public class Generator {

    static double price = 500.;

    public static Double getOrderPrice() {
        // biased random walk with a positive trend
        final Double biasPct = 5.;

        final Double offset = Math.random() * 3.;
        if (offset > 1) {
            if (offset <= 2. + biasPct/100.) {
                price += 1;
            } else {
                price -= 1;
            }
        }
        return price;
    }
}
