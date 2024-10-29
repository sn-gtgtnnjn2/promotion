package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import bean.EventInfoBean;
import dto.Event;

public class EventInfoDaoImpl implements EventInfoDao{
	DataSource ds;

	public EventInfoDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Event> selectByEventIdsWithPict(List<Integer> eventIds, String userId) {
		String sql = "SELECT "
				+ " event.event_id"
				+ ", event.user_id"
				+ ", event.event_title"
				+ ", event.event_datetime"
				+ ", event.organizer_name"
				+ ", event.organizer_id"
				+ ", event.scenario_title"
				+ ", event.recruitment_start_date"
				+ ", event.recruitment_end_date"
				+ ", event.member_limit"
				+ ", event.open_level"
				+ ", event.status"
				+ ", event.cancel_flg"				
				+ ", COUNT(entry_approval.sign_up_user_id) AS 'currentSignUpNum'"
				+ ", COUNT(entry_approval2.sign_up_user_id) AS 'currentApprovedNum'"
				+ ", profile.base64_data"
				+ " FROM event inner join profile"
				+ " ON event.user_id = profile.user_id"
				+ " INNER JOIN entry_approval"
				+ " ON event.EVENT_ID = entry_approval.event_id"
				+ " LEFT OUTER JOIN ("
				+ "   SELECT event_id, sign_up_user_id "
				+ "   FROM entry_approval "
				+ "   WHERE approve_status = 1"
				+ " ) AS entry_approval2 ON event.EVENT_ID = entry_approval2.event_id"
				+ " WHERE event.event_id IN ("
				+ eventIds.stream().map(id -> "?").collect(Collectors.joining((","))) + ")"
				+ " AND (event.organizer_id = ? OR entry_approval.sign_up_user_id = ?)"
				+ " AND event.delete_flg = 0"
				+ " AND entry_approval.delete_flg = 0"
				+ " group by event.EVENT_ID , entry_approval.sign_up_user_id";
		
		System.out.println(sql);
		List<Event> eiList = new ArrayList<Event>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			for(int i = 0; i < eventIds.size(); i ++) {
				stmt.setInt(i + 1, eventIds.get(i));
			}
			stmt.setString(eventIds.size() + 1, userId);
			stmt.setString(eventIds.size() + 2, userId);
			
			ResultSet rs = stmt.executeQuery();
			eiList = storeFetchData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eiList;
	}
	
	private List<Event> storeFetchData(ResultSet rs) throws SQLException {
		List<Event> eiList = new ArrayList<Event>();
		while(rs.next()) {
			Event ei = new Event();
			ei.setEventId(rs.getInt("event_id"));
			ei.setUserId(rs.getString("user_id"));
			ei.setEventTitle(rs.getString("event_title"));
			ei.setEventDate(rs.getTimestamp("event_datetime"));
			ei.setOrganizerName(rs.getString("organizer_name"));
			ei.setOrganizerId(rs.getString("organizer_id"));
			ei.setScenarioTitle(rs.getString("scenario_title"));
			ei.setRecruitmentStartDate(rs.getTimestamp("recruitment_start_date"));
			ei.setRecruitmentEndDate(rs.getTimestamp("recruitment_end_date"));
			ei.setMemberLimit(rs.getInt("member_limit"));
			ei.setOpenLevel(rs.getInt("open_level"));
			ei.setStatus(rs.getInt("status"));
			ei.setOrganizerImageString(rs.getString("base64_data"));
			ei.setCurrentSignUpNum(rs.getInt("currentSignUpNum"));
			ei.setCurrentApprovedNum(rs.getInt("currentApprovedNum") - 1);// 主催者が人数に含まれているのを除外するため-1
			ei.setCancelFlg(rs.getBoolean("cancel_flg"));
			eiList.add(ei);
		}
		return eiList;
	}

