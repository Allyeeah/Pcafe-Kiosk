package service;

import java.util.List;

import exception.SearchWrongException;
import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;

public class AdminServiceImpl implements AdminService {
	private static AdminService instance = new AdminServiceImpl();
	private MemberDAO memberDao = MemberDAOImpl.getInstance();

	private AdminServiceImpl() {}

	public static AdminService getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
	@Override
	public MemberDTO selectMemberById(String userId) throws SearchWrongException{
		MemberDTO member = memberDao.selectMemberById(userId);
		if(member == null) {
			throw new SearchWrongException("검색된 ID의 사용자가 없습니다.");
		}
		return member;
	}

	@Override
	public MemberDTO selectMemberByName(String userName) throws SearchWrongException{
		MemberDTO member = memberDao.selectMemberByName(userName);
		if(member == null) {
			throw new SearchWrongException("검색된 이름의 사용자가 없습니다.");
		}
		return member;
	}

	@Override
	public List<MemberDTO> selectAllMember() throws SearchWrongException {
		List<MemberDTO> list = memberDao.selectAllMember();
		if(list.isEmpty()) {
			throw new SearchWrongException("검색된 사용자가 없습니다.");
		}
		return list;
	}


}
