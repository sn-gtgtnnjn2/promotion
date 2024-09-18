package dto;

import java.util.Date;

public class Event {
	private Integer event_id;
	private String user_id;
	private String event_title;
	private String organizerName;
	private String organizerId;
	private String scenarioTitle;
	private Date recruitmentStartDate;
	private Date recruitmentEndDate;
	private Integer memberLimit;
	private Integer openLevel;
	private Integer status;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	
	public Integer getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEvent_title() {
		return event_title;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public String getOrganizerId() {
		return organizerId;
	}
	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
	}
	public String getScenarioTitle() {
		return scenarioTitle;
	}
	public void setScenarioTitle(String scenarioTitle) {
		this.scenarioTitle = scenarioTitle;
	}
	public Date getRecruitmentStartDate() {
		return recruitmentStartDate;
	}
	public void setRecruitmentStartDate(Date recruitmentStartDate) {
		this.recruitmentStartDate = recruitmentStartDate;
	}
	public Date getRecruitmentEndDate() {
		return recruitmentEndDate;
	}
	public void setRecruitmentEndDate(Date recruitmentEndDate) {
		this.recruitmentEndDate = recruitmentEndDate;
	}
	public Integer getMemberLimit() {
		return memberLimit;
	}
	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}
	public Integer getopenLevel() {
		return openLevel;
	}
	public void setopenLevel(Integer openLevel) {
		this.openLevel = openLevel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getEntryDatetime() {
		return entryDatetime;
	}
	public void setEntryDatetime(Date entryDatetime) {
		this.entryDatetime = entryDatetime;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public Boolean getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
