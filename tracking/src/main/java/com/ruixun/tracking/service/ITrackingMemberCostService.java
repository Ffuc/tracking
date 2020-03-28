package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.TrackingMemberCost;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.dto.MemberSelectCondition;

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

    /*会员账目条件查询*/
    Result selectByCondition(MemberSelectCondition memberSelectCondition, Integer page, Integer size);

}
