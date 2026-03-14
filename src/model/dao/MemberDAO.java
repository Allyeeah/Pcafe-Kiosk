package model.dao;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.List;
import exception.SearchWrongException;
import model.dto.MemberDTO;

public interface MemberDAO {
	public int insert(MemberDTO memberDTO);
	MemberDTO login(String userId, String userPwd)throws SQLException;
	MemberDTO selectMemberById(String userId) throws SearchWrongException;
	MemberDTO selectMemberByName(String userName) throws SearchWrongException;
	List<MemberDTO> selectAllMember() throws SearchWrongException;
	public int update(String userId, String userPwd) throws SQLException;
	

}
