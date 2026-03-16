package service;

import java.sql.SQLException;

import exception.NotFoundException;
import exception.UpdateUserInfoException;
import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;
import mvc.session.Session;
import mvc.session.SessionSet;

public class MemberService {
MemberDAO memberDao = new MemberDAOImpl();

<<<<<<< HEAD
//로그인
public MemberDTO login(String userId, String userPwd) throws NotFoundException, SQLException{

=======
	//로그인
	public MemberDTO login(String userId, String userPwd) throws NotFoundException, SQLException{
	
>>>>>>> cee423d61ad9b766308a8f6ea37ffffeb7b76c54
	MemberDTO member = memberDao.login(userId, userPwd);
	if(member==null) {
		throw new NotFoundException("정보를 다시 확인해주세요.");
	}
	//로그인 된 정보 저장하기
	Session session = new Session(userId);

	SessionSet sessionSet = SessionSet.getInstance();

	sessionSet.add(session);

	return member;
	}
	// 사용자 비번, 이름 변경
	public void update(String userId, String userPwd) throws SQLException{
	
	    int result = memberDao.update(userId, userPwd);
	    
	    // (선택사항) 만약 update 쿼리는 날아갔는데 영향받은 행이 0개일 때를 대비한 2중 방어
	    if (result == 0) {
	        throw new UpdateUserInfoException("사용자 정보 수정에 실패했습니다.");
	    }
	
	}
}
