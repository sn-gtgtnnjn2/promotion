package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.CharasForEventDetailDto;
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
			throw e;
		}

	}

	@Override
	public void updateRowByCharaAndEventId(ScenarioEntriedCharaDto chara) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteByCharaUserAndEventId(Integer characterId, Integer eventId, String userId) throws Exception {
		String sql = "SELECT count(*) as target from scenario_entried_chara"
				+ " WHERE character_id = ?"
				+ " AND event_id = ?"
				+ " AND player_id = ?"
				+ " AND delete_flg = 0";
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, characterId);
			stmt.setInt(2, eventId);
			stmt.setString(3, userId);
System.out.println("sql->" + sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Integer targetCount = rs.getInt("target");
				if(targetCount == 1) {
System.out.println("targetCount->" + targetCount);
					updateDeleteFlg(true, characterId, eventId, userId);
				} else {
					throw new Exception("異常");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(getClass().getName() + "正常終了");
	}

	@Override
	public List<CharasForEventDetailDto> getEventEntryCharas(Integer eventId) throws SQLException {
		String sql = "SELECT "
				+ " sec.character_id"
				+ " ,sec.event_id"
				+ " ,sec.player_id"
				+ " ,u.user_name"
				+ " ,sec.entry_datetime"
				+ " ,sec.update_datetime"
				+ " ,sec.delete_flg"
				
				+ ", c.name"
				+ ", c.name_kana"
				+ ", c.memo"
				+ ", c.is_lost"
				+ ", c.image_filename"
				+ ", c.image_path"
				+ ", c.external_link"

				+ " FROM scenario_entried_chara sec"
				+ " INNER JOIN charas c ON sec.character_id = c.character_id"
				+ " INNER JOIN user u ON sec.player_id = u.user_id"
				+ " WHERE sec.event_id = ?"
				+ " AND sec.delete_flg = false";
		System.out.println(getClass().getName() + "->" + sql);
		List<CharasForEventDetailDto> charaList = new ArrayList<>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CharasForEventDetailDto cfedDto = new CharasForEventDetailDto();
				cfedDto.setCharacterId(rs.getInt("character_id"));
				cfedDto.setEventId(rs.getInt("event_id"));
				cfedDto.setPlayerId(rs.getString("player_id"));
				cfedDto.setPlayerName(rs.getString("user_name"));
				cfedDto.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				cfedDto.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				cfedDto.setDeleteFlg(rs.getBoolean("delete_flg"));
				cfedDto.setName(rs.getString("name"));
				cfedDto.setNameKana(rs.getString("name_kana"));
				cfedDto.setMemo(rs.getString("memo"));
				cfedDto.setIsLost(rs.getBoolean("is_lost"));
				cfedDto.setImageFilename(rs.getString("image_filename"));
				cfedDto.setImagePath(rs.getString("image_path"));
				cfedDto.setExternalLink(rs.getString("external_link"));
				charaList.add(cfedDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return charaList;
	}

	@Override
	public Boolean checkExistByKeys(Integer eventId, Integer characterId) {
		String sql = "SELECT count(*) as recordCount FROM scenario_entried_chara"
				+ " WHERE event_id = ?"
				+ " AND  character_id = ?"
				+ " AND delete_flg = 1";
		boolean ret = false;
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			stmt.setInt(2, characterId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int count = rs.getInt("recordCount");
				if(count > 0) {
					ret = true;
				}
			}
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void updateDeleteFlg(boolean updateStatus, Integer characterId, Integer eventId, String userId) {
		System.out.println(getClass()+"updateDeleteflg:" + updateStatus);
		String sql2 = "UPDATE scenario_entried_chara SET delete_flg = ?"
				+ " WHERE character_id = ?"
				+ " AND event_id = ?"
				+ " AND player_id = ?";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			stmt2.setBoolean(1, updateStatus);
			stmt2.setInt(2, characterId);
			stmt2.setInt(3, eventId);
			stmt2.setString(4, userId);
			System.out.println("stmt2->" + sql2);
			stmt2.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
