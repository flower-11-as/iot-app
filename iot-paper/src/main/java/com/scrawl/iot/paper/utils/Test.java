package com.scrawl.iot.paper.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Integer[] b = list.toArray(new Integer[0]);
        System.out.println(b.length);
    }
}
