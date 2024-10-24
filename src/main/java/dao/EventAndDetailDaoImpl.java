package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.EntryApproval;
import dto.EventAndDetail;
import util.Constants;
import util.GeneralFormatter;

public class EventAndDetailDaoImpl implements EventAndDetailDao {
	DataSource ds;
	
	public EventAndDetailDaoImpl(DataSource ds){
		this.ds = ds;
	}

	@Override
	public List<EventAndDetail> selectAll() {
		String sql = "SELECT "
				+ " event_id"
				+ ", user_id"
				+ ", event_title"
				+ ", event_datetime"				
				+ ", organizer_name"
				+ ", organizer_id"
				+ ", scenario_title"
				+ ", detail "
				+ ", recruitment_start_date"
				+ ", recruitment_end_date"
				+ ", member_limit"
				+ ", open_level"
				+ ", status"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM event"
				+ " INNER JOIN event_detail"
				+ " ON event.event_id = event_detail.event_id";
		ResultSet rs = null;
		List<EventAndDetail> eadList = new ArrayList<EventAndDetail>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				EventAndDetail ead = new EventAndDetail();
				ead.setUserId(rs.getString("user_id"));
				ead.setEventTitle(rs.getString("event_title"));
				ead.setEventDatetime(rs.getTimestamp("event_datetime"));
				ead.setOrganizerName(rs.getString("organizer_name"));
				ead.setOrganizerId(rs.getString("organizer_id"));
				ead.setScenarioTitle(rs.getString("scenario_title"));
				ead.setDetail(rs.getString("detail"));
				ead.setRecruitmentStartDate(rs.getTimestamp("recruitment_start_date"));
				ead.setRecruitmentEndDate(rs.getTimestamp("recruitment_end_date"));
				ead.setMemberLimit(rs.getInt("member_limit"));
				ead.setOpenLevel(rs.getInt("open_level"));
				ead.setStatus(rs.getInt("status"));
				eadList.add(ead);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eadList;
	}

	@Override
	public EventAndDetail findById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public EventAndDetail findByEventId(Integer eventId) throws SQLException {
		String sql = "SELECT  "
				+ " event.event_id as event_id"
				+ ", event.user_id as user_id"
				+ ", event_title"
				+ ", event_datetime"
				+ ", organizer_name"
				+ ", organizer_id"
				+ ", scenario_title"
				+ ", recruitment_start_date"
				+ ", recruitment_end_date"
				+ ", open_level"				
				+ ", member_limit"
				+ ", detail"
				+ ", status"
				+ ", cancel_flg"
				+ ", event.entry_datetime as entry_datetime"
				+ ", event.update_datetime as update_datetime"
				+ ", event.delete_flg as delete_flg"
				+ " FROM event LEFT OUTER JOIN event_detail "
				+ " ON event.event_id = event_detail.event_id"
				+ " WHERE event.event_id = ?";
		EventAndDetail ead = null;
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, eventId);
			System.out.println(getClass().toString() + "SQL>" + sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ead = new EventAndDetail(); 
				ead.setEventId(rs.getInt("event_id"));
				ead.setEventTitle(rs.getString("event_title"));
				ead.setEventDatetime(rs.getTimestamp("event_datetime"));
				ead.setUserId(rs.getString("user_id"));
				ead.setOrganizerId(rs.getString("organizer_id"));
				ead.setOrganizerName(rs.getString("organizer_name"));
				ead.setScenarioTitle(rs.getString("scenario_title"));
				ead.setRecruitmentStartDate(rs.getTimestamp("recruitment_start_date"));
				ead.setRecruitmentEndDate(rs.getTimestamp("recruitment_end_date"));
				ead.setMemberLimit(rs.getInt("member_limit"));
				ead.setDetail(rs.getString("detail"));
				ead.setOpenLevel(rs.getInt("open_level"));
				ead.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				ead.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				ead.setCancelFlg(rs.getBoolean("cancel_flg"));
				ead.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
				
		return ead;
	}

