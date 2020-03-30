package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruixun.tracking.common.print.MemberPrint;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;


import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.MemberSelectCondition;
import com.ruixun.tracking.entity.dto.MemberSelectCondition2;

import com.ruixun.tracking.entity.dto.MemberSelectCondition3;
import com.ruixun.tracking.entity.pig.BigMember;
import com.ruixun.tracking.entity.pig.BigWater;
import com.ruixun.tracking.service.IDictionaryItemService;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Program: tracking_system
 * <p>
 * Description:
 **/
@CrossOrigin
@RestController
@RequestMapping("/tracking/member")
@Api("会员账目")
public class MemberAccountsController {

    @Autowired
    private ITrackingWaterDetailsService trackingWaterDetailsService;
    @Autowired
    private ITrackingUserService trackingUserService;
    @Autowired
    private ITrackingWaterService trackingWaterService;
    @Autowired
    private IDictionaryItemService iDictionaryItemService;

    /**
     * 1.会员账目 条件查询
     * 2.会员账目 导出 -excel
     * 3.会员帐目-下单详情  条件查询
     */

    @PostMapping(value = "/SelectByCondition")
    @ApiOperation("会员账目 条件查询")
    public Result SelectByCondition(@RequestBody MemberSelectCondition memberSelectCondition) {
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper_water = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition.getGameType();

        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);

        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setPage(10);
        }
        if (memberSelectCondition.getNoteCode() != null) {//注码(币种)方式一样
            lambdaQueryWrapper_water.eq(TrackingWater::getMoneyType, memberSelectCondition.getNoteCode());
        }
        if (memberSelectCondition.getBetWay() != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getBetWay, memberSelectCondition.getBetWay());  //下注方式一样
        }
        if (gameType != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getGameType, memberSelectCondition.getGameType());  //游戏类别一样
            if (gameType == 1) {
                tableType = "龙虎";
            } else if (gameType == 0) {
                tableType = "百家乐";
            }
        }
        if (memberSelectCondition.getBeginTime() != null)
            lambdaQueryWrapper_detail.ge(TrackingWaterDetails::getBetTime, memberSelectCondition.getBeginTime()); //时间要大于给定开始时间
        lambdaQueryWrapper_water.ge(TrackingWater::getCreateTime, memberSelectCondition.getBeginTime());
        if (memberSelectCondition.getEndTime() != null)
            lambdaQueryWrapper_detail.le(TrackingWaterDetails::getBetTime, memberSelectCondition.getEndTime());  //时间要小于给定结束时间
        lambdaQueryWrapper_water.le(TrackingWater::getCreateTime, memberSelectCondition.getEndTime());
        if (memberSelectCondition.getTableId() != null) {   //桌号,靴号
            String tableId = memberSelectCondition.getTableId();
            if (memberSelectCondition.getBootId() != null) {
                String booId = memberSelectCondition.getBootId();
                lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, tableId + "-" + booId);  //模糊查询
                lambdaQueryWrapper_water.like(TrackingWater::getWaterId, tableId + "-" + booId);
            } else {
                lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, tableId);  //模糊查询
                lambdaQueryWrapper_water.like(TrackingWater::getWaterId, tableId);
            }
        }
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaQueryWrapper_detail);
        List<String> listAccount = new ArrayList<>();
        List<String> accounts = new ArrayList<>();
        list.forEach(i -> listAccount.add(i.getAccount()));
        HashSet h = new HashSet(listAccount);
        accounts.clear();
        accounts.addAll(h);
        List<Map> data = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            BigMember bigMember = new BigMember(accounts.get(i), trackingWaterService, trackingWaterDetailsService, trackingUserService,iDictionaryItemService);
            Map all = bigMember.getAll(lambdaQueryWrapper_water);
            if (gameType != null)
                all.put("gameType", tableType);
            data.add(all);
        }
        Map map = new HashMap();

        map.put("records", data);
        map.put("total", accounts.size());
        map.put("size", accounts.size());
        map.put("current", 1);
        map.put("pages", 1);
        map.put("searchCount", true);
        return ResultResponseUtil.ok().msg("所有数据").data(map);
    }

    /**
     * 1.会员账目 条件查询
     * 2.会员账目 导出 -excel
     * 3.会员帐目-下单详情  条件查询
     */

    @PostMapping(value = "/SelectByCondition/print")
    @ApiOperation("会员账目 打印 条件查询")
    public void SelectByConditionAndPrint(@RequestBody MemberSelectCondition3 memberSelectCondition, @ApiIgnore HttpServletResponse response) {
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper_water = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition.getGameType();

        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);

        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setPage(10);
        }
        if (memberSelectCondition.getNoteCode() != null) {//注码(币种)方式一样
            lambdaQueryWrapper_water.eq(TrackingWater::getMoneyType, memberSelectCondition.getNoteCode());
        }
        if (memberSelectCondition.getBetWay() != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getBetWay, memberSelectCondition.getBetWay());  //下注方式一样
        }
        if (gameType != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getGameType, memberSelectCondition.getGameType());  //游戏类别一样
            if (gameType == 1) {
                tableType = "龙虎";
            } else if (gameType == 0) {
                tableType = "百家乐";
            }
        }
        if (memberSelectCondition.getBeginTime() != null)
            lambdaQueryWrapper_detail.ge(TrackingWaterDetails::getBetTime, memberSelectCondition.getBeginTime()); //时间要大于给定开始时间
        lambdaQueryWrapper_water.ge(TrackingWater::getCreateTime, memberSelectCondition.getBeginTime());
        if (memberSelectCondition.getEndTime() != null)
            lambdaQueryWrapper_detail.le(TrackingWaterDetails::getBetTime, memberSelectCondition.getEndTime());  //时间要小于给定结束时间
        lambdaQueryWrapper_water.le(TrackingWater::getCreateTime, memberSelectCondition.getEndTime());
        if (memberSelectCondition.getTableId() != null) {   //桌号,靴号
            String tableId = memberSelectCondition.getTableId();
            if (memberSelectCondition.getBootId() != null) {
                String booId = memberSelectCondition.getBootId();
                lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, tableId + "-" + booId);  //模糊查询
                lambdaQueryWrapper_water.like(TrackingWater::getWaterId, tableId + "-" + booId);
            } else {
                lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, tableId);  //模糊查询
                lambdaQueryWrapper_water.like(TrackingWater::getWaterId, tableId);
            }
        }
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaQueryWrapper_detail);
        List<String> listAccount = new ArrayList<>();
        List<String> accounts = new ArrayList<>();
        list.forEach(i -> listAccount.add(i.getAccount()));
        HashSet h = new HashSet(listAccount);
        accounts.clear();
        accounts.addAll(h);
        List<Map> data = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            BigMember bigMember = new BigMember(accounts.get(i), trackingWaterService, trackingWaterDetailsService, trackingUserService,iDictionaryItemService);
            Map all = bigMember.getAll(lambdaQueryWrapper_water);
            if (gameType != null)
                all.put("gameType", tableType);
            data.add(all);
        }
        MemberPrint memberPrint = new MemberPrint();
        HSSFWorkbook wb = memberPrint.printXlsx(data);
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
//        Map map = new HashMap();
//
//        map.put("records", data);
//        map.put("total", accounts.size());
//        map.put("size", accounts.size());
//        map.put("current", 1);
//        map.put("pages", 1);
//        map.put("searchCount", true);
//        return ResultResponseUtil.ok().msg("所有数据").data(map);
    }

    @PostMapping(value = "/SelectByCondition2")
    @ApiOperation("会员账目-结账 条件查询")
    public Result memberSelectByCondition(@RequestBody MemberSelectCondition2 memberSelectCondition2) {
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper_water = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition2.getGameType();
        if (memberSelectCondition2.getPage() == null) {
            memberSelectCondition2.setPage(1);
        }
        if (memberSelectCondition2.getSize() == null) {
            memberSelectCondition2.setSize(10);
        }
        if (memberSelectCondition2.getNoteCode() != null) {                     //注码(币种)方式一样
            lambdaQueryWrapper_water.eq(TrackingWater::getMoneyType, memberSelectCondition2.getNoteCode());
        }
        if (memberSelectCondition2.getBetWay() != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getBetWay, memberSelectCondition2.getBetWay());  //下注方式一样
        }
        if (gameType != null) {
            lambdaQueryWrapper_water.eq(TrackingWater::getGameType, memberSelectCondition2.getGameType());  //游戏类别一样
            if (gameType == 1) {
                tableType = "龙虎";
            } else if (gameType == 0) {
                tableType = "百家乐";
            }
        }
        if (memberSelectCondition2.getBeginTime() != null)
            lambdaQueryWrapper_water.ge(TrackingWater::getCreateTime, memberSelectCondition2.getBeginTime());
        if (memberSelectCondition2.getEndTime() != null)
            lambdaQueryWrapper_water.le(TrackingWater::getCreateTime, memberSelectCondition2.getEndTime());
        if (memberSelectCondition2.getTableId() != null) {   //桌号,靴号
            lambdaQueryWrapper_water.le(TrackingWater::getWaterId, memberSelectCondition2.getTableId());
        }
        if (memberSelectCondition2.getBootId() != null) {   //桌号,靴号
            lambdaQueryWrapper_water.le(TrackingWater::getBoots, memberSelectCondition2.getBootId());
        }
        List<TrackingWater> list = trackingWaterService.list(lambdaQueryWrapper_water);
        List<Map> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TrackingWater trackingWater = list.get(i);
            BigWater bigWater = new BigWater(trackingWater.getWaterId(), trackingWaterService, trackingWaterDetailsService);
            Map all = bigWater.getAll();
            String[] results = ((String) all.get("result")).split(",");
            String game_result = "";
            for (int j = 0; j < results.length; j++) {
                String cn = iDictionaryItemService.getGameCN("game_result", Integer.valueOf(results[j]));
                game_result += cn + " ";
            }
            all.put("result", game_result);
            result.add(all);
        }

        Map map = new HashMap();
        map.put("records", result);
        map.put("total", result.size());
        map.put("size", 10);
        map.put("current", 1);
        map.put("pages", 1);
        map.put("searchCount", true);
        return ResultResponseUtil.ok().msg("所有数据").data(map);
    }


}
