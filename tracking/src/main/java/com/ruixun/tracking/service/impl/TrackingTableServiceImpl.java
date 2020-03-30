package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruixun.tracking.common.utils.JudgeEmpty;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.dao.TrackingTableMapper;
import com.ruixun.tracking.dao.TrackingUserMapper;
import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.TrackingTable;
import com.ruixun.tracking.entity.TrackingUser;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.service.IDictionaryItemService;
import com.ruixun.tracking.service.ITrackingTableService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class TrackingTableServiceImpl extends ServiceImpl<TrackingTableMapper, TrackingTable> implements ITrackingTableService {

    @Autowired
    private TrackingTableMapper tableMapper;
    @Autowired
    private TrackingWaterMapper waterMapper;
    @Autowired
    private TrackingUserMapper userMapper;
    @Autowired
    private TrackingWaterDetailsMapper waterDetailsMapper;
    @Autowired
    private IDictionaryItemService dictionaryItemService;

    @Override
    public Result findTablesInfo(TrackingWater trackingWater, Integer page, Integer size) {
        QueryWrapper<TrackingWater> wrapper_water = new QueryWrapper<TrackingWater>();
        if (trackingWater.getCreateTime() != null && trackingWater.getEndTime() != null) {
            wrapper_water.lambda()
                    .ge(TrackingWater::getEndTime, trackingWater.getCreateTime())
                    .le(TrackingWater::getEndTime, trackingWater.getEndTime());
        } else {
            return ResultResponseUtil.ok().msg("请选择开始时间和结束时间");
        }
        if (!JudgeEmpty.isEmpty(trackingWater.getTableId())) {
            wrapper_water.lambda().eq(TrackingWater::getTableId, trackingWater.getTableId());
        }
        if (!JudgeEmpty.isEmpty(trackingWater.getBoots())) {
            wrapper_water.lambda().eq(TrackingWater::getBoots, trackingWater.getBoots());
        }
        if (!JudgeEmpty.isEmpty(trackingWater.getGameType())) {
            wrapper_water.lambda().eq(TrackingWater::getGameType, trackingWater.getGameType());
        }
        if (!JudgeEmpty.isEmpty(trackingWater.getMoneyType())) {
            wrapper_water.lambda().eq(TrackingWater::getMoneyType, trackingWater.getMoneyType());
        }
        if (!JudgeEmpty.isEmpty(trackingWater.getBetWay())) {
            wrapper_water.lambda().eq(TrackingWater::getBetWay, trackingWater.getBetWay());
        }

        /*查询所有流水*/
        List<TrackingWater> trackingWaters = waterMapper.selectList(wrapper_water);
        if (trackingWaters == null || trackingWaters.size() == 0) {
            return ResultResponseUtil.ok().msg("抱歉：没有查到数据");
        }
        /*流水中桌号唯一*/
        ArrayList<String> tablesId = new ArrayList<>();
        for (int i = 0; i < trackingWaters.size(); i++) {
            if (!tablesId.contains(trackingWaters.get(i).getTableId())) {
                tablesId.add(trackingWaters.get(i).getTableId());
            }
        }

        /*一个桌号，对应多个流水号，一个流水号对应多个流水详细
         * map 的key存桌号，value存对应的流水号*/
        Map<String, List<String>> tableId_waterIds = new HashMap<String, List<String>>();
        for (int i = 0; i < tablesId.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < trackingWaters.size(); j++) {
                if (tablesId.get(i) == trackingWaters.get(j).getTableId()) {
                    temp.add(trackingWaters.get(j).getWaterId());
                }
            }
            tableId_waterIds.put(tablesId.get(i), temp);
        }

        /*流水号唯一*/
        List<String> water_ids = new ArrayList<>();
        for (int i = 0; i < trackingWaters.size(); i++) {
            if (!water_ids.contains(trackingWaters.get(i).getWaterId())) {
                water_ids.add(trackingWaters.get(i).getWaterId());
            }
        }

        /* resultMapList 存放前台所需数据 */
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tablesId.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            /*数据初始化*/
            //查询该桌子对应的流水号
            List<String> temp_waterIds = tableId_waterIds.get(tablesId.get(i));
            /*根据流水号查询流水表*/
            QueryWrapper<TrackingWater> wrapper_TrackingWater = new QueryWrapper<TrackingWater>();
            wrapper_TrackingWater.lambda().in(TrackingWater::getWaterId, temp_waterIds);
            List<TrackingWater> trackingWater_temp = waterMapper.selectList(wrapper_TrackingWater);
            //根据流水号查询流水详细
            QueryWrapper<TrackingWaterDetails> wrapper_TrackingWaterDetails = new QueryWrapper<TrackingWaterDetails>();
            wrapper_TrackingWaterDetails.lambda().in(TrackingWaterDetails::getWaterId, temp_waterIds);
            List<TrackingWaterDetails> trackingWaterDetails = waterDetailsMapper.selectList(wrapper_TrackingWaterDetails);
            /*1 table类型放入map*/
            QueryWrapper<TrackingTable> wrapper_table = new QueryWrapper<TrackingTable>();
            wrapper_table.lambda().eq(TrackingTable::getTableId, tablesId.get(i));
            map.put("tableType", dictionaryItemService.getGameCN("table",tableMapper.selectOne(wrapper_table).getTableType()));
            /*2. tableId 放入map  */
            map.put("tableId", tablesId.get(i));
            /*3. 总压放入map*/
            BigDecimal zongYa = new BigDecimal(0);
            for (int j = 0; j < trackingWaterDetails.size(); j++) {
                zongYa = zongYa.add(trackingWaterDetails.get(j).getBetMoney());
            }
            map.put("vipZongYa", zongYa);
            /*4. 总输赢放入map*/
            BigDecimal zongShuYing = new BigDecimal(0);
            //遍历流水表，查询桌的总输赢
            for (int k = 0; k < trackingWaters.size(); k++) {
                zongShuYing=zongShuYing.add(trackingWaters.get(k).getProfit());
            }
            map.put("vipZongShuYing", zongShuYing);
            /*5. 和放入map*/
            BigDecimal he = new BigDecimal(0);
            for (int i1 = 0; i1 < trackingWaterDetails.size(); i1++) {
                if(trackingWaterDetails.get(i1).getWinMoney()!=null&&trackingWaterDetails.get(i1).getBetTarget()==2){
                    he=he.add(trackingWaterDetails.get(i1).getWinMoney());
                }
            }
            map.put("he", he);
            /*6. 对子放入map*/
            BigDecimal duizi = new BigDecimal(0);
            for (int i1 = 0; i1 < trackingWaterDetails.size(); i1++) {
                if((trackingWaterDetails.get(i1).getBetTarget()==4)||(trackingWaterDetails.get(i1).getBetTarget()==5)&&(trackingWaterDetails.get(i1).getWinMoney()!=null)){
                    duizi= duizi.add(trackingWaterDetails.get(i1).getWinMoney());
                }
            }
            map.put("duiZi", duizi);
            /*7. 洗码量放入map*/
            BigDecimal xiMaLiang = new BigDecimal(0);
            for (int i1 = 0; i1 < trackingWaterDetails.size(); i1++) {
                xiMaLiang=xiMaLiang.add(trackingWaterDetails.get(i1).getWashCodeAmount());
            }
            /*8. 放入公司盈亏*/
            map.put("gongSiYingKui", -zongShuYing.doubleValue());
            /*9. 放入纯利润*/
            map.put("chunLiRun", -zongShuYing.doubleValue());
            /*存入结果集*/
            resultMapList.add(map);
        }
        PageHelper.startPage(page, size);
        PageInfo<Map<String, Object>> tablesInfo = new PageInfo<Map<String, Object>>(resultMapList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("tablesInfo",tablesInfo);
        map.put("wrapper_water",tableId_waterIds);
        return ResultResponseUtil.ok().data(map);
    }

    @Override
    public Result findTablesDetailsInfo(List<String> watersId) {
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < watersId.size(); i++) {
            /*上左*/
            HashMap<String, Object> table_map = new HashMap<>();
            /*1. 放入台桌号*/
            QueryWrapper<TrackingWater> wrapper_water = new QueryWrapper<TrackingWater>();
            wrapper_water.lambda().eq(TrackingWater::getWaterId,watersId.get(i));
            TrackingWater trackingWater = waterMapper.selectOne(wrapper_water);
            table_map.put("table_id",trackingWater.getTableId());
            /*2. 放入时间*/
            table_map.put("time",trackingWater.getEndTime());
            /*3. 放入靴号*/
            table_map.put("boots",trackingWater.getBoots());
            /*4. 放入铺号*/
            table_map.put("times",trackingWater.getTimes());
            /*5. 放入总压*/
            //根据流水号查询流水详细
            QueryWrapper<TrackingWaterDetails> wrapper_TrackingWaterDetails = new QueryWrapper<TrackingWaterDetails>();
            wrapper_TrackingWaterDetails.lambda().in(TrackingWaterDetails::getWaterId, watersId);
            List<TrackingWaterDetails> trackingWaterDetails = waterDetailsMapper.selectList(wrapper_TrackingWaterDetails);
            BigDecimal zongYa = new BigDecimal(0);
            for (int j = 0; j < trackingWaterDetails.size(); j++) {
                zongYa=zongYa.add(trackingWaterDetails.get(j).getBetMoney());
            }
            table_map.put("vipZongYa", zongYa);
            /*6. 放入公司盈亏*/
            /*根据流水号查询流水表*/
            QueryWrapper<TrackingWater> wrapper_TrackingWater = new QueryWrapper<TrackingWater>();
            wrapper_TrackingWater.lambda().eq(TrackingWater::getWaterId, watersId.get(i));
            TrackingWater water = waterMapper.selectOne(wrapper_TrackingWater);
            table_map.put("gongSiYingKui",water.getProfit());
            /*7. 放入保险*/
            table_map.put("insurance",water.getInsurance());
            /*8. 放入开牌结果*/
            table_map.put("result",dictionaryItemService.getGameCN("game_result",Integer.valueOf(water.getResult())));
            /*9. 放入参与会员*/
            String vipAccounts = "";
            for (int i1 = 0; i1 < trackingWaterDetails.size(); i1++) {
                vipAccounts = vipAccounts+","+trackingWaterDetails.get(i1).getAccount();
            }
            table_map.put("vipAccounts",vipAccounts);
            /*下右*/
            List<Map<String,Object>> table_list = new ArrayList<Map<String,Object>>();
            for (int i1 = 0; i1 < trackingWaterDetails.size(); i1++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("account",trackingWaterDetails.get(i1).getAccount());
                QueryWrapper<TrackingUser> wrapper_user = new QueryWrapper<TrackingUser>();
                wrapper_user.lambda().eq(TrackingUser::getAccount,trackingWaterDetails.get(i1).getAccount());
                TrackingUser trackingUser = userMapper.selectOne(wrapper_user);
                map.put("cardId",trackingUser.getCardId());
                map.put("name",trackingUser.getUsername());
                map.put("betTarget",trackingWaterDetails.get(i1).getBetTarget());
                map.put("insurance",trackingWaterDetails.get(i1).getInsurance());
                map.put("zongYing",trackingWaterDetails.get(i1).getWinMoney());
                map.put("money_type",dictionaryItemService.getGameCN("currency",trackingWater.getMoneyType()));
                map.put("bet_way",dictionaryItemService.getGameCN("bet",trackingWater.getBetWay()));
                table_list.add(map);
            }
            table_map.put("table_details",table_list);
            resultMapList.add(table_map);
        }
        return ResultResponseUtil.ok().data(resultMapList);
    }

}
