package com.entites;

import java.util.Date;

public class TaskComplete {
    private Integer id;

    private Integer uId;

    private Integer tId;

    private Date completeTime;

    private Integer agent;

    private Boolean iscomplete;

    private String completePassword;

    private String tName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }

    public Boolean getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(Boolean iscomplete) {
        this.iscomplete = iscomplete;
    }

    public String getCompletePassword() {
        return completePassword;
    }

    public void setCompletePassword(String completePassword) {
        this.completePassword = completePassword;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
}