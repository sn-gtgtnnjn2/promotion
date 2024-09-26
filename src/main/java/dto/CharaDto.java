package dto;

import java.util.Date;

public class CharaDto {
	private Integer characterId;
	private String createrId;
	private String name;
	private String nameKana;
	private String memo;
	private Boolean isLost;
	private String imagePath;
	private String imageFileName;
	private String externalLink;
	private Date entryDate;
	private Date updateDate;
	private Boolean deleteFlg;
	
	public Integer getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameKana() {
		return nameKana;
	}
	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getIsLost() {
		return isLost;
	}
	public void setIsLost(Boolean isLost) {
		this.isLost = isLost;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Boolean getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}
