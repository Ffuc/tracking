package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.common.utils.JudgeEmpty;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.dto.GameSelectCondition;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.ITrackingWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private TrackingWaterMapper trackingWaterMapper;


    @Override
    public Result selectLHByCondition(GameSelectCondition gameSelectCondition, Integer page, Integer size) {
        //根据开始日期和结束日期查询water表中的所有数据 ，放入trackingWaters中
        QueryWrapper<TrackingWater> queryWrapper_Warter = new QueryWrapper<TrackingWater>();
        if (gameSelectCondition.getBeginTime() != null && gameSelectCondition.getEndTime() != null) {
            /*时间操作  ge(>= )  le(<=) */
            queryWrapper_Warter.lambda()
                    .ge(TrackingWater::getCreateTime, gameSelectCondition.getBeginTime())
                    .le(TrackingWater::getCreateTime, gameSelectCondition.getEndTime());
        } else {
            return ResultResponseUtil.ok().msg("请选择开始时间和结束时间");
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getTableId())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getTableId, gameSelectCondition.getTableId());
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getBootId())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getBootsId, gameSelectCondition.getBootId());
        }
        if (!JudgeEmpty.isEmpty(gameSelectCondition.getGameType())) {
            queryWrapper_Warter.lambda().eq(TrackingWater::getGameType, gameSelectCondition.getGameType());
        }

        List<TrackingWater> trackingWaters = trackingWaterMapper.selectList(queryWrapper_Warter);
        if (trackingWaters == null || trackingWaters.size() <= 0) {
            return ResultResponseUtil.ok().msg("没有查到数据");
        }

        ArrayList<String> waterIds = new ArrayList<>();
        trackingWaters.forEach(trackingWater -> waterIds.add(trackingWater.getWaterId()));
        return null;
//        QueryWrapper

//        Page<TrackingWaterDetails> page = new Page<TrackingWaterDetails>(currentPage, size);
//        Page<TrackingGame> gamePage = trackingGameMapper.selectPage(page, queryWrapper);
//        if (gamePage.getRecords() == null || gamePage.getRecords().size() <= 0) {
//            return ResultResponseUtil.ok().msg("没有查询到数据");
//        }


//        HashMap<String, Object> map = new HashMap<>();
//        map.put("page", gamePage);
//        return ResultResponseUtil.ok().data(map);

//        List<TrackingGame> trackingGameList = list(queryWrapper);
//        if(trackingGameList==null||trackingGameList.size()<=0){
//            return ResultResponseUtil.ok().msg("没有查询到数据");
//        }
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("data",);
//        map.put("totalSize",trackingGameList.size());
//        map.put("currentPage",currentPage);
//        map.put("size",size);
//        map.put("totalPage",trackingGameList.size()%size>0?(trackingGameList.size()/size):(trackingGameList.size()/size+1));
//        return ResultResponseUtil.ok().data(map);

    }

    @Override
    public IPage<Map<String, Object>> waterAccounts(TrackingAgencyAccountsDto trackingAgencyAccountsDto, Integer current) {
        QueryWrapper<TrackingWater> queryWrapper = new QueryWrapper<>();
        if (trackingAgencyAccountsDto == null) {
            IPage page = trackingWaterMapper.selectPage(new Page(1, 1), queryWrapper);
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
            queryWrapper.lambda().eq(TrackingWater::getBootsId, trackingAgencyAccountsDto.getBootId());
        }
        //结果集过滤
        queryWrapper.lambda().select(TrackingWater::getWaterId,
                TrackingWater::getTableId,
                TrackingWater::getBootsId,
                TrackingWater::getEndTime,
                TrackingWater::getDutchOfficer,
                TrackingWater::getResult,
                TrackingWater::getModifiedResult);
        //分页查询
        Page<TrackingWater> trackingWaterPage = new Page<>(current, 1);
        IPage<Map<String, Object>> mapIPage = this.pageMaps(trackingWaterPage, queryWrapper);
        if (mapIPage!=null || mapIPage.getRecords()!=null){
            for (Map<String, Object> record : mapIPage.getRecords()) {
                record.put("ship_id",record.get("water_id").toString()
                        .substring(record.get("water_id").toString().lastIndexOf("-"),
                                record.get("water_id").toString().length()));

            }
        }

        return mapIPage;
    }


    public IPage<Map<String, Object>> waterDetails(TrackingAgencyAccountsDto trackingAgencyAccountsDto, Integer current) {
        QueryWrapper<TrackingWater> queryWrapper = new QueryWrapper<>();
        if (trackingAgencyAccountsDto == null) {
            IPage page = trackingWaterMapper.selectPage(new Page(1, 1), queryWrapper);
            return page;
        }
        //输入时间
        if (trackingAgencyAccountsDto.getStartTime() != null) {
            queryWrapper.lambda().ge(TrackingWater::getEndTime, trackingAgencyAccountsDto.getStartTime());
            queryWrapper.lambda().le(TrackingWater::getEndTime, LocalDateTime.now());
        }
        //输入桌号
        if (trackingAgencyAccountsDto.getTableId() != null) {
            queryWrapper.lambda().eq(TrackingWater::getTableId, trackingAgencyAccountsDto.getTableId());
        }
        //输入靴号
        if (trackingAgencyAccountsDto.getBootId() != null) {
            queryWrapper.lambda().eq(TrackingWater::getBootsId, trackingAgencyAccountsDto.getBootId());
        }
        //结果集过滤
        queryWrapper.lambda().select(TrackingWater::getWaterId,
                TrackingWater::getTableId,
                TrackingWater::getBootsId,
                TrackingWater::getEndTime,
                TrackingWater::getResult);
        //分页查询
        Page<TrackingWater> trackingWaterPage = new Page<>(current, 1);
        IPage<Map<String, Object>> mapIPage = this.pageMaps(trackingWaterPage, queryWrapper);
        if (mapIPage!=null || mapIPage.getRecords()!=null){
            for (Map<String, Object> record : mapIPage.getRecords()) {
                record.put("ship_id",record.get("water_id").toString()
                        .substring(record.get("water_id").toString().lastIndexOf("-"),
                                record.get("water_id").toString().length()));

            }
        }
        return mapIPage;
    }


}
