package dao;

import java.util.List;

import dto.User;

public interface UserDao {
	List<User> selectAll();
	User findById(Integer id);
	User findByUserId(String userId);
	Boolean isAuthenticated(String userId, String password);
	void insert(User user);
	void updateRow(User user);
	void deleteById(User user);
}
