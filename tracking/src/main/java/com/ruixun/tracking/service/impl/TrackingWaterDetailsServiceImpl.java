package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Track;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class TrackingWaterDetailsServiceImpl extends ServiceImpl<TrackingWaterDetailsMapper, TrackingWaterDetails> implements ITrackingWaterDetailsService {

    @Autowired
    private TrackingWaterMapper trackingWaterMapper;

    /**
     * 通过给定 代理人 ,自动查询到这个代理人下面会员的所有返点收益
     */
    @Override
    public BigDecimal getRebatesEarnings(TrackingUser trackingUser) {
        BigDecimal bigDecimal = new BigDecimal(0); //不是代理2,返回0
        if (trackingUser.getUserType() != 2) {
            return bigDecimal;
        }
        LambdaQueryWrapper<TrackingWaterDetails> lam = new LambdaQueryWrapper<>();
        lam.select(TrackingWaterDetails::getWashCodeAmount).eq(TrackingWaterDetails::getReferrer, trackingUser.getReferrer());
        List<TrackingWaterDetails> list = list(lam);

        for (int i = 0; i < list.size(); i++) {
            bigDecimal = bigDecimal.add(list.get(i).getWashCodeAmount());
        }
        return bigDecimal;
    }

    /**
     * 通过给定 代理人 账号,自动查询到这个代理人下面会员的所有返点收益  警告!使用前需确认该代理的userType为2,是返点代理
     */
    @Override
    public BigDecimal getRebatesEarnings(String referrer) {
        BigDecimal bigDecimal = new BigDecimal(0); //不是代理2,返回0
        LambdaQueryWrapper<TrackingWaterDetails> lam = new LambdaQueryWrapper<>();
        lam.select(TrackingWaterDetails::getWashCodeAmount).eq(TrackingWaterDetails::getReferrer, referrer);
        List<TrackingWaterDetails> list = list(lam);

        for (int i = 0; i < list.size(); i++) {
            bigDecimal = bigDecimal.add(list.get(i).getWashCodeAmount());
        }
        return bigDecimal;
    }

    /**
     * 给定waterId 得到trackingWater
     */
    @Override
    public TrackingWater getTrackingWater(String waterId) {
        TrackingWater trackingWater = trackingWaterMapper.selectById(waterId);
        return trackingWater;
    }

}
