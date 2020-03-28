package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.dao.KeyMapper;
import com.ruixun.tracking.service.IKeyService;
import org.springframework.stereotype.Service;
import com.ruixun.tracking.entity.Key;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class KeyServiceImpl extends ServiceImpl<KeyMapper, Key> implements IKeyService {

}
