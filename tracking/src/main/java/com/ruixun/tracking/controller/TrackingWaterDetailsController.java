package com.ruixun.tracking.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
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
    public Result findWaterDetails(@RequestBody TrackingAgencyAccountsDto trackingAgencyAccountsDto,@RequestBody @RequestParam(defaultValue = "1") Integer current){
        IPage<Map<String, Object>> mapIPage
                = iTrackingWaterService.waterAccounts(trackingAgencyAccountsDto, current);
        return ResultResponseUtil.ok().msg("查询成功").data(mapIPage);
    }

    @PostMapping("/waterDetails")
    @ApiOperation("铺流水账目-修改结果详情 参数 1.时间2.桌号3.靴号  startTime  tableId  bootId")
    public Result fin(@RequestBody TrackingAgencyAccountsDto trackingAgencyAccountsDto, @RequestBody @RequestParam(defaultValue = "1") Integer current){
        IPage<Map<String, Object>> mapIPage
                = iTrackingWaterService.waterDetails(trackingAgencyAccountsDto, current);
        return ResultResponseUtil.ok().msg("查询成功").data(mapIPage);
    }
}
