package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
public interface ITrackingWaterDetailsService extends IService<TrackingWaterDetails> {
    /*
         获得返点收益
          */
    BigDecimal getRebatesEarnings(TrackingUser trackingUser);

    BigDecimal getRebatesEarnings(String referrer);

    TrackingWater getTrackingWater(String waterId);
}
