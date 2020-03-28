package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.service.ITrackingWaterDetailsService;
import org.springframework.stereotype.Service;

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

}
