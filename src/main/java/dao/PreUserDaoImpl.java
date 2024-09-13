package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import dto.PreUser;
import util.GeneralFormatter;

public class PreUserDaoImpl implements PreUserDao{

	private DataSource ds;

	public PreUserDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<PreUser> selectAll() {
		String sql = "SELECT "
				+ " id"
				+ " ,email"
				+ " ,token"
				+ " ,enabled"
				+ " ,expires_at"
				+ " ,entry_datetime"
				+ " ,update_datetime"
				+ " ,delete_flg"
				+ " FROM pre_user";
		List<PreUser> preUserList = new ArrayList<PreUser>();
		try(Connection con = ds.getConnection()){
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String token = rs.getString("token");
				Boolean enabled = rs.getBoolean("enabled");
				Date expiresAt = rs.getTimestamp("expires_at");
				Date entryDatetime = rs.getTimestamp("entry_datetime");
				Date updateDatetime = rs.getTimestamp("update_datetime");
				Boolean delete_flg = rs.getBoolean("delete_flg");
				PreUser pu = new PreUser(id, email, token, enabled, expiresAt, entryDatetime, updateDatetime, delete_flg);
				preUserList.add(pu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preUserList;
	}

	@Override
	public PreUser findById(Integer paramId) {
		String sql = "SELECT "
				+ " id"
				+ " ,email"
				+ " ,token"
				+ " ,enabled"
				+ " ,expires_at"
				+ " ,entry_datetime"
				+ " ,update_datetime"
				+ " ,delete_flg"
				+ " FROM pre_user"
				+ " WHERE id = ?";
		PreUser pu = null;
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, paramId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String token = rs.getString("token");
				Boolean enabled = rs.getBoolean("enabled");
				Date expiresAt = rs.getTimestamp("expires_at");
				Date entryDatetime = rs.getTimestamp("entry_datetime");
				Date updateDatetime = rs.getTimestamp("update_datetime");
				Boolean delete_flg = rs.getBoolean("delete_flg");
				pu = new PreUser(id, email, token, enabled, expiresAt, entryDatetime, updateDatetime,
						delete_flg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pu;

	}

	@Override
	public PreUser findByEmailAndToken(String paramEmail, String paramToken) {
		String sql = "SELECT "
				+ " id"
				+ " ,email"
				+ " ,token"
				+ " ,enabled"
				+ " ,expires_at"
				+ " ,entry_datetime"
				+ " ,update_datetime"
				+ " ,delete_flg"
				+ " FROM pre_user"
				+ " WHERE email = ? "
				+ " AND token = ?";
		PreUser pu = null;
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, paramEmail);
			stmt.setString(2, paramToken);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String token = rs.getString("token");
				Boolean enabled = rs.getBoolean("enabled");
				Date expiresAt = rs.getTimestamp("expires_at");
				Date entryDatetime = rs.getTimestamp("entry_datetime");
				Date updateDatetime = rs.getTimestamp("update_datetime");
				Boolean delete_flg = rs.getBoolean("delete_flg");
				pu = new PreUser(id, email, token, enabled, expiresAt, entryDatetime, updateDatetime,
						delete_flg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pu;
	}

	@Override
	public void insert(PreUser preUser) {
		String sql = "INSERT INTO pre_user ("
				+ " email"
				+ " ,token"
				+ " ,enabled"
				+ " ,expires_at"
				+ " ) values ("
				+ " ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ") ";
		
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, preUser.getEmail());
			stmt.setString(2, preUser.getToken());
			stmt.setBoolean(3, preUser.getEnabled());
			stmt.setTimestamp(4, GeneralFormatter.convDateToSqlTimestamp(preUser.getExpiresAt()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateRow(PreUser preUser) {
		String sql = "UPDATE pre_user ("
				+ " email"
				+ " ,token"
				+ " ,enabled"
				+ " ,expires_at"
				+ " ) set ("
				+ " ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ") WHERE id = ?";
		
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, preUser.getEmail());
			stmt.setString(2, preUser.getToken());
			stmt.setBoolean(3, preUser.getEnabled());
			stmt.setTimestamp(4, GeneralFormatter.convDateToSqlTimestamp(preUser.getExpiresAt()));
			stmt.setInt(5, preUser.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(PreUser preUser) {
		String sql = "DELETE FROM pre_user WHERE id = ?";
		
		try (Connection con = ds.getConnection()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, preUser.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
