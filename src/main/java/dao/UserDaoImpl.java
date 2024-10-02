package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import dto.User;

public class UserDaoImpl implements UserDao {
	private DataSource ds;

	UserDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<User> selectAll() {
		String sql = "SELECT " + " id" + " ,user_id" + " ,user_name" + " ,email" + " ,password" + " ,entry_datetime"
				+ " ,update_datetime" + " ,delete_flg" + " FROM user";
		List<User> userList = new ArrayList<User>();
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				Date entryDatetime = rs.getTimestamp("entry_datetime");
				Date updateDatetime = rs.getTimestamp("update_datetime");
				Boolean delete_flg = rs.getBoolean("delete_flg");
				User user = new User(id, user_id, user_name, email, password, entryDatetime, updateDatetime,
						delete_flg);
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public User findById(Integer id) {
		String sql = "SELECT " + " id" + ", user_id" + ", user_name" + ", password" + ", email" + ", entry_datetime"
				+ ", update_datetime" + ", delete_flg" + " FROM user" + " WHERE id = ?";
		User user = new User();
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				user.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				user.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO user " + " ( user_id" + " ,user_name" + " ,email" + " ,password )" + " values ("
				+ " ? " + ", ?" + ", ?" + ", ?" + ") ";

		try (Connection con = ds.getConnection()) {
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

	@Override
	public User findByUserId(String userId) {
		String sql = "SELECT " + " id" + ", user_id" + ", user_name" + ", password" + ", email" + ", entry_datetime"
				+ ", update_datetime" + ", delete_flg" + " FROM user" + " WHERE user_id = ?";
		System.out.println(sql);
		User user = new User();
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setEntryDatetime(rs.getTimestamp("entry_datetime"));
				user.setUpdateDatetime(rs.getTimestamp("update_datetime"));
				user.setDeleteFlg(rs.getBoolean("delete_flg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
//
//	@Override
//	public Boolean isAuthenticated(String userId, String inputPassword) {
//		System.out.println("isAuthenticated");
//		System.out.println(userId);
//		System.out.println(inputPassword);
//		Boolean isAuthenticated = false;
//
//		User user = findByUserId(userId);
//		System.out.println(user.getUserName());
//		System.out.println(user.getPassword());
//		
//		if (Objects.isNull(user.getPassword())) {
//			return isAuthenticated;
//		}
//
//		// 入力値である平文のinputPasswordとDBにあるハッシュ化されたpasswordFromDBを比較
//		if (BCrypt.checkpw(inputPassword, user.getPassword())) {
//			// 処理
//			isAuthenticated = true;
//			System.out.println("認証OK");
//		}
//		System.out.println("最後のリターン前" + isAuthenticated);
//
//		return isAuthenticated;
//	}
	public Boolean isAuthenticated(String userId, String inputPassword) {
	    System.out.println("isAuthenticated");
	    System.out.println(userId);
	    System.out.println(inputPassword);
	    Boolean isAuthenticated = false;

	    User user = findByUserId(userId);
	    if (user == null) {
	        System.out.println("User not found");
	        return isAuthenticated;
	    }

	    System.out.println(user.getUserName());
	    System.out.println(user.getPassword());

	    if (Objects.isNull(user.getPassword())) {
	        return isAuthenticated;
	    }

	    // 入力値である平文のinputPasswordとDBにあるハッシュ化されたpasswordFromDBを比較
	    if (BCrypt.checkpw(inputPassword, user.getPassword())) {
	        // 処理
	        isAuthenticated = true;
	        System.out.println("認証OK");
	    }
	    System.out.println("最後のリターン前" + isAuthenticated);

	    return isAuthenticated;
	}

}