	@Override
	public EventInfoBean findByEventId(Integer eventId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Event> selectAvailableEventsWithPict(Integer maxRows, String whereStr) {
//		String sql = "SELECT "
//				+ " event.event_id"
//				+ ", event.user_id"
//				+ ", event.event_title"
//				+ ", event.event_datetime"
//				+ ", event.organizer_name"
//				+ ", event.organizer_id"
//				+ ", event.scenario_title"
//				+ ", event.recruitment_start_date"
//				+ ", event.recruitment_end_date"
//				+ ", event.member_limit"
//				+ ", event.open_level"
//				+ ", event.status"
//				+ ", profile.base64_data"
//				+ ", COUNT(entry_approval.sign_up_user_id) AS 'currentSignUpNum'"
//				+ ", COUNT(entry_approval2.sign_up_user_id) AS 'currentApprovedNum'"
//				+ ", event.delete_flg"
//				+ ", event.cancel_flg"
//				+ " FROM event INNER JOIN profile"
//				+ " ON event.user_id = profile.user_id"
//				+ " INNER JOIN entry_approval"
//				+ " ON event.EVENT_ID = entry_approval.event_id"
//				+ " LEFT OUTER JOIN ("
//				+ "   SELECT event_id, sign_up_user_id "
//				+ "   FROM entry_approval "
//				+ "   WHERE approve_status = 1"
//				+ " ) AS entry_approval2 ON event.EVENT_ID = entry_approval2.event_id"
//				+ " " + whereStr
//				+ " group by event.EVENT_ID , entry_approval.sign_up_user_id"
//				+ " order by event.update_datetime DESC"
//				+ " limit " + maxRows;
		String sql = "SELECT "
		        + " event.event_id"
		        + ", event.user_id"
		        + ", event.event_title"
		        + ", event.event_datetime"
		        + ", event.organizer_name"
		        + ", event.organizer_id"
		        + ", event.scenario_title"
		        + ", event.recruitment_start_date"
		        + ", event.recruitment_end_date"
		        + ", event.member_limit"
		        + ", event.open_level"
		        + ", event.status"
		        + ", event.cancel_flg"
//		        + ", COUNT(entry_approval.approve_status) AS currentSignUpNum"
//		        + ", COUNT(entry_approval2.approve_status) AS currentApprovedNum"
				+ ", SUM(CASE WHEN entry_approval.approve_status = 0 THEN 1 ELSE 0 END) AS currentSignUpNum "
				+ ", SUM(CASE WHEN entry_approval.approve_status = 1 THEN 1 ELSE 0 END) AS currentApprovedNum "
		        + ", profile.base64_data "
		        + " FROM event "
		        + " INNER JOIN profile ON event.user_id = profile.user_id "
//		        + " LEFT JOIN (SELECT event_id, sign_up_user_id, approve_status FROM entry_approval WHERE approve_status = 0 and delete_flg = 0) AS entry_approval"
//		        + " ON event.event_id = entry_approval.event_id "
//		        + " LEFT JOIN (SELECT event_id, sign_up_user_id, approve_status FROM entry_approval WHERE approve_status = 1 and delete_flg = 0) AS entry_approval2 "
//		        + " ON event.event_id = entry_approval2.event_id "
				+ " LEFT JOIN (SELECT * FROM entry_approval WHERE delete_flg = 0) AS entry_approval ON event.event_id = entry_approval.event_id " 
				+ " " + whereStr
		        + " GROUP BY event.event_id"
		        + ", event.user_id"
		        + ", event.event_title"
		        + ", event.event_datetime"
		        + ", event.organizer_name"
		        + ", event.organizer_id"
		        + ", event.scenario_title"
		        + ", event.recruitment_start_date"
		        + ", event.recruitment_end_date"
		        + ", event.member_limit"
		        + ", event.open_level"
		        + ", event.status"
		        + ", event.cancel_flg"
		        + ", profile.base64_data"
				+ " order by event.update_datetime DESC"
				+ " limit " + maxRows;
		System.out.println("SQL>" + sql);
		System.out.println(sql);
		List<Event> eiList = new ArrayList<Event>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			eiList = storeFetchData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eiList;
	}
	
}
