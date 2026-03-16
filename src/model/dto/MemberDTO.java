package model.dto;

public class MemberDTO {
    private String userId;
    private String userPw;
    private String userName;
    private String isAdmin;  // 'Y' or 'N' 식별
    private java.sql.Timestamp createdAt;
    private String isDeleted;  // 'Y' or 'N' 식별

    public MemberDTO() {}

    public MemberDTO(String userId, String userPw, String userName, String isAdmin, java.sql.Timestamp createdAt, String isDeleted) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    // getter / setter
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserPw() {
        return userPw;
    }
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[userId: ");
		builder.append(userId);
		builder.append(" | userPw: ");
		builder.append(userPw);
		builder.append(" | userName: ");
		builder.append(userName);
		builder.append(" | isAdmin: ");
		builder.append(isAdmin);
		builder.append(" | createdAt: ");
		builder.append(createdAt);
		builder.append(" | isDeleted: ");
		builder.append(isDeleted);
		builder.append("]");
		return builder.toString();
	}


	
    
}