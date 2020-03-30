package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingManager;
import com.ruixun.tracking.service.ITrackingManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-30 02:00
 **/
@CrossOrigin
@RestController
@RequestMapping("/tracking/manager")
@Api("管理员")
public class ManagerController {
    @Autowired
    ITrackingManagerService trackingManagerService;

    @PostMapping("/login")
    @ApiOperation("登陆")
    public Result login(@RequestBody User user, @ApiIgnore HttpSession session) {
        LambdaUpdateWrapper<TrackingManager> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(TrackingManager::getAccount, user.getUsername()).eq(TrackingManager::getPassword, user.getPassword());
        TrackingManager one = trackingManagerService.getOne(lambdaUpdateWrapper);
        Map map = new HashMap<>();
        if (one != null) {
            session.setAttribute("username", one.getAccount());
            session.setAttribute("password", one.getPassword());
            map.put("result", true);
            map.put("name", one.getName());
            return ResultResponseUtil.ok().msg("查询成功!欢迎!").data(map);
        } else {
            map.put("result", false);
            map.put("name", null);
            return ResultResponseUtil.ok().msg("查询失败!请重新检验账号和密码").data(map);
        }

    }

    @PostMapping("logout")
    @ApiOperation("注销")
    public Result logOut(@ApiIgnore HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("password");
        return ResultResponseUtil.ok().msg("退出成功!再见!");
    }
}
