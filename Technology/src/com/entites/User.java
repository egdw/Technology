package com.entites;

import java.util.Date;

public class User {
	private Integer uId;

	private String uName;

	private String uPassword;

	private Integer auth;

	private Date creadtDate;

	private Integer task;

	private String taskName;

	private Integer completeNums;

	public Integer getuId() {
		return uId;
	}

	public Integer getCompleteNums() {
		return completeNums;
	}

	public void setCompleteNums(Integer completeNums) {
		this.completeNums = completeNums;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public Integer getAuth() {
		return auth;
	}

	public void setAuth(Integer auth) {
		this.auth = auth;
	}

	public Date getCreadtDate() {
		return creadtDate;
	}

	public void setCreadtDate(Date creadtDate) {
		this.creadtDate = creadtDate;
	}

	public Integer getTask() {
		return task;
	}

	public void setTask(Integer task) {
		this.task = task;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uPassword="
				+ uPassword + ", auth=" + auth + ", creadtDate=" + creadtDate
				+ ", task=" + task + ", completeNums=" + completeNums + "]";
	}

}