package model.dao;

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
<<<<<<< HEAD

=======
	public int update(String userId, String userPwd) throws SQLException;
	
>>>>>>> cee423d61ad9b766308a8f6ea37ffffeb7b76c54

}
