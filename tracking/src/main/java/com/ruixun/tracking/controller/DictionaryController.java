package com.ruixun.tracking.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.DictionaryItem;
import com.ruixun.tracking.service.IDictionaryItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Program: tracking_system
 * <p>
 * Description: 字典
 **/
@CrossOrigin
@RestController
@Api("字典查询游戏类别等等信息")
@RequestMapping("/tracking/dictionary")

public class DictionaryController {
    @Autowired
    IDictionaryItemService dictionaryItemService;

    //service层写一个可以查询根据类型得到所有子元素的查询,数据存一个list<dictionary> 的集合返回即可,没有则返回500,空
    @ApiOperation("首页-信息接口1:查询所有游戏类别")
    @GetMapping("/type/game")
    public Result getGameType() {
        List<DictionaryItem> list = dictionaryItemService.getTypeList("game");
        return ResultResponseUtil.ok().msg("查询完毕").data(list);
    }

    @ApiOperation("首页-信息接口1:查询所有用户类别")
    @GetMapping("/type/user")
    public Result getUserType() {
        List<DictionaryItem> list = dictionaryItemService.getTypeList("user");
        return ResultResponseUtil.ok().msg("查询完毕").data(list);
    }

    @ApiOperation("首页-信息接口1:查询下注方式")
    @GetMapping("/type/bet")
    public Result getBetType() {
        List<DictionaryItem> list = dictionaryItemService.getTypeList("currency");
        return ResultResponseUtil.ok().msg("查询完毕").data(list);
    }

    @ApiOperation("首页-信息接口1:查询金钱类别")
    @GetMapping("/type/money")
    public Result getMoneyType() {
        List<DictionaryItem> list = dictionaryItemService.getTypeList("bet");
        return ResultResponseUtil.ok().msg("查询完毕").data(list);
    }

    @PostMapping("/info")
    @ApiOperation("首页-信息接口1:对应的字典:提供code,index(对应下标)")
    public Result getType2(@RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        String code = (String) jsonObject.get("code");
        Integer index = Integer.valueOf((String) jsonObject.get("index"));
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(DictionaryItem::getIndexNameCn).eq(DictionaryItem::getDicCode, code).eq(DictionaryItem::getCodeIndex, index);
        DictionaryItem one = dictionaryItemService.getOne(lambdaQueryWrapper);
        return ResultResponseUtil.ok().msg("查询完毕").data(one);
    }


}
