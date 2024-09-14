package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import dto.User;

public class UserDaoImpl implements UserDao{
	private DataSource ds;
	
	UserDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public List<User> selectAll() {
		String sql = "SELECT "
				+ " id"
				+ " ,user_id"
				+ " ,user_name"
				+ " ,email"
				+ " ,password"
				+ " ,entry_datetime"
				+ " ,update_datetime"
				+ " ,delete_flg"
				+ " FROM user";
		List<User> userList = new ArrayList<User>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				Date entryDatetime = rs.getTimestamp("entry_datetime");
				Date updateDatetime = rs.getTimestamp("update_datetime");
				Boolean delete_flg = rs.getBoolean("delete_flg");
				User user = new User(id, user_id, user_name, email, password, entryDatetime, updateDatetime, delete_flg);
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO user "
				+ " ( user_id"
				+ " ,user_name"
				+ " ,email"
				+ " ,password )"
				+ " values ("
				+ " ? "
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ") ";
		
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getUserName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRow(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(User user) {
		// TODO Auto-generated method stub
		
	}

}
