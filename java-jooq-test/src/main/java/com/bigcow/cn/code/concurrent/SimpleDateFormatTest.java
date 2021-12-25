package com.bigcow.cn.code.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatTest {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");

    public static void main(String[] args) throws ParseException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Date date = simpleDateFormat.parse("2020-12-13");
                    System.out.println("--->" + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
