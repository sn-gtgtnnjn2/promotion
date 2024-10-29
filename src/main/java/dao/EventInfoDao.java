package dao;

import java.util.List;

import bean.EventInfoBean;
import dto.Event;

public interface EventInfoDao {
	public List<Event> selectByEventIdsWithPict(List<Integer> eventIds, String userId);
	public List<Event> selectAvailableEventsWithPict(Integer MaxRows, String whereStr);
	public EventInfoBean findByEventId(Integer eventId);
}
