package com.ruixun.tracking.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruixun.tracking.common.utils.MapUtil;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.UserParams;
import com.ruixun.tracking.service.ITrackingMemberCostService;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-26 07:08
 **/
@RestController
@RequestMapping("/tracking/user")
@Api("用户管理")
public class UserController {
    /**
     * 1.用户管理查询
     * 2.统计页面
     * 3.已删会员查询
     * 4.已删代理查询 (可能缺少一个操作字段,可以在remark那个公共字段记录,或者其他办法)
     * 5.新增代理
     */
    @Autowired
    ITrackingUserService iTrackingUserService;

    @Autowired
    ITrackingWaterDetailsService trackingWaterDetailsService;

    @Autowired
    ITrackingMemberCostService costService;

    @PostMapping("/info")
    @ApiOperation("信息接口1:提供条件,获得对应的结果")
    public Result getUserInfo(@RequestBody UserParams userParams) {
        if (userParams.getPage() == null) {
            return ResultResponseUtil.ok().msg("查询失败,页码为null").data(null);
        }
        LambdaQueryWrapper<TrackingUser> queryWrapper = new LambdaQueryWrapper<>();
        if (userParams.getName() != null) { //名字不为空就拼接模糊查询
            queryWrapper.like(TrackingUser::getUsername, userParams.getName());
        }
        if (userParams.getPhone() != null) { //电话不为空就拼接查询电话
            queryWrapper.eq(TrackingUser::getPhone, userParams.getPhone());
        }
        if (userParams.getType() != null) { //用户类型不为空就拼接类型
            queryWrapper.eq(TrackingUser::getUserType, userParams.getType());
        }
        if (userParams.getAccount() != null) { //是否拼接账号
            queryWrapper.eq(TrackingUser::getAccount, userParams.getAccount());
        }
        //指定需要查询的数据  TrackingUser::getType=字段名
        //返点收益和分摊费用另查
        Page<TrackingUser> page = new Page<>(userParams.getPage(), 10);
        IPage<Map<String, Object>> aa = iTrackingUserService.pageMaps(page, queryWrapper);
//        for (int i = 0; i < aa.getRecords().size(); i++) {
//            Map map = aa.getRecords().get(i);
//
//            BigDecimal rebatesEarnings = trackingWaterDetailsService.getRebatesEarnings(trackingUser); //二次查询  返点收益
//            map.put("rebatesEarnings", rebatesEarnings);//返点收益
//            BigDecimal sharingCost = costService.getSharingCost(trackingUser);
//            map.put("sharingCost", sharingCost);//返点收益
//            list.add(map);
//        }
//        BeanUtils.copyProperties(page, result);
//        result.setRecords(list);
//        return ResultResponseUtil.ok().msg("查询成功").data(result);
        return null;
    }

    @PostMapping("/info/deletedMember")
    @ApiOperation("信息接口1:提供条件,获得对应的结果 referrer上级代理账号,account账号,username姓名")
    public Result getUserStatisticsInfo(@RequestBody Map data) {
        if (data.get("page") == null) {
            return ResultResponseUtil.ok().msg("查询失败,页码为null").data(null);
        }
        LambdaQueryWrapper<TrackingUser> queryWrapper = new LambdaQueryWrapper<>();
        if (data.get("referrer") != null) {
            queryWrapper.eq(TrackingUser::getReferrer, (String) data.get("referrer"));
        }
        if (data.get("account") != null) {
            queryWrapper.eq(TrackingUser::getAccount, (String) data.get("account"));
        }
        if (data.get("username") != null) {
            queryWrapper.eq(TrackingUser::getUsername, (String) data.get("username"));
        }
        queryWrapper.eq(TrackingUser::getIsDelete, 1);//已被删除
        queryWrapper.select(TrackingUser::getReferrer, TrackingUser::getAccount, TrackingUser::getUsername, TrackingUser::getPhone, TrackingUser::getProportion, TrackingUser::getWashCodeRatio);


        Integer pageNum = (Integer) data.get("page");
        Page<TrackingUser> page = new Page<>(pageNum, 10);
        IPage<TrackingUser> page1 = iTrackingUserService.page(page, queryWrapper);
        return ResultResponseUtil.ok().msg("查询成功").data(page1);
    }
//
//
//    @GetMapping("/info/deletedAgent")
//    @ApiOperation("信息接口1:提供条件,获得对应的结果 referrer上级代理账号,account账号,username姓名")
//    public Result getDeletedMemberInfo(@RequestBody Map data) {
//
//        iTrackingUserService
//
//
//    }


}
