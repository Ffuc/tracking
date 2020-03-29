package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.dao.TrackingMemberCostMapper;
import com.ruixun.tracking.entity.TrackingMemberCost;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.service.ITrackingMemberCostService;
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
public class TrackingMemberCostServiceImpl extends ServiceImpl<TrackingMemberCostMapper, TrackingMemberCost> implements ITrackingMemberCostService {

    /**
     * 查询分担费用
     */
    @Override
    public BigDecimal getSharingCost(TrackingUser trackingUser) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if (trackingUser.getUserType() != 1) {
            return bigDecimal;
        }
        LambdaQueryWrapper<TrackingMemberCost> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TrackingMemberCost::getReference, trackingUser.getReferrer()).eq(TrackingMemberCost::getIsDelete, 0);
        List<TrackingMemberCost> list = list(lambdaQueryWrapper);
        for (int i = 0; i < list.size(); i++) {
            bigDecimal = bigDecimal.add(list.get(i).getTotalMoney());
        }
        return bigDecimal;
    }

    /**
     * 查询分担费用
     * 警告:请提前保证用户类型为 占成代理
     */
    @Override
    public BigDecimal getSharingCost(String referer) {
        BigDecimal bigDecimal = new BigDecimal(0);
        LambdaQueryWrapper<TrackingMemberCost> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TrackingMemberCost::getReference, referer).eq(TrackingMemberCost::getIsDelete, 0);
        List<TrackingMemberCost> list = list(lambdaQueryWrapper);
        for (int i = 0; i < list.size(); i++) {
            bigDecimal = bigDecimal.add(list.get(i).getTotalMoney());
        }
        return bigDecimal;
    }
}
