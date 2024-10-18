package bean;

import java.io.Serializable;

public class CharaInfoForEventDetailBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private Integer characterId;
	private String playerName;
	private String playerId;
	private String name;
	private String nameKana;
	private String imageFileName;
	private String externalLink;
	private String memo;
	private String tmpImageFilePath;
	private String imageFilePath;
	private Boolean isLoginUserOwner;	

	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTmpImageFilePath() {
		return tmpImageFilePath;
	}
	public void setTmpImageFilePath(String tmpImageFilePath) {
		this.tmpImageFilePath = tmpImageFilePath;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	public Boolean getIsLoginUserOwner() {
		return isLoginUserOwner;
	}
	public void setIsLoginUserOwner(Boolean isLoginUserOwner) {
		this.isLoginUserOwner = isLoginUserOwner;
	}
}