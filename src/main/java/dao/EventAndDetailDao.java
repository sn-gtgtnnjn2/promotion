package dao;

import java.util.List;

import dto.EventAndDetail;

public interface EventAndDetailDao {
	List<EventAndDetail> selectAll();
	EventAndDetail findById(Integer id);
	EventAndDetail findByEventId(Integer eventId);
	void insert(EventAndDetail EventAndDetail);
	void updateRow(EventAndDetail EventAndDetail);
	void deleteById(EventAndDetail EventAndDetail);
}
