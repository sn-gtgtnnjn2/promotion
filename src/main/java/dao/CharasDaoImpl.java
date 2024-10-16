package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.CharaDto;

public class CharasDaoImpl implements CharasDao{
	private DataSource ds;
	
	CharasDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public List<CharaDto> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<CharaDto> selectByCreaterId(String createrId) throws SQLException {
		String sql = "SELECT "
				+ " character_id"
				+ ", creater_id"
				+ ", name"
				+ ", name_kana"
				+ ", memo"
				+ ", is_lost"
				+ ", image_filename"
				+ ", image_path"
				+ ", external_link"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM charas"
				+ " WHERE creater_id = ?";
		List<CharaDto> charaList = new ArrayList<CharaDto>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, createrId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CharaDto cd = new CharaDto();
				cd.setCharacterId(rs.getInt("character_id"));
				cd.setCreaterId(rs.getString("creater_id"));
				cd.setName(rs.getString("name"));
				cd.setNameKana(rs.getString("name_kana"));
				cd.setMemo(rs.getString("memo"));
				cd.setIsLost(rs.getBoolean("is_lost"));
				cd.setImageFileName(rs.getString("image_filename"));
				cd.setImagePath(rs.getString("image_path"));
				cd.setExternalLink(rs.getString("external_link"));
				cd.setEntryDate(rs.getTimestamp("entry_datetime"));
				cd.setUpdateDate(rs.getTimestamp("update_datetime"));
				cd.setDeleteFlg(rs.getBoolean("delete_flg"));
				charaList.add(cd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return charaList;
	}

	@Override
	public CharaDto findByCharacterId(Integer characterId) throws SQLException {
		String sql = "SELECT "
				+ " character_id"
				+ ", creater_id"
				+ ", name"
				+ ", name_kana"
				+ ", memo"
				+ ", is_lost"
				+ ", image_filename"
				+ ", image_path"
				+ ", external_link"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM charas"
				+ " WHERE character_id = ?";
		CharaDto charaInfo = new CharaDto();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, characterId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				charaInfo.setCharacterId(rs.getInt("character_id"));
				charaInfo.setCreaterId(rs.getString("creater_id"));
				charaInfo.setName(rs.getString("name"));
				charaInfo.setNameKana(rs.getString("name_kana"));
				charaInfo.setMemo(rs.getString("memo"));
				charaInfo.setIsLost(rs.getBoolean("is_lost"));
				charaInfo.setImageFileName(rs.getString("image_filename"));
				charaInfo.setImagePath(rs.getString("image_path"));
				charaInfo.setExternalLink(rs.getString("external_link"));
				charaInfo.setEntryDate(rs.getTimestamp("entry_datetime"));
				charaInfo.setUpdateDate(rs.getTimestamp("update_datetime"));
				charaInfo.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(charaInfo.getCreaterId());
		return charaInfo;
	}

	@Override
	public void insert(CharaDto chara) throws SQLException {
		String sql = "INSERT INTO charas ("
				+ " creater_id"
				+ ", name"
				+ ", name_kana"
				+ ", memo"
				+ ", image_path"
				+ ", image_filename"
				+ ", external_link"
				+ " ) values ("
				+ " ?, ?, ?, ?, ?, ?, ? )";
		System.out.println(sql);
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, chara.getCreaterId());
			stmt.setString(2, chara.getName());
			stmt.setString(3, chara.getNameKana());
			stmt.setString(4, chara.getMemo());
			stmt.setString(5, chara.getImagePath());
			stmt.setString(6, chara.getImageFileName());
			stmt.setString(7, chara.getExternalLink());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("キャラクター情報の登録に失敗しました");
			e.printStackTrace();
		}
	}

	@Override
	public void updateRow(CharaDto chara) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteByCharacterId(Integer characterId) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public List<CharaDto> selectCharasListByNamePartialMatch(String matchStr) throws SQLException {
		String sql = "Select "
				+ " CHARACTER_ID"
				+ " ,CREATER_ID"
				+ " ,NAME"
				+ " ,NAME_KANA"
				+ " ,MEMO"
				+ " ,IS_LOST"
				+ " ,IMAGE_FILENAME"
				+ " ,IMAGE_PATH"
				+ " ,EXTERNAL_LINK"
				+ " ,ENTRY_DATETIME"
				+ " ,UPDATE_DATETIME"
				+ " ,DELETE_FLG"
				+ " FROM charas"
				+ " WHERE name_kana like ?"
				+ " OR  name like ?"; 
		System.out.println(getClass().getName() + ":sql;->" + sql);
		List<CharaDto> rt = new ArrayList<CharaDto>();
		
		try (Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + matchStr + "%");
			stmt.setString(2, "%" + matchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CharaDto cdto = new CharaDto();
				cdto.setCharacterId(rs.getInt("character_id"));
				cdto.setCreaterId(rs.getString("creater_id"));
				cdto.setName(rs.getString("name"));
				cdto.setNameKana(rs.getString("name_kana"));
				cdto.setMemo(rs.getString("memo"));
				cdto.setIsLost(rs.getBoolean("is_lost"));
				cdto.setImageFileName(rs.getString("image_filename"));
				cdto.setImagePath(rs.getString("image_path"));
				cdto.setExternalLink(rs.getString("external_link"));
				cdto.setEntryDate(rs.getTimestamp("entry_datetime"));
				cdto.setUpdateDate(rs.getTimestamp("update_datetime"));
				cdto.setDeleteFlg(rs.getBoolean("delete_flg"));
				rt.add(cdto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("エラーです。");
		}
		
		return rt;
	}

}
