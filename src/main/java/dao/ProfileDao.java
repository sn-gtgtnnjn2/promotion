package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Profile;

public interface ProfileDao {
	List<Profile> selectAll();
	Profile findByUserId(String userId);
	void insert(Profile profile) throws SQLException;
	void updateRow(Profile profile);
	void updateImagePath(String userId, String newPath) throws SQLException;
	void updateText(String userId, String newText);
	void deleteById(Profile profile);
}