	@Override
	public void insert(EventAndDetail ead) {
		String sql = "INSERT INTO event ( "
				+ " user_id"
				+ ", event_title"
				+ ", event_datetime"
				+ ", organizer_name"
				+ ", organizer_id"
				+ ", scenario_title"
				+ ", recruitment_start_date"
				+ ", recruitment_end_date"
				+ ", member_limit"
				+ ", open_level"
				+ ", status"
				+ ") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		String sql2 = "INSERT INTO event_detail ( "
				+ " event_id"
				+ ", user_id"
				+ ", detail"
				+ " ) values ( ?, ?, ?)";
		
		try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false); // トランザクションを開始
			
			try {
				PreparedStatement stmtEvent = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmtEvent.setString(1, ead.getUserId());
				stmtEvent.setString(2, ead.getEventTitle());
				stmtEvent.setTimestamp(3, GeneralFormatter.convDateToSqlTimestamp(ead.getEventDatetime()));
				stmtEvent.setString(4, ead.getOrganizerName());
				stmtEvent.setString(5, ead.getOrganizerId());
				stmtEvent.setString(6, ead.getScenarioTitle());
				stmtEvent.setTimestamp(7, GeneralFormatter.convDateToSqlTimestamp(ead.getRecruitmentStartDate()));
				stmtEvent.setTimestamp(8, GeneralFormatter.convDateToSqlTimestamp(ead.getRecruitmentEndDate()));
				stmtEvent.setInt(9, ead.getMemberLimit());
				stmtEvent.setInt(10, ead.getOpenLevel());
				stmtEvent.setInt(11, 0);
				System.out.println(sql);
				System.out.println(sql2);
				stmtEvent.executeUpdate();
				
				PreparedStatement stmtEventDetail = con.prepareStatement(sql2);
				ResultSet keys = stmtEvent.getGeneratedKeys();
				keys.next();
				Integer newEventId = keys.getInt(1);
				System.out.println(newEventId);
				stmtEventDetail.setInt(1, keys.getInt(1)); // カラムインデックスを使用
				stmtEventDetail.setString(2, ead.getUserId());
				stmtEventDetail.setString(3, ead.getDetail());
				stmtEventDetail.executeUpdate();
				
				// 応募状況テーブルにも追加
				EntryApprovalDao entAppDao = DaoFactory.createEntryApprovalDao();
				EntryApproval entApp = new EntryApproval();
				entApp.setEventID(newEventId);
				entApp.setSignUpUserId(ead.getUserId());
				entApp.setApprovalStatus(Constants.EVENT_APPROVAL_AVAILABLE);		
				entAppDao.insert(entApp);
				
				con.commit();
				
			} catch (SQLException e) {
				System.out.println("ロールバックが行われました");
				con.rollback();
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRow(EventAndDetail ead) throws SQLException{
System.out.println(ead.getEventId());
System.out.println(ead.getEventTitle());
System.out.println(ead.getScenarioTitle());
System.out.println(ead.getEventDatetime());
		// TODO 自動生成されたメソッド・スタブ
		String sql = "UPDATE event set"
				+ " event_title = ?"
				+ ", event_datetime = ?"
				+ ", scenario_title = ?"
				+ ", recruitment_start_date = ?"
				+ ", recruitment_end_date = ?"
				+ ", member_limit = ?"
				+ ", open_level = ?"
				+ " WHERE event_id = ?"
				+ " AND delete_flg = 0";
		
		String sql2 = "UPDATE event_detail set "
				+ " detail = ?"
				+ " WHERE event_id = ?"
				+ " AND delete_flg = 0";
		
		try(Connection con = ds.getConnection()){
			con.setAutoCommit(false); // トランザクションを開始
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, ead.getEventTitle());
			stmt.setTimestamp(2, GeneralFormatter.convDateToSqlTimestamp(ead.getEventDatetime()));
			stmt.setString(3, ead.getScenarioTitle());
			stmt.setTimestamp(4, GeneralFormatter.convDateToSqlTimestamp(ead.getRecruitmentStartDate()));
			stmt.setTimestamp(5, GeneralFormatter.convDateToSqlTimestamp(ead.getRecruitmentEndDate()));
			stmt.setInt(6, ead.getMemberLimit());
			stmt.setInt(7, ead.getOpenLevel());
			
			stmt.setInt(8, ead.getEventId());
			
System.out.println(sql);
			int affectedRows = stmt.executeUpdate();
			boolean firstUpdateSuccess = (affectedRows == 1);
            
			if (firstUpdateSuccess) {
				PreparedStatement stmt2 = con.prepareStatement(sql2);
				stmt2.setString(1, ead.getDetail());
				stmt2.setInt(2, ead.getEventId());
				
System.out.println(sql2);
				int affectedRows2 = stmt2.executeUpdate();
                boolean secondUpdateSuccess = (affectedRows2 == 1);
                if (secondUpdateSuccess) {
                    con.commit(); // 全ての更新が成功した場合にコミット
                    System.out.println("commit");
                } else {
                    con.rollback(); // 失敗した場合はロールバック
                }
            } else {
                con.rollback(); // 失敗した場合はロールバック
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ead更新失敗");
		}
		
		
	}

	@Override
	public void deleteById(EventAndDetail EventAndDetail) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
