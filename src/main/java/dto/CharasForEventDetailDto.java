package dto;

import java.util.Date;

public class CharasForEventDetailDto {
	private Integer characterId;
	private Integer eventId;
	private String playerId;
	private String playerName;
	private Date entryDatetime;
	private Date updateDatetime;
	private Boolean deleteFlg;
	private String name;
	private String nameKana;
	private String memo;
	private Boolean isLost;
	private String imageFilename;
	private String imagePath;
	private String externalLink;
	
	public Integer getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	
	
	public CharasForEventDetailDto(Integer characterId, Integer eventId, String playerId, Date entryDatetime,
			Date updateDatetime, Boolean deleteFlg, String name, String nameKana, String memo, Boolean isLost,
			String imageFilename, String imagePath, String externalLink) {
		super();
		this.characterId = characterId;
		this.eventId = eventId;
		this.playerId = playerId;
		this.entryDatetime = entryDatetime;
		this.updateDatetime = updateDatetime;
		this.deleteFlg = deleteFlg;
		this.name = name;
		this.nameKana = nameKana;
		this.memo = memo;
		this.isLost = isLost;
		this.imageFilename = imageFilename;
		this.imagePath = imagePath;
		this.externalLink = externalLink;
	}
	public CharasForEventDetailDto() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
