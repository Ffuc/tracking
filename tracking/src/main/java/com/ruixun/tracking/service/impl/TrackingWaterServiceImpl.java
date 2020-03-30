package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.common.utils.JudgeEmpty;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.ITrackingWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class TrackingWaterServiceImpl extends ServiceImpl<TrackingWaterMapper, TrackingWater> implements ITrackingWaterService {

    @Autowired
    private TrackingWaterDetailsMapper trackingWaterDetailsMapper;
    @Autowired
    private TrackingWaterMapper trackingWaterMapper;

    @Override
    public Result selectLHByCondition(GameSelectCondition gameSelectCondition, Integer currentPage, Integer size) {
        //根据开始日期和结束日期查询water表中的所有数据 ，放入trackingWaters中
        QueryWrapper<TrackingWater> queryWrapper_Warter = new QueryWrapper<TrackingWater>();
        if (gameSelectCondition.getBeginTime() != null && gameSelectCondition.getEndTime() != null) {
            /*时间操作  ge(>= )  le(<=) */
            queryWrapper_Warter.lambda()
                    .ge(TrackingWater::getEndTime, gameSelectCondition.getBeginTime())
                    .le(TrackingWater::getEndTime, gameSelectCondition.getEndTime());
        } else {
            return ResultResponseUtil.ok().msg("请选择开始时间和结束时间");
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getTableId())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getTableId, gameSelectCondition.getTableId());
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getBootId())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getBootsTimes, gameSelectCondition.getBootId());
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getGameType())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getGameType, gameSelectCondition.getGameType());
        }

        List<TrackingWater> list = list(queryWrapper_Warter);
        if (list == null || list.size() <= 0) {
            return ResultResponseUtil.ok().msg("没有查到数据");
        }
        /*拿到流水表主键集合 waters*/
        ArrayList<String> waters = new ArrayList<>();
        list.forEach(l -> waters.add(l.getWaterId()));

        QueryWrapper<TrackingWaterDetails> queryWrapper = new QueryWrapper<TrackingWaterDetails>();

        if (!JudgeEmpty.isEmpty(gameSelectCondition.getNoteCode())) {
//            queryWrapper.lambda().eq(TrackingWaterDetails::getMoneyType,gameSelectCondition.getNoteCode());
        }

        if (!JudgeEmpty.isEmpty(gameSelectCondition.getCodeId())) {
            queryWrapper.lambda().eq(TrackingWaterDetails::getAccount, gameSelectCondition.getCodeId());
        }
        queryWrapper.lambda().in(TrackingWaterDetails::getWaterId, waters);
        List<TrackingWaterDetails> trackingWaterDetails = trackingWaterDetailsMapper.selectList(queryWrapper);
        ArrayList<String> strings = new ArrayList<>();
        trackingWaterDetails.forEach(tr -> strings.add(tr.getAccount()));
        List<String> distinctList = strings.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctList.size());


//        trackingWaterDetailsMapper.selectGroupByAccount(distinctList).forEach(System.out::println);

        queryWrapper.lambda().in(TrackingWaterDetails::getAccount, distinctList);
        Page<TrackingWaterDetails> page = new Page<TrackingWaterDetails>(currentPage, size);

        IPage<Map<String, Object>> mapIPage = trackingWaterDetailsMapper.selectMapsPage(page, queryWrapper);
//
//        IPage<TrackingWaterDetails> selectPage = trackingWaterDetailsMapper.selectPage(page, queryWrapper);
//        selectPage.getRecords().forEach(s->s.getAccount());

