package dao;

import java.sql.SQLException;
import java.util.List;

import dto.CharasForEventDetailDto;
import dto.ScenarioEntriedCharaDto;

public interface ScenarioEntriedCharaDao {

	List<ScenarioEntriedCharaDto> selectAll();
	List<ScenarioEntriedCharaDto> selectByCreaterId(String userId) throws SQLException;
	ScenarioEntriedCharaDto findByCharacterId(Integer characterId) throws SQLException;
	void insert(ScenarioEntriedCharaDto chara) throws SQLException;
	void updateRowByCharaAndEventId(ScenarioEntriedCharaDto chara) throws SQLException;
	void deleteByCharaUserAndEventId(Integer characterId, Integer eventId, String userId) throws SQLException, Exception;
	List<CharasForEventDetailDto> getEventEntryCharas(Integer eventId) throws SQLException;
	Boolean checkExistByKeys(Integer eventId, Integer characterId);
	void updateDeleteFlg(boolean updateStatus, Integer characterId, Integer eventId, String userId);
	
}
