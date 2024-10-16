package bean;

public class CharaInfoMin {
    private Integer characterId;
    private String name;
    private String name_kana;
    
    
	public Integer getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_kana() {
		return name_kana;
	}
	public void setName_kana(String name_kana) {
		this.name_kana = name_kana;
	}
	
	public CharaInfoMin(Integer characterId, String name, String name_kana) {
		super();
		this.characterId = characterId;
		this.name = name;
		this.name_kana = name_kana;
	}
}
