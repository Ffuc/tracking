package com.ruixun.tracking.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface TrackingWaterDetailsMapper extends BaseMapper<TrackingWaterDetails> {

}
