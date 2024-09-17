package dto;

import java.util.Date;

public class EventDetail {
	private Integer event_id;
	private String detail;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;

	public Integer getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
