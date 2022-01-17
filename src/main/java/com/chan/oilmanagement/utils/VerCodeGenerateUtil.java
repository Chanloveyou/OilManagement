package com.chan.oilmanagement.utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;


public class VerCodeGenerateUtil {

    private static final String SYMBOLS = "0123456789";

    private static final Random RANDOM = new SecureRandom();

    public static String generateVerCode() {
        char[] codeChars = new char[6];
        for (int i = 0; i < codeChars.length; i++) {
            codeChars[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(codeChars);
    }

    /**
     *计算两个日期的分钟差
     */
    public static int getMinute(Date fromDate, Date toDate) {
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }
}
