package dto;

import java.util.Date;

public class EntryApproval {
	private Integer eventID;
	private String signUpUserId;
	private Integer approvalStatus;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	
	public Integer getEventID() {
		return eventID;
	}
	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	public String getSignUpUserId() {
		return signUpUserId;
	}
	public void setSignUpUserId(String signUpUserId) {
		this.signUpUserId = signUpUserId;
	}
	public Integer getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
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
