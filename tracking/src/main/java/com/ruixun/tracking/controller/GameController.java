package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-26 07:57
 **/
@RestController
@RequestMapping("/tracking/game")
@Api("游戏")
public class GameController {
    @Autowired
    private ITrackingWaterService iTrackingWaterService;

    /**
     * 1.龙湖和局-条件查询
     */
    @PostMapping(value = "/SelectByCondition")
    @ApiOperation("龙虎和局-条件查询")
    public Result SelectByCondition(@RequestBody GameSelectCondition gameSelectCondition, Integer page, Integer size) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        //设值游戏类型为龙虎和局
        return iTrackingWaterService.selectLHByCondition(gameSelectCondition, page, size);
    }

    /**
     * 1.龙湖和局-条件查询
     */
    @PostMapping(value = "/SelectByCondition2")
    @ApiOperation("龙虎和局-条件查询")
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
