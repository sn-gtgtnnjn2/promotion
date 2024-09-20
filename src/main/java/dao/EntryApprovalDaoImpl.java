package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.EntryApproval;

public class EntryApprovalDaoImpl implements EntryApprovalDao{

	DataSource ds;
	public EntryApprovalDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<EntryApproval> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public EntryApproval findById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public EntryApproval selectByEventId(Integer eventId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<EntryApproval> selectByUserId(String userId) throws SQLException {
		String sql = "SELECT event_id"
				+ ", sign_up_user_id"
				+ ", approve_status"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM entry_approval"
				+ " WHERE sign_up_user_id = ?";
		List<EntryApproval> eventList = new ArrayList<EntryApproval>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1 , userId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EntryApproval ea = new EntryApproval();
				ea.setEventID(rs.getInt("event_id"));
				ea.setSignUpUserId(rs.getString("sign_up_user_id"));
				ea.setApprovalStatus(rs.getInt("approve_status"));
				ea.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				ea.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				ea.setDeleteFlg(rs.getBoolean("delete_flg"));
				eventList.add(ea);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}	
		
		return eventList;
	}

	@Override
	public void insert(EntryApproval EventApproval) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void updateRow(EntryApproval EventApproval) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteById(EntryApproval EventApproval) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public List<EntryApproval> selectByEventIds(List<Integer> eventIds) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
