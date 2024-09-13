package dao;

import java.util.List;

import dto.PreUser;

public interface PreUserDao {
	List<PreUser> selectAll();
	PreUser findById(Integer id);
	PreUser findByEmailAndToken(String email, String token);
	void insert(PreUser preUser);
	void updateRow(PreUser preUser);
	void deleteById(PreUser preUser);
}
