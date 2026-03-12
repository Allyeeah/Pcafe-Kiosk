package service;

import java.lang.reflect.Member;
import java.sql.SQLException;

import exception.NotFoundException;
import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;
import mvc.session.Session;
import mvc.session.SessionSet;

public class MemberService {
MemberDAO memberDao = new MemberDAOImpl();

//로그인
public MemberDTO login(String userId, String userPwd) throws NotFoundException, SQLException{
	
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
}
