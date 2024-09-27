package dto;

import java.util.Date;

public class Profile {
	private String userId;
	private String imagePath;
	private String text;
	private String Base64Data;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	private String imgFileType;
	
	public Profile(String userId, String imagePath, String text, String imgFileType) {
		this.userId = userId;
		this.imagePath = imagePath;
		this.text = text;
		this.imagePath = imgFileType;
	}
	
	public Profile(String userId, String imagePath, String text) {
		this.userId = userId;
		this.imagePath = imagePath;
		this.text = text;
	}
	public Profile() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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

	public String getBase64Data() {
		return Base64Data;
	}

	public void setBase64Data(String base64Data) {
		Base64Data = base64Data;
	}

	public String getImgFileType() {
		return imgFileType;
	}

	public void setImgFileType(String imgFileType) {
		this.imgFileType = imgFileType;
	}
}

