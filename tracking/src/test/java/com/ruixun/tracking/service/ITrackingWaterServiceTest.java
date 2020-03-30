package com.ruixun.tracking.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    @Test
    void name() {
        List<String> list = new ArrayList<>();
        list.add("o1");
        list.add("o2");
        list.add("o3");
        list.add("o4");
        List<String> strings = list.subList(0, 2);
        strings.forEach(i -> System.out.print(i));

        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test2() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();


        HSSFSheet sheet = wb.createSheet("任务模板");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("试题类型");
        row.createCell(1).setCellValue("试题序号");
        row.createCell(2).setCellValue("题干");
        row.createCell(3).setCellValue("解析");
        row.createCell(4).setCellValue("正确答案");
        row.createCell(5).setCellValue("选择题选项数");
        for (int i = 0; i < 10; i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(i);
            row1.createCell(1).setCellValue(i);
            row1.createCell(2).setCellValue(i);
            row1.createCell(3).setCellValue(i);
            row1.createCell(4).setCellValue(i);
            row1.createCell(5).setCellValue(i);
//            for (int j = 0; j < 10; j++) {
//                row1.createCell(j + 6).setCellValue(j);
//            }
        }
        String basePack = System.getProperty("user.dir") + "/xls/";
        File file = new File(basePack);
        if (!file.exists())
            file.mkdir();
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(basePack + "hello.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wb.write(output);
        output.flush();
    }
}