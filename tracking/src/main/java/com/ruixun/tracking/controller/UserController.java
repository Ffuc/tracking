package com.ruixun.tracking.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.VO.Agent;
import com.ruixun.tracking.entity.dto.UserParams;
import com.ruixun.tracking.entity.dto.UserParams2;
import com.ruixun.tracking.service.ITrackingMemberCostService;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-26 07:08
 * @yichenkang
 **/
@CrossOrigin
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
    @ApiOperation(value = "信息接口1-用户管理:提供条件,获得对应的结果  ")
    public Result getUserInfo(@RequestBody UserParams userParams) {
        if (userParams.getPage() == null) {
            userParams.setPage(1);
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
        for (int i = 0; i < aa.getRecords().size(); i++) {
            Map map = aa.getRecords().get(i);
            Integer userType = (Integer) map.get("user_type");
            String userName = (String) map.get("account");
            if (userType == 1) { //占成代理
                BigDecimal sharingCost = costService.getSharingCost(userName);
                map.put("sharingCost", sharingCost);//分担消费
                map.put("rebatesEarnings", "/");//返点收益
            } else if (userType == 2) {  //返点代理
                BigDecimal rebatesEarnings = trackingWaterDetailsService.getRebatesEarnings(userName); //二次查询  返点收益
                map.put("rebatesEarnings", rebatesEarnings);//返点收益
                map.put("sharingCost", "/");//分担消费
            } else if (userType == 0) {  //会员
                map.put("rebatesEarnings", "/");//返点收益
                map.put("sharingCost", "/");//分担消费
            } else {
                return ResultResponseUtil.error().msg("查询失败,用户类型错误").data(null);
            }
        }
        return ResultResponseUtil.ok().msg("查询成功").data(aa);
    }

    @PostMapping("/info/deletedMember")
    @ApiOperation(value = "信息接口1-已删代理:提供条件,获得对应的结果 referrer上级代理账号,account账号,username姓名,page页码", notes = "referrer上级代理账号,account账号,username姓名,page页码")
    public Result getUserStatisticsInfo(@RequestBody UserParams2 userParams2) {
        if (userParams2.getPage() == null) {
            userParams2.setPage(1);
        }
        LambdaQueryWrapper<TrackingUser> queryWrapper = new LambdaQueryWrapper<>();
        if (userParams2.getHigherAgent() != null) {
            queryWrapper.eq(TrackingUser::getReferrer, userParams2.getHigherAgent());
        }
        if (userParams2.getAccount() != null) {
            queryWrapper.eq(TrackingUser::getAccount, userParams2.getAccount());
        }
        if (userParams2.getName() != null) {
            queryWrapper.eq(TrackingUser::getUsername, userParams2.getName());
        }
        queryWrapper.eq(TrackingUser::getUserType, 1).or().eq(TrackingUser::getUserType, 2).eq(TrackingUser::getIsDelete, 1);//已被删除的代理
        queryWrapper.select(TrackingUser::getReferrer, TrackingUser::getAccount, TrackingUser::getUsername, TrackingUser::getPhone, TrackingUser::getProportion, TrackingUser::getWashCodeRatio);
        Integer pageNum = userParams2.getPage();
        Page<TrackingUser> page = new Page<>(pageNum, 10);
        IPage<Map<String, Object>> page1 = iTrackingUserService.pageMaps(page, queryWrapper);
        return ResultResponseUtil.ok().msg("查询成功").data(page1);
    }

    @PostMapping("/info/deletedAgent")
    @ApiOperation(value = "信息接口1:提供条件,获得对应的结果 referrer上级代理账号,account账号,username姓名,page页码", notes = "referrer上级代理账号,account账号,username姓名,page页码")
    public Result getDeletedMemberInfo(@RequestBody Map data) {
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
        queryWrapper.eq(TrackingUser::getIsDelete, 1).eq(TrackingUser::getUserType, 0);//已被删除
        queryWrapper.select(TrackingUser::getReferrer, TrackingUser::getAccount, TrackingUser::getUsername, TrackingUser::getCardId, TrackingUser::getUsername, TrackingUser::getPhone, TrackingUser::getWashCodeRatio);
        Integer pageNum = (Integer) data.get("page");
        Page<TrackingUser> page = new Page<>(pageNum, 10);
        IPage<Map<String, Object>> page1 = iTrackingUserService.pageMaps(page, queryWrapper);
        return ResultResponseUtil.ok().msg("查询成功").data(page1);
    }

    @PostMapping("/info/regainMember")
    @ApiOperation(value = "信息接口-增加会员1:提供条件,account账号 可以从删除状态还原账号", notes = "account账号")
    public Result regainMember(String account) {
        LambdaUpdateWrapper<TrackingUser> queryWrapper = new LambdaUpdateWrapper<>();
        if (account != null) {
            queryWrapper.eq(TrackingUser::getAccount, account);
        }
        queryWrapper.eq(TrackingUser::getUserType, 0).set(TrackingUser::getIsDelete, 0);//已被删除

        boolean update = iTrackingUserService.updateOne(queryWrapper);
        Map map = new HashMap();
        map.put("result", update);
        return ResultResponseUtil.ok().msg("已更新").data(map);
    }

    @PostMapping("/info/deleteMember")
    @ApiOperation(value = "信息接口-删除会员1:提供条件,account账号  删除账号", notes = "account账号")
    public Result deleteMember(String account) {
        LambdaUpdateWrapper<TrackingUser> queryWrapper = new LambdaUpdateWrapper<>();
        if (account != null) {
            queryWrapper.eq(TrackingUser::getAccount, account);
        }
        queryWrapper.eq(TrackingUser::getUserType, 0).set(TrackingUser::getIsDelete, 1);//已被删除
        boolean update = iTrackingUserService.updateOne(queryWrapper);
        Map map = new HashMap();
        map.put("result", update);
        return ResultResponseUtil.ok().msg("操作已执行").data(map);
    }

    @PostMapping("/info/addAgent")
    @ApiOperation("信息接口1:添加代理")
    public Result addAgent(@RequestBody Agent agent) {
        LambdaUpdateWrapper<TrackingUser> queryWrapper = new LambdaUpdateWrapper<>();
        //校验数据是否为null
        int count = 0;
        queryWrapper.eq(TrackingUser::getAccount, agent.getAccount()).or().eq(TrackingUser::getCardId, agent.getCard_id());
        int count1 = iTrackingUserService.count(queryWrapper);
        if (count1 > 0) {
            return ResultResponseUtil.error().msg("用户名或卡号已已存在").data(null);
        }
        //校验数据是否为null
        TrackingUser trackingUser = new TrackingUser();
        trackingUser.setAccount(agent.getAccount());
        trackingUser.setCreatePerson("root");
        trackingUser.setCreateTime(LocalDateTime.now());
        if (agent.getProportion() != null) {
            trackingUser.setUserType(1); //占成代理
        } else {
            trackingUser.setUserType(2); //返点代理
        }
        trackingUser.setUsername(agent.getName());
        trackingUser.setRemark(agent.getRemark());
        trackingUser.setCardId(agent.getCard_id());
        trackingUser.setPhone(agent.getPhone());
        boolean save = iTrackingUserService.addOne(trackingUser);
        Map map = new HashMap();
        map.put("result", save);
        return ResultResponseUtil.error().msg("操作执行").data(map);
    }


}
