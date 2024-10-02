package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dto.Profile;

public class ProfileDaoImpl implements ProfileDao{
	private DataSource ds;

	public ProfileDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Profile> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Profile findByUserId(String userId) {
		String sql = "SELECT "
				+ " user_id"
				+ ", image_path"
				+ ", base64_data "
				+ ", text"
				+ ", entry_datetime"
				+ ", update_datetime"
				+ ", delete_flg"
				+ " FROM profile"
				+ " WHERE user_id = ?";
		Profile pr = new Profile();
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1 , userId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				pr.setUserId(rs.getString("user_id"));
				pr.setImagePath(rs.getString("image_path"));
				pr.setBase64Data(rs.getString("base64_data"));
				pr.setText(rs.getString("text"));
				pr.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				pr.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				pr.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pr;
	}

	@Override
	public void insert(Profile profile) throws SQLException {
		String sql = "INSERT INTO profile ( "
				+ " user_id"
				+ ", image_path"
				+ ", base64_data"
				+ ", img_file_type"
				+ ", text"
				+ ") values ("
				+ "?, ?, ?, ?, ? )";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1 , profile.getUserId());
			stmt.setString(4 , profile.getImagePath());
			stmt.setString(2 , profile.getBase64Data());
			stmt.setString(3 , profile.getImgFileType());
			stmt.setString(5 , profile.getText());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateRow(Profile profile) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteById(Profile profile) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void updateImage(String userId, String newPath, String base64Data, String fileExtention) throws SQLException {
		String sql = "UPDATE profile SET "
				+ " image_path = ?"
				+ " ,base64_data = ?"
				+ " ,img_file_type = ?"
				+ " WHERE user_id = ?";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1 , newPath);
			stmt.setString(2 , base64Data);
			stmt.setString(3 , fileExtention);
			stmt.setString(4 , userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}		
	}

	@Override
	public void updateText(String userId, String newText) throws SQLException  {
		String sql = "UPDATE profile SET "
				+ " text = ?"
				+ " WHERE user_id = ?";
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1 , newText);
			stmt.setString(2 , userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}		
	}

}
