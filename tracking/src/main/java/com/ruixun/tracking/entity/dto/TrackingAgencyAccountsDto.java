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
    @ApiModelProperty(value = "台桌类型")
    private Integer tableType;
    @ApiModelProperty(value = "注码")
    private String noteId;
    @ApiModelProperty(value = "下注方式")
    private String betType;
    @ApiModelProperty(value = "代理卡号")
    private String cardId;
    @ApiModelProperty(value = "代理账号")
    private String account;
    @ApiModelProperty(value = "代理名字")
    private String username;

    public TrackingAgencyAccountsDto(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime yesterday, LocalDateTime today, Integer thismonth, LocalDateTime lastmonth, Integer all, String tableId, String bootId, Integer tableType, String noteId, String betType, String cardId, String account, String username) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.yesterday = yesterday;
        this.today = today;
        this.thismonth = thismonth;
        this.lastmonth = lastmonth;
        this.all = all;
        this.tableId = tableId;
        this.bootId = bootId;
        this.tableType = tableType;
        this.noteId = noteId;
        this.betType = betType;
        this.cardId = cardId;
        this.account = account;
        this.username = username;
    }

    public TrackingAgencyAccountsDto(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TrackingAgencyAccountsDto() {
    }

    public TrackingAgencyAccountsDto(LocalDateTime startTime, String tableId, String bootId) {
        this.startTime = startTime;
        this.tableId = tableId;
        this.bootId = bootId;
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

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
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
                ", tableType=" + tableType +
                ", noteId='" + noteId + '\'' +
                ", betType='" + betType + '\'' +
                ", cardId='" + cardId + '\'' +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}