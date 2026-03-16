package service;

import exception.NotFoundException;
import model.dto.MemberDTO;

import java.sql.SQLException;

public interface MemberService {
	MemberDTO login(String userId, String userPwd) throws NotFoundException, SQLException;

	void updateMemberInfo(String userId, String userPwd, String userName) throws SQLException;

	void withdrawMember(String userId, String userPwd) throws SQLException;
}
