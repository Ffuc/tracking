package com.ruixun.tracking.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruixun.tracking.entity.TrackingUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
@Repository
public interface TrackingUserMapper extends BaseMapper<TrackingUser> {
    //查询会员人数
    Long findOneByUserType();
    //代理人数
    long findOneByCountUserType();
    Long findOnLineByUserType(Integer UserType);
    //代理在线人数
    long findAllByStateVIP();
    //会员在线人数
    long findAllByStateAgency();
    //会员盈利
    List<Map<String,Object>> findProfitVIP();
    //代理盈利
    List<Map<String,Object>> findProfitAgency();
    //散客盈利





}
