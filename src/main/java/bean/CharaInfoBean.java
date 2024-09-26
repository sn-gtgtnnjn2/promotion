package bean;

import java.io.Serializable;

public class CharaInfoBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String characterName;
	private String characterNameFurigana;
	private String imageFileName;
	private String externalLink;
	private String memo;
	private String tmpImageFilePath;
	private String imageFilePath;
	
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getCharacterNameFurigana() {
		return characterNameFurigana;
	}
	public void setCharacterNameFurigana(String characterNameFurigana) {
		this.characterNameFurigana = characterNameFurigana;
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
}
