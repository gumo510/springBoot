package com.gumo.demo;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTest {


    @Test
    public void test() {
        // 随机进度
        Random r = new Random();
        int nextInt = r.nextInt(50);
        System.out.println(nextInt);
//        utc.setProgress(r.nextInt(50));
//        utc.setProgress(50 + r.nextInt(50));
//        utc.setProgress(100);

        // 随机验证码
        String substring = Double.toString(Math.random()).substring(2, 6);
        System.out.println(substring);

    }
}
