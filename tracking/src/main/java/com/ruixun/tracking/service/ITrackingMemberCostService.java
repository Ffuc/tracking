package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.entity.TrackingMemberCost;
import com.ruixun.tracking.entity.TrackingUser;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
public interface ITrackingMemberCostService extends IService<TrackingMemberCost> {

    BigDecimal getSharingCost(TrackingUser trackingUser);

    BigDecimal getSharingCost(String referer);
}
