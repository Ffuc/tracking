package com.ruixun.tracking.entity.pig;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.service.ITrackingUserService;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import com.ruixun.tracking.service.ITrackingWaterService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.map.MultiKeyMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-29 09:45
 **/
public class BigWater {
    //"铺流水号")
    private String waterId;

    //"台桌号")
    private String tableId;

    //"靴号")
    private Integer bootsId;

    //"对应靴号的次数")
    private Integer times;

    //"牌库")
    private String cards;

    //"游戏类别")
    private Integer gameType;

    //"结束时间(开牌时间)")
    private LocalDateTime endTime;

    //"荷官")
    private String dutchOfficer;

    //"结果")
    private String result;

    //"修改结果")
    private String modifiedResult;
    private ITrackingWaterService trackingWaterService;

    private ITrackingWaterDetailsService trackingWaterDetailsService;

    public BigWater(String waterId, ITrackingWaterService trackingWaterService,
                    ITrackingWaterDetailsService trackingWaterDetailsService) {
        this.waterId = waterId;
        //初始化
        this.trackingWaterService = trackingWaterService;
        this.trackingWaterDetailsService = trackingWaterDetailsService;
        LambdaQueryWrapper<TrackingWater> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrackingWater::getWaterId, waterId);
        TrackingWater one = trackingWaterService.getOne(queryWrapper);
        //"铺流水号")
        this.waterId = one.getWaterId();
        //"台桌号")
        this.tableId = one.getTableId();
        //"靴号")
        this.bootsId = one.getBoots();
        //"对应靴号的次数")
        this.times = one.getTimes();
        //"牌库")
        this.cards = one.getCards();
        //"游戏类别")
        this.gameType = one.getGameType();
        //"结束时间(开牌时间)")
        this.endTime = one.getEndTime();
        //"荷官")
        this.dutchOfficer = one.getDutchOfficer();
        //"结果")
        this.result = one.getResult();
        this.endTime = one.getEndTime();

    }

    private BigWater() {

    }

    public Map betDetail(String waterId) {
        Map map = new HashMap();
        BigDecimal z1 = new BigDecimal(0);
        BigDecimal z2 = new BigDecimal(0);
        BigDecimal z3 = new BigDecimal(0);
        BigDecimal z4 = new BigDecimal(0);
        BigDecimal z5 = new BigDecimal(0);
        BigDecimal z6 = new BigDecimal(0);
        BigDecimal z7 = new BigDecimal(0);
        BigDecimal z8 = new BigDecimal(0);
        LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TrackingWaterDetails::getWaterId, waterId);
        List<TrackingWaterDetails> list = trackingWaterDetailsService.list(lambdaQueryWrapper);
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getBetTarget()) {
                case 1:
                    z1.add(list.get(i).getBetMoney());
                case 2:
                    z2.add(list.get(i).getBetMoney());
                case 3:
                    z3.add(list.get(i).getBetMoney());
                case 4:
                    z4.add(list.get(i).getBetMoney());
                case 5:
                    z5.add(list.get(i).getBetMoney());
                case 6:
                    z6.add(list.get(i).getBetMoney());
                case 7:
                    z7.add(list.get(i).getBetMoney());
                case 8:
                    z8.add(list.get(i).getBetMoney());
            }

        }
        map.put("z1", z1);
        map.put("z2", z2);
        map.put("z3", z3);
        map.put("z4", z4);
        map.put("z5", z5);
        map.put("z6", z6);
        map.put("z7", z7);
        map.put("z8", z8);
        return map;
    }

    public Map getAll() {
        Map map = new HashMap();
        map.put("waterId", waterId);
        map.put("tableId", tableId);
        map.put("bootsId", bootsId);
        map.put("times", times);
        map.put("cards", cards);
        map.put("gameType", gameType);
        map.put("endTime", endTime);
        map.put("dutchOfficer", dutchOfficer);
        map.put("result", result);
        map.put("betMap", betDetail(waterId));
        return map;
    }
}
