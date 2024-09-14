package dao;

import java.util.List;

import dto.User;

public interface UserDao {
	List<User> selectAll();
	User findById(Integer id);
	void insert(User user);
	void updateRow(User user);
	void deleteById(User user);
}
