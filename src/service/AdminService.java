package service;

import java.util.List;

import exception.SearchWrongException;
import model.dto.MemberDTO;

public interface AdminService {
	/*
	 * id로 멤버 검색
	 */
	MemberDTO selectMemberById(String userId) throws SearchWrongException;
	/*
	 * name으로 멤버 검색
	 */
	MemberDTO selectMemberByName(String userName) throws SearchWrongException;
	/*
	 * 전체 멤버 검색
	 */
	List<MemberDTO> selectAllMember() throws SearchWrongException;
}
