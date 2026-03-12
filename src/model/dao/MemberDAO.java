package model.dao;

import java.lang.reflect.Member;
import java.sql.SQLException;

import model.dto.MemberDTO;

public interface MemberDAO {
	int insert(MemberDTO memberDTO);

	MemberDTO login(String userId, String userPwd)throws SQLException;
}
