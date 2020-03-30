package com.ruixun.tracking.service.impl;

        import com.alibaba.druid.sql.visitor.functions.If;
        import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
        import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
        import com.baomidou.mybatisplus.core.metadata.IPage;
        import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import com.ruixun.tracking.dao.TrackingUserMapper;
        import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
        import com.ruixun.tracking.dao.TrackingWaterMapper;
        import com.ruixun.tracking.entity.TrackingTable;
        import com.ruixun.tracking.entity.TrackingUser;
        import com.ruixun.tracking.entity.TrackingWater;
        import com.ruixun.tracking.entity.TrackingWaterDetails;
        import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
        import com.ruixun.tracking.service.ITrackingAgentAccounts;

        import java.math.BigDecimal;
        import java.time.LocalDate;
        import java.util.*;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.time.LocalDateTime;
        import java.time.LocalTime;
        import java.time.ZoneOffset;
        import java.time.temporal.TemporalAdjusters;

@Service
public class TrackingAgentAccounts extends ServiceImpl<TrackingUserMapper, TrackingUser> implements ITrackingAgentAccounts {


    @Autowired
    private TrackingUserMapper trackingUserMapper;

    @Autowired
    private TrackingWaterMapper trackingWaterMapper;

    @Autowired
    private TrackingWaterDetailsMapper trackingWaterDetailsMapper;

    //准备结果集
    IPage<Map<String, Object>> mapIPage=null;
    //流水信息表
    List<TrackingWaterDetails> trackingWaterDetails=null;
    //流水详情表
    List<TrackingWater> trackingWaters=null;

    QueryWrapper<TrackingUser> userqueryWrapper= new QueryWrapper<>();
    QueryWrapper<TrackingTable> tableQueryWrapper = new QueryWrapper<>();
    QueryWrapper<TrackingUser> userQueryWrapper = new QueryWrapper<>();
    QueryWrapper<TrackingWater> waterQueryWrapper = new QueryWrapper<>();
    QueryWrapper<TrackingWater> waterQueryWrapper2 = new QueryWrapper<>();
    QueryWrapper<TrackingWaterDetails> waterDetailsQueryWrapper=new QueryWrapper<>();
    QueryWrapper<TrackingUser> userQueryWrapper1 = new QueryWrapper<>();

    //定义下注金额总
    BigDecimal USDbetMoney = new BigDecimal(0);
    //定义总赢
    BigDecimal USDwashCodeAmountMAX = new BigDecimal(0);
    //定义总洗码
    BigDecimal USDwashCodeAmountMIN = new BigDecimal(0);
    //总洗码费
    BigDecimal USDwashAodeAmount = new BigDecimal(0);

    //定义下注金额总
    BigDecimal RMBbetMoney = new BigDecimal(0);
    //定义总赢
    BigDecimal RMBwashCodeAmountMAX = new BigDecimal(0);
    //定义总洗码
    BigDecimal RMBwashCodeAmountMIN = new BigDecimal(0);
    //总洗码费
    BigDecimal RMBwashAodeAmount = new BigDecimal(0);
    //代理占股
    BigDecimal Earnings= new BigDecimal(0);



