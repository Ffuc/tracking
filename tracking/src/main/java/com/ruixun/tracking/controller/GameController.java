package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruixun.tracking.common.check.PageCheck;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.DictionaryItem;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.entity.dto.GameSelectCondition2;
import com.ruixun.tracking.entity.pig.BigMember;
import com.ruixun.tracking.service.IDictionaryItemService;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.junit.rules.TestWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


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
    @Autowired
    private IDictionaryItemService dictionaryItemService;

    /**
     * 1.龙湖和局-条件查询
     */
    @PostMapping(value = "/dragon/index")
    @ApiOperation("龙虎和局-查询")
    public Result SelectByCondition(@RequestBody GameSelectCondition memberSelectCondition) {
        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);
        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setSize(10);
        }
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper_water = new LambdaQueryWrapper<>();
        String tableType = "";
        Integer gameType = memberSelectCondition.getGameType(); //龙湖和局类型是1
        gameType = 1;
        lambdaQueryWrapper_water.eq(TrackingWater::getGameType, 1); //龙虎类型
        if (memberSelectCondition.getPage() == null) {
            memberSelectCondition.setPage(1);
        }
        if (memberSelectCondition.getSize() == null) {
            memberSelectCondition.setPage(10);
        }
        //找到对应的waterId
        if (memberSelectCondition.getBeginTime() != null)
            lambdaQueryWrapper_water.ge(TrackingWater::getEndTime, memberSelectCondition.getBeginTime()); //时间要大于给定开始时间
        if (memberSelectCondition.getEndTime() != null)
            lambdaQueryWrapper_water.le(TrackingWater::getEndTime, memberSelectCondition.getEndTime());  //时间要小于给定结束时间
        if (memberSelectCondition.getTableId() != null)
            lambdaQueryWrapper_water.like(TrackingWater::getWaterId, memberSelectCondition.getTableId());       //桌号
        if (memberSelectCondition.getBootId() != null)
            lambdaQueryWrapper_water.like(TrackingWater::getWaterId, "-" + memberSelectCondition.getBootId());  //靴号
        if (memberSelectCondition.getGameType() != null)
            lambdaQueryWrapper_water.eq(TrackingWater::getGameType, memberSelectCondition.getGameType());   //游戏类别
        if (memberSelectCondition.getNoteCode() != null)
            lambdaQueryWrapper_water.eq(TrackingWater::getMoneyType, memberSelectCondition.getNoteCode());   //注码类别


        List<TrackingWater> list = iTrackingWaterService.list(lambdaQueryWrapper_water);
        //找到对应的waterId
        List<String> waterIdList = new ArrayList<>();       //waterId
        for (int i = 0; i < list.size(); i++) {
            waterIdList.add(list.get(i).getWaterId());
        }
        if (memberSelectCondition.getTableId() != null)
            if (memberSelectCondition.getCodeId() != null) {
                lambdaQueryWrapper_detail.eq(TrackingWaterDetails::getAccount, memberSelectCondition.getCodeId());
            }   //注码类别
        lambdaQueryWrapper_detail.in(TrackingWaterDetails::getWaterId, waterIdList); //选择在目标waterId里的
        List<TrackingWaterDetails> list1 = iTrackingWaterDetailsService.list(lambdaQueryWrapper_detail);
        List<String> accounts = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            accounts.add(list1.get(i).getAccount());
        }
        HashSet h = new HashSet(accounts);
        accounts.clear();
        accounts.addAll(h);
        List<Map> mapList = new ArrayList<>();
        //清除重复账号操作 -end
        for (int i = 0; i < accounts.size(); i++) {
            BigMember bigMember = new BigMember(accounts.get(i), iTrackingWaterService, iTrackingWaterDetailsService, iTrackingUserService,dictionaryItemService);
            Integer times = bigMember.betTimesSelf(null);
            BigDecimal betTotalMoney = bigMember.betTotalMoneySelf(null);
            Map map = new HashMap();
            map.put("userType", "会员");
            map.put("userId", bigMember.getAccount());
            map.put("name", bigMember.getUsername());
            map.put("totalMoney", betTotalMoney);
            map.put("times", times);
            mapList.add(map);
        }
        PageCheck pageCheck = new PageCheck(accounts.size(), memberSelectCondition.getPage(), 10);
        Map map = new HashMap();
        map.put("records", mapList);
        map.put("total", mapList.size());
        map.put("size", pageCheck.getSize());
        map.put("current", memberSelectCondition.getPage());
        map.put("pages", pageCheck.getPages());
        map.put("searchCount", true);
        return ResultResponseUtil.ok().msg("已查询").data(map);
    }

    /**
     * 1.龙湖和局-详细-条件查询
     */
    @PostMapping(value = "/dragon/details")
    @ApiOperation("龙虎和局-详细-查询")
    public Result selectDragonDetails(@RequestBody GameSelectCondition2 gameSelectCondition) {
        LambdaUpdateWrapper<TrackingWaterDetails> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        if (gameSelectCondition.getPage() == null) {
            gameSelectCondition.setPage(1);
        }
        if (gameSelectCondition.getSize() == null) {
            gameSelectCondition.setSize(10);
        }
        if (gameSelectCondition.getAccount() != null) {
            lambdaUpdateWrapper.eq(TrackingWaterDetails::getAccount, gameSelectCondition.getAccount());
        }
        lambdaUpdateWrapper.last("limit " + (gameSelectCondition.getPage() - 1) * gameSelectCondition.getSize() + "," + gameSelectCondition.getSize());//手动分页查询
        List<TrackingWaterDetails> list = iTrackingWaterDetailsService.list(lambdaUpdateWrapper);
        List<Map> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            TrackingWaterDetails one = list.get(i);
            map.put("time", one.getBetTime());
            map.put("account", one.getAccount());
            String arr[] = one.getWaterId().split("-");
            map.put("tableId", arr[0]);
            map.put("bootId", arr[1]);
            map.put("waterId", arr[2]);
            switch (one.getBetTarget()) {
                case 2:        //和
                    map.put("dragon", 0);
                    map.put("tiger", 0);
                    map.put("sum", one.getBetMoney());
                    break;
                case 6:      //龙
                    map.put("dragon", one.getBetMoney());
                    map.put("tiger", 0);
                    map.put("sum", 0);
                    break;
                case 7:      //虎
                    map.put("dragon", 0);
                    map.put("tiger", one.getBetMoney());
                    map.put("sum", 0);
                    break;
            }
            map.put("returnMoney", one.getBetMoney().multiply(new BigDecimal(0.5)));
            map.put("moneyType", iTrackingWaterService.getMoneyType(one.getWaterId()) == 0 ? "RMB" : "USD"); //人民币或者美元
            list1.add(map);
        }
        PageCheck pageCheck = new PageCheck(list.size(), gameSelectCondition.getPage(), 10);
        Map map = new HashMap();
        map.put("records", list);
        map.put("total", list.size());
        map.put("size", pageCheck.getSize());
        map.put("current", gameSelectCondition.getPage());
        map.put("pages", pageCheck.getPages());
        map.put("searchCount", true);
        return ResultResponseUtil.ok().msg("查询成功").data(map);
    }
}
