package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Follows;

public interface FollowsDao {
	List<Follows> selectByUserId(String userId) throws SQLException;
	List<Follows> selectByFollowsId(String followsId) throws SQLException;
	void deleteRow(String userId, String followsId) throws SQLException;
	Follows findByUserIdAndFlsId(String userId, String followsId) throws SQLException;
}
