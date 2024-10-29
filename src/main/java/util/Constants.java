package util;

public class Constants {
	public static final int EVENT_APPROVAL_SIGNUP = 0;
	public static final int EVENT_APPROVAL_AVAILABLE = 1;
	public static final int EVENT_APPROVAL_REJECT = 2;
	public static final int EVENT_APPROVAL_CANCEL = 3;
	
	public static String getApprovalStatusName(Integer approvalStatus) {
		String statusName = null;
		switch(approvalStatus) {
		case EVENT_APPROVAL_SIGNUP:
			statusName = "申請中";
		break;
		
		case EVENT_APPROVAL_AVAILABLE:
			statusName = "承認済み";
		break;
		
		case EVENT_APPROVAL_REJECT:
			statusName = "却下";
		break;
		
		case EVENT_APPROVAL_CANCEL:
			statusName = "中止";
		break;
		default :
			statusName = "ステータスなし";
		}
		return statusName;
	}
	
}
