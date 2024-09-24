package dao;

import java.util.List;

import dto.Event;

public interface EventInfoDao {
	public List<Event> selectByEventIdsWithPict(List<Integer> eventIds);
}
