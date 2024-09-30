package bean;

import java.io.Serializable;
import java.util.Date;

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
	private Integer status;
	
	public static final int STATUS_YET = 4;
	public static final int STATUS_IS_AVAILABLE = 0;
	public static final int STATUS_CLOSED = 1;
	public static final int STATUS_DONE = 2;
	public static final int STATUS_CANCEL = 3;
	
	
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
		case STATUS_YET :
			retStatus = "募集前";
			break;
		default :
			retStatus = "";
		}
		return retStatus;
	}
}
