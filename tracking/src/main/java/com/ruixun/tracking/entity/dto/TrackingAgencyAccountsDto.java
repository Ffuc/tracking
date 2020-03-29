package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Date;

@ApiModel(value="TrackingAgencyAccountsDto对象", description="接收json的java类")
public class TrackingAgencyAccountsDto {
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "昨天")
    private LocalDateTime yesterday;
    @ApiModelProperty(value = "今天")
    private LocalDateTime today;
    @ApiModelProperty(value = "本月")
    private Integer thismonth;
    @ApiModelProperty(value = "上月")
    private LocalDateTime lastmonth;
    @ApiModelProperty(value = "全部")
    private Integer all;
    @ApiModelProperty(value = "台桌号")
    private String tableId;
    @ApiModelProperty(value = "靴号")
    private String bootId;
    @ApiModelProperty(value = "注码")
    private String monyType;
    @ApiModelProperty(value = "游戏类型")
    private String gameType;
    @ApiModelProperty(value = "下注方式")
    private String betWay;
    @ApiModelProperty(value = "代理卡号")
    private String cardId;
    @ApiModelProperty(value = "代理账号")
    private String account;
    @ApiModelProperty(value = "代理名字")
    private String username;
    @ApiModelProperty(value = "分页")
    private Integer current;

    public TrackingAgencyAccountsDto() {
    }

    public TrackingAgencyAccountsDto(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime yesterday, LocalDateTime today, Integer thismonth, LocalDateTime lastmonth, Integer all, String tableId, String bootId, String monyType, String gameType, String betWay, String cardId, String account, String username, Integer current) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.yesterday = yesterday;
        this.today = today;
        this.thismonth = thismonth;
        this.lastmonth = lastmonth;
        this.all = all;
        this.tableId = tableId;
        this.bootId = bootId;
        this.monyType = monyType;
        this.gameType = gameType;
        this.betWay = betWay;
        this.cardId = cardId;
        this.account = account;
        this.username = username;
        this.current = current;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getYesterday() {
        return yesterday;
    }

    public void setYesterday(LocalDateTime yesterday) {
        this.yesterday = yesterday;
    }

    public LocalDateTime getToday() {
        return today;
    }

    public void setToday(LocalDateTime today) {
        this.today = today;
    }

    public Integer getThismonth() {
        return thismonth;
    }

    public void setThismonth(Integer thismonth) {
        this.thismonth = thismonth;
    }

    public LocalDateTime getLastmonth() {
        return lastmonth;
    }

    public void setLastmonth(LocalDateTime lastmonth) {
        this.lastmonth = lastmonth;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getBootId() {
        return bootId;
    }

    public void setBootId(String bootId) {
        this.bootId = bootId;
    }

    public String getMonyType() {
        return monyType;
    }

    public void setMonyType(String monyType) {
        this.monyType = monyType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getBetWay() {
        return betWay;
    }

    public void setBetWay(String betWay) {
        this.betWay = betWay;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCurrent() {
        if (this.current==null)
            current=1;
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "TrackingAgencyAccountsDto{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", yesterday=" + yesterday +
                ", today=" + today +
                ", thismonth=" + thismonth +
                ", lastmonth=" + lastmonth +
                ", all=" + all +
                ", tableId='" + tableId + '\'' +
                ", bootId='" + bootId + '\'' +
                ", monyType='" + monyType + '\'' +
                ", gameType='" + gameType + '\'' +
                ", betWay='" + betWay + '\'' +
                ", cardId='" + cardId + '\'' +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", current='" + current + '\'' +
                '}';
    }
}