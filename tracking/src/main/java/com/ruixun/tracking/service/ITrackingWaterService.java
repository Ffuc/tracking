package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;

import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
public interface ITrackingWaterService extends IService<TrackingWater> {
    IPage<TrackingWater> waterAccounts(TrackingAgencyAccountsDto trackingAgencyAccountsDto, Integer current);

    Result selectLHByCondition(GameSelectCondition gameSelectCondition, Integer page, Integer size);


    List<TrackingWaterDetails> list(LambdaQueryWrapper<TrackingWaterDetails> lambdaQueryWrapper_detail);
}
