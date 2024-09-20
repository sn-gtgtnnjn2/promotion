package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import bean.EventInfo;
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
	public Event findById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
	public List<EventInfo> selectByEventIds(List<Integer> eventIds) {
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
				+ " WHERE user_id IN ("
				+ eventIds.stream().map(id -> "?").collect(Collectors.joining((","))) + ")";
		
		System.out.println(sql);
		List<EventInfo> eiList = new ArrayList<EventInfo>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			for(int i = 0; i < eventIds.size(); i ++) {
				stmt.setInt(i + 1, eventIds.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EventInfo ei = new EventInfo();
				ei.setEventId(rs.getInt("event_id"));
				ei.setEventId(rs.getInt("user_id"));
				ei.setEventId(rs.getInt("event_title"));
				ei.setEventId(rs.getInt("event_datetime"));
				ei.setEventId(rs.getInt("organizer_name"));
				ei.setEventId(rs.getInt("organizer_id"));
				ei.setEventId(rs.getInt("scenario_title"));
				ei.setEventId(rs.getInt("recruitement_start_date"));
				ei.setEventId(rs.getInt("recruitement_end_date"));
				ei.setEventId(rs.getInt("member_limit"));
				ei.setEventId(rs.getInt("open_level"));
				ei.setEventId(rs.getInt("status"));
				eiList.add(ei);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eiList;
	}

}