    /*
    账目管理
     */
    @Override
    @Transactional
    public IPage<Map<String, Object>> getAll(TrackingAgencyAccountsDto trackingAgencyAccountsDto) {
        //查询所有 代理
        //代理卡号
        if (trackingAgencyAccountsDto != null && trackingAgencyAccountsDto.getAccount()!=null){
            userQueryWrapper.lambda().eq(TrackingUser::getAccount,trackingAgencyAccountsDto.getAccount());
        }
        //代理卡号
        if (trackingAgencyAccountsDto != null && trackingAgencyAccountsDto.getCardId()!=null){
            userQueryWrapper.lambda().eq(TrackingUser::getCardId,trackingAgencyAccountsDto.getCardId());
        }
        //代理姓名
        if (trackingAgencyAccountsDto != null && trackingAgencyAccountsDto.getUsername()!=null){
            userQueryWrapper.lambda().eq(TrackingUser::getUsername,trackingAgencyAccountsDto.getUsername());
        }
        userQueryWrapper.lambda().eq(TrackingUser::getUserType,1).or().eq(TrackingUser::getUserType,2);
        //设置结果集
        userQueryWrapper.lambda().select(TrackingUser::getProportion,   //占股
                TrackingUser::getRebatesEarnings,                       //返点收益率
                TrackingUser::getUsername,                              //用户名字
                TrackingUser::getAccount,                               //代理账号，洗码号
                TrackingUser::getReferrer,                              //上级代理
                TrackingUser::getWashCodeRatio);                        //洗码率
        Page<TrackingUser> trackingWaterPage = new Page<>(1, 10);

        //准备结果集
        mapIPage = this.pageMaps(trackingWaterPage, userQueryWrapper);

        if (mapIPage!=null || mapIPage.getRecords().size()!=0){
            //构造查询条件
              /*  waterDetailsQueryWrapper.lambda().select(TrackingWaterDetails::getAccount,  //代理账号，洗码号
                        TrackingWaterDetails::getBetMoney,                                  //下注金额
                        TrackingWaterDetails::getWaterId,                                   //对应的铺号
                        TrackingWaterDetails::getBetTarget,                                //下注目标
                        TrackingWaterDetails::getWashCodeAmount);                                   //下注方式*/
            List<Map<String, Object>> records = mapIPage.getRecords();

            for (Map<String, Object> record : records) {

                if (record.get("referrer")!=null && !"Q000".equals(record.get("referrer").toString())){
                    //设置过滤条件  流水表中的代理 和 用户表相匹配
                    waterDetailsQueryWrapper.lambda().eq(TrackingWaterDetails::getReferrer,record.get("referrer"));
//                        waterDatailsQueryWrapper.lambda().eq(TrackingWaterDetails::getReferrer,record.get("account"));
                    this.getTrackingTaterDetailss( waterDetailsQueryWrapper,trackingAgencyAccountsDto);
                    record.put("USDbetMoney",USDbetMoney);
                    record.put("USDwashCodeAmountMAX",USDwashCodeAmountMAX);
                    record.put("USDwashCodeAmountMIN",USDwashCodeAmountMIN);
                    record.put("USDwashAodeAmount",USDwashAodeAmount);

                    record.put("RMBwashAodeAmount",RMBwashAodeAmount);
                    record.put("RMBbetMoney",RMBbetMoney);
                    record.put("RMBwashCodeAmountMAX",RMBwashCodeAmountMAX);
                    record.put("RMBwashCodeAmountMIN",RMBwashCodeAmountMIN);
                    record.put("Earnings",Earnings);
                    USDbetMoney=new BigDecimal(0);
                    USDwashCodeAmountMAX=new BigDecimal(0);
                    USDwashCodeAmountMIN=new BigDecimal(0);
                    RMBbetMoney=new BigDecimal(0);
                    RMBwashCodeAmountMAX=new BigDecimal(0);
                    RMBwashCodeAmountMIN=new BigDecimal(0);
                    USDwashAodeAmount=new BigDecimal(0);
                    RMBwashAodeAmount=new BigDecimal(0);
                    Earnings=new BigDecimal(0);
                }
            }
              /*  if (waterQueryWrapper!=null){

                    trackingWaters = trackingWaterMapper.selectList(waterQueryWrapper);
                    for (TrackingWater trackingWater : trackingWaters) {

                    }
                }*/
        }
        return mapIPage;
    }
    //需要去重
    List<String> waterId;
    private void getTrackingTaterDetailss(QueryWrapper<TrackingWaterDetails> waterDetailsQueryWrapper,TrackingAgencyAccountsDto trackingAgencyAccountsDto){
        if (trackingAgencyAccountsDto!=null){
            //开始时间
            if (trackingAgencyAccountsDto.getStartTime()!=null && trackingAgencyAccountsDto.getEndTime()!=null) {
                waterDetailsQueryWrapper.lambda().ge(TrackingWaterDetails::getBetTime,trackingAgencyAccountsDto.getStartTime());
                waterDetailsQueryWrapper.lambda().le(TrackingWaterDetails::getBetTime,trackingAgencyAccountsDto.getEndTime());
            }
            //昨天
            if (trackingAgencyAccountsDto.getYesterday()!=null){
                if (trackingAgencyAccountsDto.getStartTime()==null && trackingAgencyAccountsDto.getEndTime()==null) {
                    waterDetailsQueryWrapper.lambda().ge(TrackingWaterDetails::getBetTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(-1));
                    waterDetailsQueryWrapper.lambda().le(TrackingWaterDetails::getBetTime, LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(-1));
                }
            }
            //今天
            if (trackingAgencyAccountsDto.getToday()!=null){
                if (trackingAgencyAccountsDto.getStartTime()==null && trackingAgencyAccountsDto.getEndTime()==null) {
                    waterDetailsQueryWrapper.lambda().ge(TrackingWaterDetails::getBetTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
                    waterDetailsQueryWrapper.lambda().le(TrackingWaterDetails::getBetTime, LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
                }
            }
            //上月
            if (trackingAgencyAccountsDto.getLastmonth()!=null){
                if (trackingAgencyAccountsDto.getStartTime()==null && trackingAgencyAccountsDto.getEndTime()==null) {
                    LocalDateTime date = LocalDateTime.now();
                    waterDetailsQueryWrapper.lambda().ge(TrackingWaterDetails::getBetTime, LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN).plusMonths(-1));
                    waterDetailsQueryWrapper.lambda().le(TrackingWaterDetails::getBetTime, LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MAX).plusMonths(-1));
                }
            }
            //本月
            if (trackingAgencyAccountsDto.getThismonth()!=null){
                if (trackingAgencyAccountsDto.getStartTime()==null && trackingAgencyAccountsDto.getEndTime()==null) {
                    LocalDateTime date = LocalDateTime.now();
                    waterDetailsQueryWrapper.lambda().ge(TrackingWaterDetails::getBetTime, LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN));
                    waterDetailsQueryWrapper.lambda().le(TrackingWaterDetails::getBetTime, LocalDateTime.of(date.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX));
                }
            }

            //游戏类型
            if (trackingAgencyAccountsDto.getGameType() !=null){
                waterDetailsQueryWrapper.lambda().eq(TrackingWaterDetails::getGameType,trackingAgencyAccountsDto.getGameType());
            }
            //台桌 桌号
            if (trackingAgencyAccountsDto.getTableId() !=null){
                waterQueryWrapper2.lambda().eq(TrackingWater::getTableId,trackingAgencyAccountsDto.getTableId());
                waterQueryWrapper2.lambda().select(TrackingWater::getWaterId);
                List<TrackingWater> trackingWaters = trackingWaterMapper.selectList(waterQueryWrapper2);
                waterId = new ArrayList<>();
                if (trackingWaters!=null || trackingWaters.size()!=0){
                    for (TrackingWater trackingWater : trackingWaters) {
                        waterId.add(trackingWater.getWaterId());
                    }
                }
                if (waterId!=null || waterId.size()!=0)
                    waterDetailsQueryWrapper.lambda().in(TrackingWaterDetails::getWaterId,waterId);
            }
            // 靴号
            if (trackingAgencyAccountsDto.getBootId() !=null){
                waterQueryWrapper2.lambda().eq(TrackingWater::getBoots,trackingAgencyAccountsDto.getBootId());
                waterQueryWrapper2.lambda().select(TrackingWater::getWaterId);
                List<TrackingWater> trackingWaters = trackingWaterMapper.selectList(waterQueryWrapper2);
                waterId = new ArrayList<>();
                if (trackingWaters!=null || trackingWaters.size()!=0){
                    for (TrackingWater trackingWater : trackingWaters) {
                        waterId.add(trackingWater.getWaterId());
                    }
                }
                if (waterId!=null || waterId.size()!=0)
                    waterDetailsQueryWrapper.lambda().in(TrackingWaterDetails::getWaterId,waterId);
            }
            //下注方式
            if (trackingAgencyAccountsDto.getBetWay()!=null){
                waterQueryWrapper2.lambda().eq(TrackingWater::getBetWay,trackingAgencyAccountsDto.getBetWay());
                waterQueryWrapper2.lambda().select(TrackingWater::getWaterId);
                List<TrackingWater> trackingWaters = trackingWaterMapper.selectList(waterQueryWrapper2);
                waterId = new ArrayList<>();
                if (trackingWaters!=null || trackingWaters.size()!=0){
                    for (TrackingWater trackingWater : trackingWaters) {
                        waterId.add(trackingWater.getWaterId());
                    }
                }
                if (waterId!=null || waterId.size()!=0)
                    waterDetailsQueryWrapper.lambda().in(TrackingWaterDetails::getWaterId,waterId);
            }
            //注码
            if (trackingAgencyAccountsDto.getMonyType()!=null){
                waterQueryWrapper2.lambda().eq(TrackingWater::getMoneyType,trackingAgencyAccountsDto.getMonyType());
                waterQueryWrapper2.lambda().select(TrackingWater::getWaterId);
                List<TrackingWater> trackingWaters = trackingWaterMapper.selectList(waterQueryWrapper2);
                waterId = new ArrayList<>();
                if (trackingWaters!=null || trackingWaters.size()!=0){
                    for (TrackingWater trackingWater : trackingWaters) {
                        waterId.add(trackingWater.getWaterId());
                    }
                }
                if (waterId!=null || waterId.size()!=0)
                    waterDetailsQueryWrapper.lambda().in(TrackingWaterDetails::getWaterId,waterId);
            }
            //去重
            System.out.println(waterId+"-----+-----+");
            if (waterId!=null || "".equals(waterId)) {
                HashSet hashSet = new HashSet(waterId);
                waterId.clear();
                waterId.addAll(hashSet);
            }
            System.out.println(waterId+"----------+");
        }



