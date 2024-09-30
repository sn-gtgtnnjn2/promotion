package dto;

import java.util.Date;

public class Event {
	private Integer eventId;
	private String userId;
	private String eventTitle;
	private Date eventDate;
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
	private Boolean cancelFlg;
	private String organizerImageString;
	private Integer currentSignUpNum;
	private Integer currentApprovedNum;
	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
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
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Integer getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(Integer openLevel) {
		this.openLevel = openLevel;
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
	public String getOrganizerImageString() {
		return organizerImageString;
	}
	public void setOrganizerImageString(String organizerImageString) {
		this.organizerImageString = organizerImageString;
	}
	public Boolean getCancelFlg() {
		return cancelFlg;
	}
	public void setCancelFlg(Boolean cancelFlg) {
		this.cancelFlg = cancelFlg;
	}
	public Integer getCurrentSignUpNum() {
		return currentSignUpNum;
	}
	public void setCurrentSignUpNum(Integer currentSignUpNum) {
		this.currentSignUpNum = currentSignUpNum;
	}
	public Integer getCurrentApprovedNum() {
		return currentApprovedNum;
	}
	public void setCurrentApprovedNum(Integer currentApprovedNum) {
		this.currentApprovedNum = currentApprovedNum;
	}


}
