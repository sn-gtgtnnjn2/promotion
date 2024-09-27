package dao;

import java.util.List;

import bean.EventInfo;
import dto.Event;

public interface EventInfoDao {
	public List<Event> selectByEventIdsWithPict(List<Integer> eventIds);

	public EventInfo findByEventId(Integer eventId);
}
