package com.space.util;

import com.space.constans.Constant;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataUtil {

    public static double round(double d) {
        return new BigDecimal(d).setScale(Constant.PRECISION, RoundingMode.HALF_UP).doubleValue();
    }


    public static Long parseId(String parseId) {
        long id = Long.parseLong(parseId);
        if (id < 1) {
            throw new IllegalArgumentException("ID less than 1");
        }
        return id;
    }
}
