package dao;

import java.util.List;

import dto.Event;

public interface EventDao {
	List<Event> selectAll();
	Event findById(Integer id);
	Event findByEventId(Integer eventId);
	void insert(Event Event);
	void updateRow(Event Event);
	void deleteById(Event Event);
}
