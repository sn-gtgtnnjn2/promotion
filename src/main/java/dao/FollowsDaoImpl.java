package dao;

import java.util.List;

import javax.sql.DataSource;

import dto.Follows;

public class FollowsDaoImpl implements FollowsDao{
	private DataSource ds;

	public FollowsDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Follows> selectByUserId(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Follows> selectByFollowsId(String followsId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deleteRow(String userId, String followsId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public Follows findByUserIdAndFlsId(String userId, String followsId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
