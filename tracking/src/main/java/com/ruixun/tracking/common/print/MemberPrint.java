package com.ruixun.tracking.common.print;

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
            row.add(""+map.get("lotteryWin"));
            row.add(""+map.get("insuranceTotal"));
            row.add(""+map.get("winMoneyTotal"));
            row.add(""+map.get("washCodeTotal"));
            row.add(""+map.get("betTotalMoney"));
            row.add(""+map.get("sum"));
            row.add(""+map.get("pair"));
            row.add(""+map.get("count"));
            row.add(""+map.get("washCodeMoney"));
            row.add(""+map.get("washCodeRatio"));
            row.add(""+map.get("username"));
            row.add(""+map.get("washCodeId"));
            lists.add(row);
        }
        HSSFWorkbook hssfWorkbook = basePrint.getHSSFWorkbook("会员账目-详单", titles, lists, "会员账目-详单");
        return hssfWorkbook;
    }
}
