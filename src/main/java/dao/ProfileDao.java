package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Profile;

public interface ProfileDao {
	List<Profile> selectAll();
	Profile findByUserId(String userId);
	void insert(Profile profile) throws SQLException;
	void updateRow(Profile profile);
	void updateImage(String userId, String newPath, String base64Data) throws SQLException;
	void updateText(String userId, String newText) throws SQLException;
	void deleteById(Profile profile);
}
