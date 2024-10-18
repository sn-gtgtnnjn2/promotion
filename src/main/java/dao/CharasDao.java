package dao;

import java.sql.SQLException;
import java.util.List;

import dto.CharaDto;

public interface CharasDao {
	List<CharaDto> selectAll();
	List<CharaDto> selectByCreaterId(String userId) throws SQLException;
	CharaDto findByCharacterId(Integer characterId) throws SQLException;
	void insert(CharaDto chara) throws SQLException;
	void updateRow(CharaDto chara) throws SQLException;
	void deleteByCharacterId(Integer characterId) throws SQLException;
	List<CharaDto> selectCharasListByNamePartialMatch(String matchStr, String userId) throws SQLException;
}
