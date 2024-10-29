package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.EntryApproval;
import dto.EntryApprovalWithPict;

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
	public List<EntryApproval> selectByEventId(Integer eventId) {
		List<EntryApproval> entryAppList = new ArrayList<EntryApproval>();
		String sql = "SELECT "
				+ " id"
				+ ", event_id"
				+ ", sign_up_user_id"
				+ ", approve_status"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM entry_approval"
				+ " WHERE event_id = ?"
				+ " ORDER BY update_datetime ASC";
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			System.out.println("EntryApproval:sql->" + sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EntryApproval ea = new EntryApproval();
				ea.setId(rs.getInt("id"));
				ea.setEventID(rs.getInt("event_id"));
				ea.setSignUpUserId(rs.getString("sign_up_user_id"));
				ea.setApprovalStatus(rs.getInt("approve_status"));
				ea.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				ea.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				ea.setDeleteFlg(rs.getBoolean("delete_flg"));
				entryAppList.add(ea);
			}
		} catch(SQLException e) {
			System.out.println("selectByEventIdで問題が生じました");
			e.printStackTrace();
		}
				
		return entryAppList;
	}

	@Override
	public List<EntryApprovalWithPict> selectByEventIdWithPict(Integer eventId) {
		System.out.println("Dao戦闘eventId->" + eventId);
		List<EntryApprovalWithPict> entryAppList = new ArrayList<EntryApprovalWithPict>();
		String sql = "SELECT "
				+ " entry_approval.id as id"
				+ ", entry_approval.event_id"
				+ ", entry_approval.sign_up_user_id"
				+ ", entry_approval.approve_status"
				+ ", entry_approval.entry_datetime as entry_datetime"
				+ ", entry_approval.update_datetime as update_datetime"
				+ ", entry_approval.delete_flg as delete_flg"
				+ ", profile.base64_data"
				+ ", user.user_name"
				+ " FROM entry_approval"
				+ " INNER JOIN profile"
				+ " ON entry_approval.sign_up_user_id = profile.user_id"
				+ " INNER JOIN user ON entry_approval.sign_up_user_id = user.user_id"
				+ " WHERE entry_approval.event_id = ?"
				+ " ORDER BY entry_approval.update_datetime ASC";
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EntryApprovalWithPict eawp = new EntryApprovalWithPict();
				eawp.setId(rs.getInt("id"));
				eawp.setEventID(rs.getInt("event_id"));
				eawp.setSignUpUserId(rs.getString("sign_up_user_id"));
				eawp.setApprovalStatus(rs.getInt("approve_status"));
				eawp.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				eawp.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				eawp.setDeleteFlg(rs.getBoolean("delete_flg"));
				eawp.setBase64ImgStr(rs.getString("base64_data"));
				eawp.setUserName(rs.getString("user_name"));
				entryAppList.add(eawp);
			}
		} catch(SQLException e) {
			System.out.println("selectByEventIdWithPictで問題が生じました");
			e.printStackTrace();
		}
		System.out.println("entryAppList:" + entryAppList.size());
		return entryAppList;
	}
	
	@Override
	public List<EntryApprovalWithPict> selectByEventIdWithPict(Integer eventId, String organizerId , Integer approvalStatus) {
		System.out.println(getClass().getName() + "->" + eventId + "," + approvalStatus);
		List<EntryApprovalWithPict> entryAppList = new ArrayList<EntryApprovalWithPict>();
		String sql = "SELECT "
				+ " entry_approval.id as id"
				+ ", entry_approval.event_id"
				+ ", entry_approval.sign_up_user_id"
				+ ", entry_approval.approve_status"
				+ ", entry_approval.entry_datetime as entry_datetime"
				+ ", entry_approval.update_datetime as update_datetime"
				+ ", entry_approval.delete_flg as delete_flg"
				+ ", profile.base64_data"
				+ ", user.user_name"
				+ " FROM entry_approval"
				+ " INNER JOIN profile"
				+ " ON entry_approval.sign_up_user_id = profile.user_id"
				+ " INNER JOIN user ON entry_approval.sign_up_user_id = user.user_id"
				+ " WHERE entry_approval.event_id = ?"
				+ " AND entry_approval.approve_status = ? "
				+ " AND entry_approval.sign_up_user_id <> ? "
				+ " ORDER BY entry_approval.update_datetime ASC";
		System.out.println(sql);
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			stmt.setInt(2, approvalStatus);
			stmt.setString(3, organizerId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EntryApprovalWithPict eawp = new EntryApprovalWithPict();
				eawp.setId(rs.getInt("id"));
				eawp.setEventID(rs.getInt("event_id"));
				eawp.setSignUpUserId(rs.getString("sign_up_user_id"));
				eawp.setApprovalStatus(rs.getInt("approve_status"));
				eawp.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				eawp.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				eawp.setDeleteFlg(rs.getBoolean("delete_flg"));
				eawp.setBase64ImgStr(rs.getString("base64_data"));
				eawp.setUserName(rs.getString("user_name"));
				entryAppList.add(eawp);
			}
		} catch(SQLException e) {
			System.out.println("selectByEventIdWithPictで問題が生じました");
			e.printStackTrace();
		}
				
		return entryAppList;
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
				+ " WHERE sign_up_user_id = ?"
				+ " AND delete_flg = 0";
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
	public void insert(EntryApproval entryApproval) throws SQLException {
		System.out.println("ここまで到達");
		String sql = "INSERT into entry_Approval ("
				+ "event_id"
				+ ", sign_up_user_id"
//				+ ", approve_status"
				+ ") values ( ?, ?)";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, entryApproval.getEventID());
			stmt.setString(2, entryApproval.getSignUpUserId());
