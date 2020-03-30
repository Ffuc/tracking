package com.ruixun.tracking.common.print;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-30 15:25
 **/
public class MemberPrint {

    public HSSFWorkbook printXlsx(List<Map> list) {
        BasePrint basePrint = new BasePrint();
        List<String> titles = Arrays.asList("台桌类型", "洗码号", "名称", "下注次数", "下注总额", "总保险", "总洗码", "派彩所赢", "洗码率", "洗码费", "中和", "中对");
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            List<String> row = new ArrayList<>();
            row.add("" + map.get("tableType"));
            row.add("" + map.get("washCodeId"));
            row.add("" + map.get("username"));
            row.add("" + map.get("count"));
            row.add("" + map.get("betTotalMoney"));
            row.add("" + map.get("insuranceTotal"));
            row.add("" + map.get("winMoneyTotal"));
            row.add("" + map.get("lotteryWin"));
            row.add("" + map.get("washCodeRatio"));
            row.add("" + map.get("washCodeMoney"));
            row.add("" + map.get("sum"));
            row.add("" + map.get("pair"));
            lists.add(row);
        }
        HSSFWorkbook hssfWorkbook = basePrint.getHSSFWorkbook("member_details", titles, lists, "会员账目-详单");
        HSSFSheet sheet = hssfWorkbook.getSheet("member_details");
        //设置xlsx样式
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 3500);
        sheet.setColumnWidth(2, 2500);
        sheet.setColumnWidth(3, 2500);
        sheet.setColumnWidth(4, 4500);
        sheet.setColumnWidth(5, 4500);
        sheet.setColumnWidth(6, 4500);
        sheet.setColumnWidth(7, 4500);
        sheet.setColumnWidth(8, 2500);
        sheet.setColumnWidth(9, 2500);
        sheet.setColumnWidth(10, 2500);
        sheet.setColumnWidth(11, 2500);

        //设置xlsx样式结束
        return hssfWorkbook;
    }


}
