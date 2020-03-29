package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.entity.pig.BigMember;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-26 07:57
 **/
@CrossOrigin
@RestController
@RequestMapping("/tracking/game")
@Api("游戏")
public class GameController {
    @Autowired
    private ITrackingWaterService iTrackingWaterService;
    @Autowired
    private ITrackingWaterDetailsService iTrackingWaterDetailsService;
    @Autowired
    private ITrackingUserService iTrackingUserService;

    /**
     * 1.龙湖和局-条件查询
     */
    @PostMapping(value = "/SelectByCondition")
    @ApiOperation("龙虎和局-条件查询 还在编写")
    public Result SelectByCondition(@RequestBody GameSelectCondition memberSelectCondition) {
        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);
        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setSize(10);
        }
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition.getGameType();

        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);
        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setPage(10);
        }
        if (memberSelectCondition.getBeginTime() != null)
            lambdaQueryWrapper_detail.ge(TrackingWaterDetails::getBetTime, memberSelectCondition.getBeginTime()); //时间要大于给定开始时间
        if (memberSelectCondition.getEndTime() != null)
            lambdaQueryWrapper_detail.le(TrackingWaterDetails::getBetTime, memberSelectCondition.getEndTime());  //时间要小于给定结束时间
        if (memberSelectCondition.getTableId() != null)
            lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, memberSelectCondition.getTableId());       //桌号
        if (memberSelectCondition.getBootId() != null)
            lambdaQueryWrapper_detail.like(TrackingWaterDetails::getWaterId, "-" + memberSelectCondition.getBootId());  //靴号
        if (memberSelectCondition.getGameType() != null)
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getGameType, memberSelectCondition.getGameType());   //游戏类别
        if (memberSelectCondition.getNoteCode() != null)
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getMoneyType, memberSelectCondition.getNoteCode());   //注码类别
        if (memberSelectCondition.getCodeId() != null)
            lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getAccount, memberSelectCondition.getCodeId());   //注码类别

        List<TrackingWaterDetails> list = iTrackingWaterService.list(lambdaQueryWrapper_detail);
        //清除重复账号操作
        List<String> listAccount = new ArrayList<>();
        List<String> accounts = new ArrayList<>();
        list.forEach(i -> listAccount.add(i.getAccount()));
        HashSet h = new HashSet(listAccount);
        accounts.clear();
        accounts.addAll(h);
        //清除重复账号操作 -end
//        for (int i = 0; i < accounts.size(); i++) {
//            BigMember bigMember = new BigMember(accounts.get(i), iTrackingWaterService, iTrackingWaterDetailsService, iTrackingUserService);
//            Map all = bigMember.getAll(lambdaQueryWrapper_water);
//            if (gameType != null)
//                all.put("gameType", tableType);
//            data.add(all);
//        }


        //设值游戏类型为龙虎和局

//        //设值游戏类型为龙虎和局
//        return iTrackingWaterService.selectLHByCondition(gameSelectCondition, gameSelectCondition.getPage(), gameSelectCondition.getSize());
        return null;
    }

    /**
     * 1.龙湖和局-条件查询
     */
//    @PostMapping(value = "/SelectByCondition2")
//    @ApiOperation("龙虎和局-条件查询")
    public Result SelectByCondition2(@RequestBody GameSelectCondition gameSelectCondition, Integer page, Integer size) {
        LambdaUpdateWrapper<TrackingWaterDetails> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        if (gameSelectCondition.getBeginTime() != null)
            lambdaUpdateWrapper.ge(TrackingWaterDetails::getBetTime, gameSelectCondition.getBeginTime()); //时间要大于给定开始时间
        if (gameSelectCondition.getEndTime() != null)
            lambdaUpdateWrapper.le(TrackingWaterDetails::getBetTime, gameSelectCondition.getEndTime());  //时间要小于给定结束时间
        if (gameSelectCondition.getCodeId() != null)
            lambdaUpdateWrapper.eq(TrackingWaterDetails::getAccount, gameSelectCondition.getCodeId());
        if (gameSelectCondition.getCodeId() != null)
            lambdaUpdateWrapper.eq(TrackingWaterDetails::getAccount, gameSelectCondition.getCodeId());
        //设值游戏类型为龙虎和局


        return iTrackingWaterService.selectLHByCondition(gameSelectCondition, page, size);
    }

}
