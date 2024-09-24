package dao;

import java.sql.SQLException;
import java.util.List;

import dto.EntryApproval;


public interface EntryApprovalDao {
	List<EntryApproval> selectAll();
	EntryApproval findById(Integer id);
	EntryApproval selectByEventId(Integer eventId);
	List<EntryApproval> selectByUserId(String userId) throws SQLException;
	void insert(EntryApproval EventApproval) throws SQLException;
	void updateRow(EntryApproval EventApproval);
	void deleteById(EntryApproval EventApproval);
	public List<EntryApproval> selectByEventIds(List<Integer> eventIds);
}
