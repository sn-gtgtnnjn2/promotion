package dao;

import java.sql.SQLException;
import java.util.List;

import dto.EntryApproval;
import dto.EntryApprovalWithPict;


public interface EntryApprovalDao {
	List<EntryApproval> selectAll();
	EntryApproval findById(Integer id);
	List<EntryApproval> selectByEventId(Integer eventId);
	List<EntryApproval> selectByUserId(String userId) throws SQLException;
	void insert(EntryApproval EventApproval) throws SQLException;
	void updateRow(EntryApproval EventApproval);
	void deleteById(EntryApproval EventApproval);
	public List<EntryApproval> selectByEventIds(List<Integer> eventIds);
	List<EntryApprovalWithPict> selectByEventIdWithPict(Integer eventId);
	List<EntryApprovalWithPict> selectByEventIdWithPict(Integer eventId, Integer approvalStatus);
	void updateApproveStatus(List<EntryApproval> approveTargetList, Integer updateStatus) throws SQLException;
}
