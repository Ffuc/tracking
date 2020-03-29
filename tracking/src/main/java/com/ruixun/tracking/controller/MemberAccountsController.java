package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.MemberSelectCondition;
import com.ruixun.tracking.entity.pig.BigMember;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Program: tracking_system
 * <p>
 * Description:
 **/
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

    /**
     * 1.会员账目 条件查询
     * 2.会员账目 导出 -excel
     * 3.会员帐目-下单详情  条件查询
     */

    @PostMapping(value = "/SelectByCondition")
    @ApiOperation("会员账目 条件查询")
    public Result SelectByCondition(@RequestBody MemberSelectCondition memberSelectCondition, Integer page, Integer size) {
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper_water = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition.getGameType();

        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        if (memberSelectCondition.getNoteCode() != null) {
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getMoneyType, memberSelectCondition.getNoteCode());  //注码(币种)方式一样
        }
        if (memberSelectCondition.getBetWay() != null) {
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getBetWay, memberSelectCondition.getBetWay());  //下注方式一样
        }
        if (gameType != null) {
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getGameType, memberSelectCondition.getGameType());  //游戏类别一样
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
            BigMember bigMember = new BigMember(accounts.get(i), trackingWaterService, trackingWaterDetailsService, trackingUserService);
            Map all = bigMember.getAll(lambdaQueryWrapper_water);
            if (gameType != null)
                all.put("gameType", tableType);
            data.add(all);
        }

        return ResultResponseUtil.ok().msg("所有数据").data(data);
    }
}
