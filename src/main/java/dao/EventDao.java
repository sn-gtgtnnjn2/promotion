package dao;

import java.util.List;

import dto.Event;

public interface EventDao {
	List<Event> selectAll();
	List<Event> findByUserId(String userId);
	List<Event> findRecentInfo(Integer monthRange);
	Event findByEventId(Integer eventId);
	void insert(Event Event);
	void updateRow(Event Event);
	void deleteById(Event Event);
	public List<Event> selectByEventIds(List<Integer> eventIds);
}
