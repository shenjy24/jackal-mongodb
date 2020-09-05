package com.jonas.util;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtils {

    private static Random random = new Random();

    public static int nextInt(int start, int end) {
        return (int) Math.floor(Math.random() * (end - start + 1) + start);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(nextInt(1, 3));
        }
    }
}
