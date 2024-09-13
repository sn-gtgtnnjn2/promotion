package dto;

import java.util.Date;

public class PreUser {
	private Integer id;
	private String email;
	private String token;
	private Boolean enabled;
	private Date expiresAt;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	
	PreUser(){
		
	}

	public PreUser(Integer id, String email, String token, Boolean enabled, Date expiresAt, Date entryDatetime,
			Date updateDatetime, Boolean deleteFlg) {
		super();
		this.id = id;
		this.email = email;
		this.token = token;
		this.enabled = enabled;
		this.expiresAt = expiresAt;
		this.entryDatetime = entryDatetime;
		this.updateDatetime = updateDatetime;
		this.deleteFlg = deleteFlg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
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
