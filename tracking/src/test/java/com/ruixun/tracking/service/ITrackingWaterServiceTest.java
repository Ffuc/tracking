package com.ruixun.tracking.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ITrackingWaterServiceTest {


    @Test
    void test1() {
        List<String> list = Arrays.asList("oo", "11", "00", "oo");
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        list.forEach(i -> System.out.println(i));

    }
}