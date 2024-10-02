package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dto.Follows;

public class FollowsDaoImpl implements FollowsDao{
	private DataSource ds;

	public FollowsDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Follows> selectByUserId(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Follows> selectByFollowsId(String followsId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deleteRow(String userId, String followsId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	/**
	 *	@param userId followしている人
	 *	@param folloesId followされている人
	 */
	@Override
	public Follows findByUserIdAndFlsId(String userId, String followsId) throws SQLException {
		String sql = "SELECT "
				+ " id"
				+ ", user_id"
				+ ", follows_id"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM follows"
				+ " WHERE user_id = ?"
				+ " AND follows_id = ?";
		Follows fl = new Follows();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, sql);
			stmt.setString(2, sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				fl.setId(rs.getInt("id"));
				fl.setUserId(rs.getString("user_id"));
				fl.setFollowsId(rs.getString("followed_id"));
				fl.setEntryDate(rs.getTimestamp("entry_date"));
				fl.setUpdateDate(rs.getTimestamp("update_datetime"));
				fl.setDeleteFlg(rs.getBoolean("delete_flg"));
			}

			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		return fl;
	}

}
