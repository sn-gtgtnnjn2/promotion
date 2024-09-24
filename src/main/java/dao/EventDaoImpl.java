package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import dto.Event;

public class EventDaoImpl implements EventDao{

	DataSource ds;
	
	public EventDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public List<Event> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Event> findByUserId(String userId) {
			String sql = "SELECT "
					+ " event_id"
					+ ", user_id"
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
					+ " FROM event "
					+ " WHERE user_id = ?"
					+ " ORDER BY event_datetime desc";
			System.out.println(sql);
			List<Event> eiList = new ArrayList<Event>();
			try(Connection con = ds.getConnection()){
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, userId);				
				ResultSet rs = stmt.executeQuery();
//				while(rs.next()) {
//					EventInfo ei = new EventInfo();
//					ei.setEventId(rs.getInt("event_id"));
//					ei.setUserId(rs.getString("user_id"));
//					ei.setEventTitle(rs.getString("event_title"));
//					ei.setEventDate(rs.getTimestamp("event_datetime"));
//					ei.setOrganizerName(rs.getString("organizer_name"));
//					ei.setOrganizerId(rs.getString("organizer_id"));
//					ei.setScenarioTitle(rs.getString("scenario_title"));
//					ei.setRecruitmentStartDate(rs.getTimestamp("recruitment_end_date"));
//					ei.setRecruitmentEndDate(rs.getTimestamp("recruitment_end_date"));
//					ei.setMemberLimit(rs.getInt("member_limit"));
//					ei.setOpenLevel(rs.getInt("open_level"));
//					ei.setStatus(rs.getInt("status"));
//					eiList.add(ei);
//				}
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
			eiList.add(ei);
		}
		return eiList;
	}

	@Override
	public Event findByEventId(Integer eventId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void insert(Event Event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void updateRow(Event Event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteById(Event Event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public List<Event> selectByEventIds(List<Integer> eventIds) {
		String sql = "SELECT "
				+ " event_id"
				+ ", user_id"
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
				+ " FROM event "
				+ " WHERE event_id IN ("
				+ eventIds.stream().map(id -> "?").collect(Collectors.joining((","))) + ")";
		
		System.out.println(sql);
		List<Event> eiList = new ArrayList<Event>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			for(int i = 0; i < eventIds.size(); i ++) {
				stmt.setInt(i + 1, eventIds.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			eiList = storeFetchData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eiList;
	}

	@Override
	public List<Event> findRecentInfo(Integer monthRange) {
		String sql = "SELECT "
				+ " event_id"
				+ ", user_id"
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
				+ " FROM event "
				+ " WHERE event_datetime > (NOW() - INTERVAL ? MONTH)"
				+ " ORDER BY event_datetime desc";
		System.out.println(sql);
		List<Event> eiList = new ArrayList<Event>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setInt(1, monthRange);
			
			ResultSet rs = stmt.executeQuery();
			eiList = storeFetchData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eiList;
	}

}