//			stmt.setInt(3, entryApproval.getApprovalStatus());
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateApproveStatusRow(EntryApproval entryApproval) throws SQLException {
		String sql = "UPDATE entry_approval SET approve_status = ?"
				+ " WHERE event_id = ?"
				+ " AND sign_up_user_id = ? ";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, entryApproval.getApprovalStatus());
			stmt.setInt(2, entryApproval.getEventID());
			stmt.setString(3, entryApproval.getSignUpUserId());
			stmt.executeUpdate();
		} catch (SQLException e ) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteById(EntryApproval entryApproval) throws SQLException {
		String sql = "Delete from entry_approval"
				+ " WHERE event_id = ?"
				+ " AND sign_up_user_id = ? ";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, entryApproval.getApprovalStatus());
			stmt.setInt(2, entryApproval.getEventID());
			stmt.setString(3, entryApproval.getSignUpUserId());
			stmt.executeUpdate();
		} catch (SQLException e ) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<EntryApproval> selectByEventIds(List<Integer> eventIds) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void updateApproveStatus(List<EntryApproval> targetList, Integer updateStatus) throws SQLException {
		String sql = "UPDATE entry_approval SET approve_status = ?"
				+ " WHERE event_id = ?"
				+ " AND sign_up_user_id = ? ";
		try(Connection con = ds.getConnection()){
			 PreparedStatement stmt = con.prepareStatement(sql);
			for(EntryApproval entryApproval : targetList ) {
				stmt.setInt(1, updateStatus);
				stmt.setInt(2, entryApproval.getEventID());
				stmt.setString(3, entryApproval.getSignUpUserId());
				stmt.addBatch();
			}
			stmt.executeBatch();
		} catch (SQLException e ) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public EntryApproval findByEventIdAndSgnUpUsrId(Integer eventId, String userId) {
		String sql = "SELECT "
				+ " id"
				+ ", event_id"
				+ ", sign_up_user_id"
				+ ", approve_status"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM entry_approval"
				+ " WHERE event_id = ?"
				+ " AND sign_up_user_id = ?"
				+ " AND delete_flg = 0"
				+ " ORDER BY update_datetime ASC";
		
		System.out.println(getClass().getName() + "SQL->" + sql);
		EntryApproval ea = new EntryApproval();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			stmt.setString(2, userId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ea.setId(rs.getInt("id"));
				ea.setEventID(rs.getInt("event_id"));
				ea.setSignUpUserId(rs.getString("sign_up_user_id"));
				ea.setApprovalStatus(rs.getInt("approve_status"));
				ea.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				ea.setEntryDatetime(rs.getTimestamp("update_datetime"));
				ea.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return ea;
	}

	@Override
	public Integer getApprovalStatus(Integer eventId, String userId) throws SQLException {
		String sql = "SELECT "
				+ " id"
				+ ", event_id"
				+ ", sign_up_user_id"
				+ ", approve_status"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM entry_approval"
				+ " WHERE event_id = ?"
				+ " AND sign_up_user_id = ?"
				+ " ORDER BY update_datetime ASC";
		
		System.out.println(getClass().getName() + "SQL->" + sql);
		Integer ret = null;
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			stmt.setString(2, userId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ret = rs.getInt("approve_status");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
