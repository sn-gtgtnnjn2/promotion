package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EventAndDetailBean implements Serializable{
    private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String userId;
	private String eventTitle;
	private Date eventDate;
	private String organizerName;
	private String organizerId;
	private String scenarioTitle;
	private String detail;
	private Date recruitmentStartDate;
	private Date recruitmentEndDate;
	private Integer memberLimit;
	private Integer openLevel;
	private Integer status;	// DtoからBeanに格納されるタイミングでセットされる
	private List<String> userRejectList;
	private Boolean isAvailableUser;
	private Boolean organizerFlg;
	private Boolean cancelFlg;
	
	public static final int STATUS_YET = 4;
	public static final int STATUS_IS_AVAILABLE = 0;
	public static final int STATUS_CLOSED = 1;
	public static final int STATUS_DONE = 2;
	public static final int STATUS_CANCEL = 3;
	public static final int STATUS_MEMBER_LIMIT_OVER = 5;
	
	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Date getEventDate() {
		return eventDate;
	}
//	public String getFormattedEventDate() {
//		return GeneralFormatter.toISO8601(eventDate);
//	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getRecruitmentStartDate() {
		return recruitmentStartDate;
	}
//	public String getFormattedRecruitmentStartDate() {
//		return GeneralFormatter.toISO8601(recruitmentStartDate);
//	}
	public void setRecruitmentStartDate(Date recruitmentStartDate) {
		this.recruitmentStartDate = recruitmentStartDate;
	}
	public Date getRecruitmentEndDate() {
		return recruitmentEndDate;
	}
//	public String getFormattedRecruitmentEndDate() {
//		return GeneralFormatter.toISO8601(recruitmentEndDate);
//	}
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

	public String getStatusName() {
		String retStatus = null;
		switch(status) {
		case STATUS_IS_AVAILABLE :
			retStatus = "募集中";
			break;
		case STATUS_CLOSED :
			retStatus = "開催待ち";
			break;
		case STATUS_DONE :
			retStatus = "終了";
			break;
		case STATUS_CANCEL :
			retStatus = "中止";
			break;
		case STATUS_MEMBER_LIMIT_OVER :
			retStatus = "定員オーバー";
			break;
		case STATUS_YET :
			retStatus = "募集前";
			break;
		default :
			retStatus = "";
		}
		return retStatus;
	}
	public List<String> getUserRejectList() {
		return userRejectList;
	}
	public void setUserRejectList(List<String> userRejectList) {
		this.userRejectList = userRejectList;
	}

	public Boolean getIsAvailableUser() {
		return isAvailableUser;
	}
	public void setIsAvailableUser(Boolean isAvailableUser) {
		this.isAvailableUser = isAvailableUser;
	}
//	public Boolean getOverallStatus() {
////		Boolean ret = false;
////		if(isAvailableUser && (status == Integer.valueOf(STATUS_IS_AVAILABLE))) {
////			ret = true;
////		}
////		return ret;
//		if(isAvailableUser) {
//			return false;
//		}
//		return true;
//	}
	
	public Boolean getOverallStatus() {
	    if (isAvailableUser != null && status != null && isAvailableUser && (status == Integer.valueOf(STATUS_IS_AVAILABLE))) {
	        return true;
	    }
	    return false;
	}
	public Boolean getOrganizerFlg() {
		return organizerFlg;
	}
	public void setOrganizerFlg(Boolean organizerFlg) {
		this.organizerFlg = organizerFlg;
	}
	public Boolean getCancelFlg() {
		return cancelFlg;
	}
	public void setCancelFlg(Boolean cancelFlg) {
		this.cancelFlg = cancelFlg;
	}
}
