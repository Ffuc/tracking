package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.dao.TrackingUserMapper;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.service.ITrackingUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class TrackingUserServiceImpl extends ServiceImpl<TrackingUserMapper, TrackingUser> implements ITrackingUserService {

}
