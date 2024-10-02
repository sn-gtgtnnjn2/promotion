package dto;

import java.util.Date;

import bean.EventAndDetailBean;

public class EventAndDetail {
	private Integer eventId;
	private String userId;
	private String eventTitle;
	private Date eventDatetime;
	private String organizerName;
	private String organizerId;
	private String scenarioTitle;
	private String detail;
	private Date recruitmentStartDate;
	private Date recruitmentEndDate;
	private Integer memberLimit;
	private Integer openLevel;
	private Integer status;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	private Boolean cancelFlg;

	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Date getEventDatetime() {
		return eventDatetime;
	}
	public void setEventDatetime(Date eventDatetime) {
		this.eventDatetime = eventDatetime;
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
	public void setEventTitle(String event_title) {
		this.eventTitle = event_title;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public Integer getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(Integer openLevel) {
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
	public EventAndDetail(String userId, String eventTitle, String scenarioTitle, String detail) {
		super();
		this.userId = userId;
		this.eventTitle = eventTitle;
		this.scenarioTitle = scenarioTitle;
		this.detail = detail;
	}
	
	public EventAndDetail() {
		
	}
	public Boolean getCancelFlg() {
		return cancelFlg;
	}
	public void setCancelFlg(Boolean cancelFlg) {
		this.cancelFlg = cancelFlg;
	}

//	public static Integer getIsAvailable(EventAndDetail ead, int signUpUserList) {
//		boolean availableStatus = false;
//		Date nowDate = new Date();
//		if(ead.getCancelFlg()) {
//			return EventAndDetailBean.STATUS_CANCEL;
//		}
//
//		// 現在時刻が募集開始日以降、募集終了日以前であるかを判定する
//		if(nowDate.after(ead.getRecruitmentStartDate())) {
//			if(nowDate.before(ead.getRecruitmentEndDate())){
//				// 現在が有効期限内であるとき、人数に問題がないか判定
//				if(availableStatus) {
//					if(ead.getMemberLimit() <= signUpUserList) {
//						return EventAndDetailBean.STATUS_IS_AVAILABLE;
//					} else {
//						return EventAndDetailBean.STATUS_CLOSED;
//					}
//				}
//			}
//		}
//		return EventAndDetailBean.STATUS_YET;
//	}
	
	public static Integer getIsAvailable(EventAndDetail ead, int signUpUserList) {
	    Date nowDate = new Date();
	    
	    // イベントがキャンセルされている場合
	    if (ead.getCancelFlg()) {
	        return EventAndDetailBean.STATUS_CANCEL;
	    }
	    
	    // 現在時刻が募集開始日より前の場合
	    if (nowDate.before(ead.getRecruitmentStartDate())) {
	        return EventAndDetailBean.STATUS_YET;
	    }
	    
	    // 現在時刻が募集終了日より後の場合
	    if (nowDate.after(ead.getRecruitmentEndDate())) {
	        return EventAndDetailBean.STATUS_CLOSED;
	    }
	    
	    // 現在時刻が募集期間中の場合
	    if (nowDate.after(ead.getRecruitmentStartDate()) && nowDate.before(ead.getRecruitmentEndDate())) {
	        if (signUpUserList < ead.getMemberLimit()) {
	            return EventAndDetailBean.STATUS_IS_AVAILABLE;
	        } else {
	            return EventAndDetailBean.STATUS_MEMBER_LIMIT_OVER;
	        }
	    }
	    
	    // デフォルトの返り値
	    return EventAndDetailBean.STATUS_YET;
	}
	
	public static Integer getIsAvailable(Event event, int signUpUserList) {
		boolean availableStatus = false;
		Date nowDate = new Date();
		if(event.getCancelFlg()) {
			return EventAndDetailBean.STATUS_CANCEL;
		}

		// 現在時刻が募集開始日以降、募集終了日以前であるかを判定する
		if(nowDate.after(event.getRecruitmentStartDate())) {
			if(nowDate.before(event.getRecruitmentEndDate())){
				// 現在が有効期限内であるとき、人数に問題がないか判定
				if(availableStatus) {
					if(event.getMemberLimit() <= signUpUserList) {
						return EventAndDetailBean.STATUS_IS_AVAILABLE;
					} else {
						return EventAndDetailBean.STATUS_CLOSED;
					}
				}
			}
		}
		return EventAndDetailBean.STATUS_YET;
	}
}
