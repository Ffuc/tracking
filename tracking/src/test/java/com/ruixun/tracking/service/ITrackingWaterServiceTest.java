package com.ruixun.tracking.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;



class ITrackingWaterServiceTest {


    @Test
    void test1() {
        List<String> list = new ArrayList<>();
        list.add("o");
        list.add("o");
        list.add("o");
        list.add("o1");
        list.forEach(i -> System.out.print(i));
        System.out.println();
        //去重操作

        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        //去重操作

        list.forEach(i -> System.out.print(i));


    }
}