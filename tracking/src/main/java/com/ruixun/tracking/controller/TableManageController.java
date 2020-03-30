package com.ruixun.tracking.controller;

import com.github.pagehelper.PageInfo;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.service.ITrackingTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author suitao
 * @description
 */
@RestController
@RequestMapping("/tracking/tableManage")
@Api("台桌管理")
public class TableManageController {

    @Autowired
    private ITrackingTableService tableService;

    @CrossOrigin
    @PostMapping(value = "/init")
    @ApiOperation("台桌管理 初始化")
    public Result init(@RequestBody TrackingWater trackingWater, Integer page, Integer size){
        if(page==null||page<=1){
            page=1;
        }
        if(size==null){
            size=10;
        }
        LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        trackingWater.setCreateTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        trackingWater.setEndTime(LocalDateTime.now());
        return tableService.findTablesInfo(trackingWater,page,size);
    }

    @CrossOrigin
    @PostMapping(value = "/SelectByCondition")
    @ApiOperation("台桌管理 条件查询（createTime-开始时间，endTime-结束时间，tableId-桌号，boots-靴号，gameType-类型，moneyType-注码，betWay-下注方式）")
    public Result findTablesInfo(@RequestBody TrackingWater trackingWater, Integer page, Integer size){
        if(page==null||page<=1){
            page=1;
        }
        if(size==null){
            size=10;
        }
        return tableService.findTablesInfo(trackingWater,page,size);
    }

    @CrossOrigin
    @PostMapping(value = "/SelectDetailsByCondition")
    @ApiOperation("台桌管理 详情：查询该台桌的详情（根据watersId 流水号集合查询数据）")
    public Result findTablesDetailsInfo(List<String> watersId){

        return tableService.findTablesDetailsInfo(watersId);

    }


}
