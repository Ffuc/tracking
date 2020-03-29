package com.ruixun.tracking.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@Api("铺流水账目详情")
@RequestMapping("/water")
public class TrackingWaterDetailsController {
    @Autowired
    private ITrackingWaterService iTrackingWaterService;

    @PostMapping("/findWaterDetails")
    @ApiOperation("铺流水账目 需传递参数 1.时间2.桌号3.靴号  startTime  tableId  bootId")
    public Result findWaterDetails(@RequestBody @RequestParam(required = false) TrackingAgencyAccountsDto trackingAgencyAccountsDto){
        IPage<TrackingWater> mapIPage;
        if (trackingAgencyAccountsDto==null){
            TrackingAgencyAccountsDto trackingAgencyAccountsDto1 = new TrackingAgencyAccountsDto();
            trackingAgencyAccountsDto1.setCurrent(1);
            mapIPage
                    = iTrackingWaterService.waterAccounts(trackingAgencyAccountsDto1, trackingAgencyAccountsDto1.getCurrent());
            if (mapIPage!=null)
                return ResultResponseUtil.ok().msg("查询成功").data(mapIPage);
            return ResultResponseUtil.ok().msg("null");
        }
        mapIPage
                = iTrackingWaterService.waterAccounts(trackingAgencyAccountsDto, trackingAgencyAccountsDto.getCurrent());
        if (mapIPage!=null)
        return ResultResponseUtil.ok().msg("查询成功").data(mapIPage);
        return ResultResponseUtil.ok().msg("null");
    }

    @PostMapping("/waterDetails")
    @ApiOperation("铺流水账目-修改结果详情 ")
    public Result fin(@RequestParam(required = true) String waterId){
        TrackingWater mapIPage
                = iTrackingWaterService.waterDetails(waterId);
        return ResultResponseUtil.ok().msg("查询成功").data(mapIPage);
    }
}
