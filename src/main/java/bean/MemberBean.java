package bean;

import java.io.Serializable;

public class MemberBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String signUpDateStr;
	private String imageString;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSignUpDateStr() {
		return signUpDateStr;
	}
	public void setSignUpDateStr(String signUpDateStr) {
		this.signUpDateStr = signUpDateStr;
	}
	public String getImageString() {
		return imageString;
	}
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
}