        if (waterDetailsQueryWrapper!=null){
            //查询流水信息表
            trackingWaterDetails
                    = trackingWaterDetailsMapper.selectList(waterDetailsQueryWrapper);
            if (trackingWaterDetails!=null || trackingWaterDetails.size()!=0){

                //定义
                for (TrackingWaterDetails trackingWaterDetail : trackingWaterDetails) {
                    if (trackingWaterDetail.getWaterId()!=null){
                        waterQueryWrapper.lambda().eq(TrackingWater::getWaterId, trackingWaterDetail.getWaterId());
                        waterQueryWrapper.lambda().select(TrackingWater::getMoneyType);
                        TrackingWater trackingWater = trackingWaterMapper.selectOne(waterQueryWrapper);
                        if (trackingWater.getMoneyType()!=null){


                            if (trackingWater.getMoneyType()==0){

                                if (trackingWaterDetail.getWaterId()!=null && trackingWaterDetail.getAccount()!=null) {

                                    userQueryWrapper1.lambda().eq(TrackingUser::getAccount,trackingWaterDetail.getAccount());
                                    //拿到用户的 洗码率
                                    userQueryWrapper1.lambda().select(TrackingUser::getWashCodeRatio,
                                            TrackingUser::getUserType,
                                            TrackingUser::getRebatesEarnings,
                                            TrackingUser::getProportion,
                                            TrackingUser::getWashCodeRatio);

                                    TrackingUser trackingUser = trackingUserMapper.selectOne(userQueryWrapper1);
                                    //占股
                                    if (trackingUser.getUserType()!=null && trackingUser.getUserType()==1){
                                        if (trackingUser.getWashCodeRatio()!=null){
                                            RMBwashAodeAmount= RMBwashAodeAmount.add(trackingWaterDetail.getWashCodeMoney());
                                            Earnings= Earnings.add(trackingUser.getProportion().multiply(trackingWaterDetail.getWinMoney().subtract(trackingWaterDetail.getWashCodeAmount())).subtract(trackingWaterDetail.getWashCodeMoney().multiply(trackingUser.getProportion())));
                                        }
                                        //返点
                                    }else if (trackingUser.getUserType()!=null && trackingUser.getUserType()==2){
                                        RMBwashAodeAmount= RMBwashAodeAmount.add(trackingWaterDetail.getWashCodeMoney());
                                        Earnings = Earnings.add(trackingUser.getWashCodeRatio().multiply(trackingWaterDetail.getWashCodeAmount()));
                                    }

                                    //下注总金额
                                    if(trackingWaterDetail.getBetMoney()!=null)
                                        RMBbetMoney=RMBbetMoney.add(trackingWaterDetail.getBetMoney());
                                    //赢
                                    if(trackingWaterDetail.getWinMoney()!=null)
                                        RMBwashCodeAmountMAX=RMBwashCodeAmountMAX.add(trackingWaterDetail.getWinMoney());
                                    //输
                                    if(trackingWaterDetail.getWashCodeAmount()!=null)
                                        RMBwashCodeAmountMIN=RMBwashCodeAmountMIN.add(trackingWaterDetail.getWashCodeAmount());
                                }
                            }
                            if (trackingWater.getMoneyType()==1){

                                if (trackingWaterDetail.getWaterId()!=null && trackingWaterDetail.getAccount()!=null) {

                                    userQueryWrapper1.lambda().eq(TrackingUser::getAccount,trackingWaterDetail.getAccount());
                                    //拿到用户的 洗码率
                                    userQueryWrapper1.lambda().select(TrackingUser::getWashCodeRatio,
                                            TrackingUser::getUserType,
                                            TrackingUser::getRebatesEarnings,
                                            TrackingUser::getProportion,
                                            TrackingUser::getWashCodeRatio);
                                    TrackingUser trackingUser = trackingUserMapper.selectOne(userQueryWrapper1);
                                    //占股
                                    if (trackingUser.getUserType()!=null && trackingUser.getUserType()==1){
                                        if (trackingUser.getWashCodeRatio()!=null){
                                            //
                                            USDwashAodeAmount= USDwashAodeAmount.add(trackingWaterDetail.getWashCodeMoney());
                                            Earnings= Earnings.add(trackingUser.getProportion().multiply(trackingWaterDetail.getWinMoney().subtract(trackingWaterDetail.getWashCodeAmount())).subtract(trackingWaterDetail.getWashCodeMoney().multiply(trackingUser.getProportion())));
                                        }
                                        //返点
                                    }else if (trackingUser.getUserType()!=null && trackingUser.getUserType()==2){
                                        USDwashAodeAmount= USDwashAodeAmount.add(trackingWaterDetail.getWashCodeMoney());
                                        Earnings = Earnings.add(trackingUser.getRebatesEarnings().multiply(trackingWaterDetail.getWashCodeAmount()));
                                    }

                                    if(trackingWaterDetail.getBetMoney()!=null)
                                        USDbetMoney = USDbetMoney.add(trackingWaterDetail.getBetMoney());
                                    if(trackingWaterDetail.getWinMoney()!=null)
                                        USDwashCodeAmountMAX=USDwashCodeAmountMAX.add(trackingWaterDetail.getWinMoney());
                                    if(trackingWaterDetail.getWashCodeAmount()!=null)
                                        USDwashCodeAmountMIN = USDwashCodeAmountMIN.add(trackingWaterDetail.getWashCodeAmount());
                                }
                            }
                        }
                    }
                }


            }



        }
    }

    /*
        公司账目
     */
    public Map<String,Object> findCompanyAccounts(){
        //查询会员人数
        Long oneByUserType = trackingUserMapper.findOneByUserType();
        //代理人数
        Long oneByUserType1 = trackingUserMapper.findOneByCountUserType();
        //代理在线人数
        Long onLineByUserType3 = trackingUserMapper.findAllByStateAgency();
        //会员在线人数
        Long onLineByUserType = trackingUserMapper.findAllByStateVIP();
        //会员盈利
        List<Map<String, Object>> profitVIP = trackingUserMapper.findProfitVIP();
        //代理盈利
        List<Map<String, Object>> profitAgency = trackingUserMapper.findProfitAgency();
        //散客盈利


        QueryWrapper<TrackingUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(TrackingUser::getUserType, 0);
        TrackingUser trackingUser = trackingUserMapper.selectOne(userQueryWrapper);
        return null;
    }
    //实时信息
    public void finRealTime(){
        //实时信息
      /*  List<Map<String, Object>> realTimeLimit = trackingWaterMapper.findRealTimeLimit();
        if (realTimeLimit==null || realTimeLimit.size()==0)
            return;
        for (Map<String, Object> stringObjectMap : realTimeLimit) {
            if ("water_id".equals(stringObjectMap.get("water_id"))) {
            }
        }*/


    }
}
