package com.ruixun.tracking.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruixun.tracking.entity.TrackingUser;
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
@Mapper
@Repository
public interface TrackingUserMapper extends BaseMapper<TrackingUser> {
    Long findOneByUserType(Integer UserType);
    Long findOnLineByUserType(Integer UserType);

}
