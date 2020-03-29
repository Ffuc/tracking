package com.ruixun.tracking.entity.pig;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-29 00:23
 **/

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

/**
 * 必须包含必要的属性,以及一些提供参数统计得到的属性
 */
@Data
public class BigMember {

    ITrackingWaterService trackingWaterService;
    ITrackingWaterDetailsService trackingWaterDetailsService;
    ITrackingUserService iTrackingUserService;

    //洗码号(账号)
    private String Account;     //账号

    private int userType = 0;  //用户类型

    private String higherAgent; //上级代理

    private Integer isDeleted; //是否删除

    private BigDecimal washCodeRatio; //洗码率

    private String username;

    public BigMember(String Account, ITrackingWaterService trackingWaterService,
                     ITrackingWaterDetailsService trackingWaterDetailsService,
                     ITrackingUserService iTrackingUserService) {
        this.Account = Account;
        //初始化
        this.trackingWaterService = trackingWaterService;
        this.trackingWaterDetailsService = trackingWaterDetailsService;
        this.iTrackingUserService = iTrackingUserService;
        QueryWrapper<TrackingUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TrackingUser::getAccount, Account);
        TrackingUser one = iTrackingUserService.getOne(queryWrapper);
        this.userType = one.getUserType();
        this.higherAgent = one.getReferrer();
        this.isDeleted = one.getIsDelete();
        this.washCodeRatio = one.getWashCodeRatio();
        this.username = one.getUsername();
    }

    private BigMember() {

    }

//    public Map getAll() {
//
//    }

    /**
     * 下注次数
     */
    public Integer betTimes(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        int count = trackingWaterDetailsService.count(lambdaUpdateWrapper);
        return count;
    }

    /**
     * 下注总额
     */
    public BigDecimal betTotalMoney(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaUpdateWrapper);
        BigDecimal betTotalMoney = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            betTotalMoney = betTotalMoney.add(list.get(i).getBetMoney());
        }
        return betTotalMoney;
    }

    /**
     * 总赢
     */
    public BigDecimal totalWinMoney(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaUpdateWrapper);
        BigDecimal betTotalMoney = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            betTotalMoney = betTotalMoney.add(list.get(i).getBetMoney());
        }
        return betTotalMoney;
    }

    /**
     * 总保险
     */
    public BigDecimal insuranceTotal(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaUpdateWrapper);
        BigDecimal insuranceTotal = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            insuranceTotal = insuranceTotal.add(list.get(i).getInsurance());
        }
        return insuranceTotal;
    }

    /**
     * 总洗码
     */
    public BigDecimal washCodeTotal(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaUpdateWrapper);
        BigDecimal washCodeTotal = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            washCodeTotal = washCodeTotal.add(list.get(i).getWashCodeAmount());
        }
        return washCodeTotal;
    }

    /**
     * 派彩所赢
     */
    public BigDecimal lotteryWin(LambdaQueryWrapper<TrackingWaterDetails> lambdaUpdateWrapper) {
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaUpdateWrapper);
        BigDecimal lotteryWin = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            lotteryWin = lotteryWin.add(list.get(i).getLotteryWin());
        }
        return lotteryWin;
    }

    /**
     * total 次数,总押,保险,总洗码,派彩所赢,洗码费,中和,中对  ,没包括桌子类型
     */
    public Map getAll(LambdaQueryWrapper<TrackingWater> queryWrapper) {
        Map map = new HashMap();
        LambdaQueryWrapper<TrackingWaterDetails> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TrackingWaterDetails::getAccount, this.Account);
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(queryWrapper1);
        BigDecimal lotteryWin = new BigDecimal(0);
        BigDecimal betTotalMoney = new BigDecimal(0);
        BigDecimal insuranceTotal = new BigDecimal(0);
        BigDecimal washCodeTotal = new BigDecimal(0);
        BigDecimal winMoneyTotal = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);
        BigDecimal pair = new BigDecimal(0);
        List<String> sumList = getSumWaterId(queryWrapper);
        List<String> pairList = getPairWaterId(queryWrapper);
        int count = trackingWaterDetailsService.count(queryWrapper1);
        for (int i = 0; i < list.size(); i++) {
            lotteryWin = lotteryWin.add(list.get(i).getLotteryWin());
            betTotalMoney = lotteryWin.add(list.get(i).getBetMoney());
            insuranceTotal = lotteryWin.add(list.get(i).getInsurance());
            washCodeTotal = lotteryWin.add(list.get(i).getWashCodeAmount());
            winMoneyTotal = lotteryWin.add(list.get(i).getWinMoney());
            if (sumList.contains(list.get(i).getWaterId())) {
                sum = lotteryWin.add(list.get(i).getWinMoney());
            }
            if (pairList.contains(list.get(i).getWaterId())) {
                pair = lotteryWin.add(list.get(i).getWinMoney());
            }
        }
        map.put("lotteryWin", lotteryWin);
        map.put("insuranceTotal", insuranceTotal);
        map.put("winMoneyTotal", winMoneyTotal);
        map.put("washCodeTotal", washCodeTotal);
        map.put("betTotalMoney", betTotalMoney);
        map.put("sum", sum);
        map.put("pair", pair);
        map.put("count", count);
        map.put("washCodeMoney", washCodeTotal.multiply(this.washCodeRatio));
        map.put("washCodeRatio", this.washCodeRatio);
        map.put("username", this.username);
        map.put("washCodeId", this.Account);
        return map;

    }

    /**
     * 和waterID
     */
    public List<String> getSumWaterId(LambdaQueryWrapper<TrackingWater> queryWrapper) {
        queryWrapper.eq(TrackingWater::getResult, 2);
        List<TrackingWater> list = trackingWaterService.list(queryWrapper);
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i).getWaterId());
        }
        return list1;
    }

    /**
     * 对WaterID
     */
    public List<String> getPairWaterId(LambdaQueryWrapper<TrackingWater> queryWrapper) {
        queryWrapper.eq(TrackingWater::getResult, 4).or().eq(TrackingWater::getResult, 5);
        List<TrackingWater> list = trackingWaterService.list(queryWrapper);
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i).getWaterId());
        }
        return list1;
    }

}
