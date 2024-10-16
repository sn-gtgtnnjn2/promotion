package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dto.ScenarioEntriedCharaDto;

public class ScenarioEntriedCharaDaoImpl implements ScenarioEntriedCharaDao{
	
	DataSource ds;

	public ScenarioEntriedCharaDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<ScenarioEntriedCharaDto> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "SELECT "
				+ " character_id"
				+ " ,event_id"
				+ " ,player_id"
				+ " ,entry_datetime"
				+ " ,update_datetime"
				+ " ,delete_flg"
				+ " FROM scenario_entried_chara";
		return null;
	}

	@Override
	public List<ScenarioEntriedCharaDto> selectByCreaterId(String userId) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ScenarioEntriedCharaDto findByCharacterId(Integer characterId) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void insert(ScenarioEntriedCharaDto chara) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO scenario_entried_chara ("
				+ " character_id"
				+ " ,event_id"
				+ " ,player_id"
				+ ") values ( ?, ?, ? )";
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, chara.getCharacterId());
			stmt.setInt(2, chara.getEventId());
			stmt.setString(3, chara.getPlayerId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateRowByCharaAndEventId(ScenarioEntriedCharaDto chara) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteByCharaAndEventId(Integer characterId, Integer eventId) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
