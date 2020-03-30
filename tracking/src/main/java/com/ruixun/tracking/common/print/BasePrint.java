package com.ruixun.tracking.common.print;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-30 02:39
 **/
public class BasePrint {

    /**
     * ;
     * 打印:
     * 参数:topic 主题,titles每列的题目,objects每行去除了第一列的所有数据
     */
    public HSSFWorkbook getHSSFWorkbook(String topic, List<String> titles, List<List<String>> objects, String name) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(topic);
        HSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < titles.size(); i++) {  //生成标识栏
            titleRow.createCell(i).setCellValue(titles.get(i));
        }
        for (int i = 0; i < objects.size(); i++) {
            HSSFRow normalRow = sheet.createRow(i + 1); //第一行已被占用
            List<String> objectList = objects.get(i);
            for (int j = 0; j < objectList.size(); j++) {
                normalRow.createCell(j).setCellValue(objectList.get(j));
            }
        }
        return wb;
    }

    public void print(String topic, List<String> titles, List<List> objects, String targetPath) {

    }

}
