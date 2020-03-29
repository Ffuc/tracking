package com.ruixun.tracking.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruixun.tracking.entity.TrackingTable;
import com.ruixun.tracking.entity.TrackingWater;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Mapper
@Component
public interface TrackingTableMapper extends BaseMapper<TrackingTable> {

//    List<Map<String, Object>> findTablesInfo(@Param("water_ids") List<String> water_ids);
}