//        Page<TrackingWater> page = new Page<>(currentPage,size);
//        IPage<Map<String, Object>> maps = pageMaps(page, queryWrapper_Warter);
//        for (int i = 0; i < maps.getRecords().size(); i++) {
//            //存一局的信息
//            Map<String, Object> map = maps.getRecords().get(i);
//            //查询局详细表
//            QueryWrapper<TrackingWaterDetails> queryWrapper_water_details = new QueryWrapper<>();
//            if(!JudgeEmpty.isEmpty(gameSelectCondition.getNoteCode())){
//                queryWrapper_water_details.lambda().eq(TrackingWaterDetails::getMoneyType,gameSelectCondition.getNoteCode());
//            }
//            if(!JudgeEmpty.isEmpty(gameSelectCondition.getCodeId())){
//                queryWrapper_water_details.lambda().eq(TrackingWaterDetails::getAccount,gameSelectCondition.getCodeId());
//            }
//            List<TrackingWaterDetails> trackingWaterDetails = trackingWaterDetailsMapper.selectList(queryWrapper_water_details);
//            map.put("下注次数",trackingWaterDetails.size());
//            map.put("总金额",trackingWaterDetails.stream().map(TrackingWaterDetails::getBetMoney).reduce(BigDecimal::add));
//        }
        return ResultResponseUtil.ok().data(mapIPage);
    }


    @Override
    public Integer getMoneyType(String waterId) {
        LambdaQueryWrapper<TrackingWater> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TrackingWater::getWaterId, waterId).select(TrackingWater::getGameType);
        TrackingWater one = this.getOne(lambdaQueryWrapper);
        return one.getGameType();
    }


    @Override
    public IPage<TrackingWater> waterAccounts(TrackingAgencyAccountsDto trackingAgencyAccountsDto, Integer current) {
        QueryWrapper<TrackingWater> queryWrapper = new QueryWrapper<>();
        if (trackingAgencyAccountsDto == null) {
            IPage page = trackingWaterMapper.selectPage(new Page(current, 1), queryWrapper);
            return page;
        }
        //输入时间
        if (trackingAgencyAccountsDto.getStartTime() != null) {

//            Instant instant = trackingAgencyAccountsDto.getStartTime().toInstant(ZoneOffset.of("+8"));
//            long entTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

//            System.out.println(startTime+"----"+entTime);

            queryWrapper.lambda().ge(TrackingWater::getEndTime, trackingAgencyAccountsDto.getStartTime());
            queryWrapper.lambda().le(TrackingWater::getEndTime, LocalDateTime.now());
        }
        //输入桌号
        if (trackingAgencyAccountsDto.getTableId() != null) {
            queryWrapper.lambda().eq(TrackingWater::getTableId, trackingAgencyAccountsDto.getTableId());
        }
        //输入靴号
        if (trackingAgencyAccountsDto.getBootId() != null) {
            queryWrapper.lambda().eq(TrackingWater::getBootsTimes, trackingAgencyAccountsDto.getBootId());
        }
        //结果集过滤
        queryWrapper.lambda().select(TrackingWater::getWaterId,
                TrackingWater::getTableId,
                TrackingWater::getBootsTimes,
                TrackingWater::getTimes,
                TrackingWater::getEndTime,
                TrackingWater::getDutchOfficer,
                TrackingWater::getResult,
                TrackingWater::getModifiedResult);
        //分页查询
        Page<TrackingWater> trackingWaterPage = new Page<>(current, 1);
        IPage<TrackingWater> trackingWaterPage1 = trackingWaterMapper.selectPage(trackingWaterPage, queryWrapper);
        return trackingWaterPage1;
    }


    //查询详情
    public TrackingWater waterDetails(String waterId) {

        if (waterId!=null){
            QueryWrapper<TrackingWater> queryWrapper = new QueryWrapper<>();
            //结果集过滤
            queryWrapper.lambda().eq(TrackingWater::getWaterId,waterId);
            queryWrapper.lambda().select(TrackingWater::getWaterId,
                    TrackingWater::getTableId,
                    TrackingWater::getBootsTimes,
                    TrackingWater::getTimes,
                    TrackingWater::getResult);
            TrackingWater trackingWater = trackingWaterMapper.selectOne(queryWrapper);
            return trackingWater;
        }
        return null;
    }


}
