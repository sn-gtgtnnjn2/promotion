package dao;

import java.sql.SQLException;
import java.util.List;

import dto.ScenarioEntriedCharaDto;

public interface ScenarioEntriedCharaDao {

	List<ScenarioEntriedCharaDto> selectAll();
	List<ScenarioEntriedCharaDto> selectByCreaterId(String userId) throws SQLException;
	ScenarioEntriedCharaDto findByCharacterId(Integer characterId) throws SQLException;
	void insert(ScenarioEntriedCharaDto chara) throws SQLException;
	void updateRowByCharaAndEventId(ScenarioEntriedCharaDto chara) throws SQLException;
	void deleteByCharaAndEventId(Integer characterId, Integer eventId) throws SQLException;
	
}
