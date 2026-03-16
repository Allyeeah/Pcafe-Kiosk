package service;

import java.sql.SQLException;

import exception.NotFoundException;
import exception.UpdateUserInfoException;
import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;
import mvc.session.Session;
import mvc.session.SessionSet;

public class MemberServiceImpl implements MemberService {
    private static final MemberService instance = new MemberServiceImpl();
    MemberDAO memberDao = MemberDAOImpl.getInstance();

    private MemberServiceImpl() {}
    public static MemberService getInstance() {
        return instance;
    }
    //로그인
    @Override
    public MemberDTO login(String userId, String userPwd) throws NotFoundException, SQLException {

        MemberDTO member = memberDao.login(userId, userPwd);
        if (member == null) {
            throw new NotFoundException("정보를 다시 확인해주세요.");
        }
        //로그인 된 정보 저장하기
        Session session = new Session(userId);
        SessionSet sessionSet = SessionSet.getInstance();
        sessionSet.add(session);

        return member;
    }

    // 사용자 비번, 이름 변경
    @Override
    public void updateMemberInfo(String userId, String userPwd, String userName) throws SQLException {
        int result = memberDao.updateMemberInfo(userId, userPwd, userName);
        if (result == 0) {
            throw new UpdateUserInfoException("사용자 정보 수정에 실패했습니다.");
        }
    }

    @Override
    public void withdrawMember(String userId, String userPwd) throws SQLException {
        int result = memberDao.withdrawMember(userId, userPwd);

        if (result == 0) {
            throw new UpdateUserInfoException("사용자 탈퇴에 실패했습니다.");
        }

        SessionSet se = SessionSet.getInstance();
        se.remove(userId);
    }
}
