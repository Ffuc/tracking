package com.ruixun.tracking.controller;

import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-29 13:05
 **/

@CrossOrigin
@RestController
@RequestMapping("/test")
@Api("跨域测试controller")
public class TestController {

    @PostMapping("/test")
    public String hello(@RequestBody String name) {
        System.out.println(name);
        return name;

    }


    @PostMapping("/test/xls")
    public String printXls(@ApiIgnore HttpServletResponse response) {
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
//        String basePack = System.getProperty("user.dir") + "/xls/";
//        File file = new File(basePack);
//        if (!file.exists())
//            file.mkdir();
        String codedFileName = "奖金制度";
        response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName + ".xlsx");
        // 响应类型,编码
        response.setContentType("application/octet-stream;charset=UTF-8");
        // 形成输出流
        OutputStream osOut = null;
        try {
            osOut = response.getOutputStream();
            // 将指定的字节写入此输出流
            wb.write(osOut);
            // 刷新此输出流并强制将所有缓冲的输出字节被写出
            osOut.flush();
            // 关闭流
            osOut.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "ok";
    }
}