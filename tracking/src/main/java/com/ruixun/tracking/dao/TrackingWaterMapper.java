package com.ruixun.tracking.dao;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruixun.tracking.entity.TrackingWater;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Repository
@Mapper
public interface TrackingWaterMapper extends BaseMapper<TrackingWater> {

    List<Object> findTablesInfo();

}
