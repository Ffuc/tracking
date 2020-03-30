package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.TrackingTable;
import com.ruixun.tracking.entity.TrackingWater;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
public interface ITrackingTableService extends IService<TrackingTable> {

    /*台桌管理条件查询*/
    Result findTablesInfo(TrackingWater trackingWater, Integer page, Integer size);


    Result findTablesDetailsInfo(List<String> watersId);
}
