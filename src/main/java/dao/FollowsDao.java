package dao;

import java.util.List;

import dto.Follows;

public interface FollowsDao {
	List<Follows> selectByUserId(String userId);
	List<Follows> selectByFollowsId(String followsId);
	void deleteRow(String userId, String followsId);
	Follows findByUserIdAndFlsId(String userId, String followsId);
}
