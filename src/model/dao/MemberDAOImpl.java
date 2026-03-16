package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import exception.SearchWrongException;
import model.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {

    private static MemberDAO instance = new MemberDAOImpl();
    public MemberDAOImpl() { }
    public static MemberDAO getInstance() {
        return instance;
    }


	@Override
	public int insert(MemberDTO memberDTO) {
		Connection con = null;
		PreparedStatement pstmt = null;
	    String sql = "INSERT INTO member (user_id, user_pw, user_name, is_admin) VALUES (?, ?, ?,?)";
	    int re = 0;

	    try {
	    	con = DBManager.getConnection();
	        pstmt = con.prepareStatement(sql); //sql

	        pstmt.setString(1, memberDTO.getUserId());
	        pstmt.setString(2, memberDTO.getUserPw());
	        pstmt.setString(3, memberDTO.getUserName());
	        pstmt.setString(4, memberDTO.getIsAdmin());

	        re = pstmt.executeUpdate();
	        System.out.println("회원가입 성공입니다.");
	    } catch (SQLException e) {
	        System.out.println("아이디 중복입니다. ");
	        e.printStackTrace();
	    } finally {
	    	DBManager.releaseConnection(con, pstmt);
	    }

	    return re;
	}

	@Override
	public MemberDTO login(String userId, String userPwd) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberDTO member=null;

		String sql="select * from member where user_id=? and user_pw=?";
		

		try {
		con=DBManager.getConnection();
		ps=con.prepareStatement(sql);
		ps.setString(1, userId);
		ps.setString(2, userPwd);

		rs=ps.executeQuery();

		if(rs.next()) {



			member = new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getTimestamp(5), rs.getString(6));
			
		}


		}finally{
			DBManager.releaseConnection(con,ps,rs);
		}

		return member;
	}

	/*
	 * id로 검색
	 * select * from member where user_id=?
	 */
	@Override
	public MemberDTO selectMemberById(String userId) throws SearchWrongException{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberDTO member=null;
		String sql="select * from member where user_id=?";
		try {
		con=DBManager.getConnection();
		ps=con.prepareStatement(sql);
		ps.setString(1, userId);
		rs=ps.executeQuery();

		while(rs.next()) {
			member = new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getTimestamp(5),rs.getString(6));
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(con, ps, rs);
		}
		return member;
	}



	/*
	 * name으로 검색
	 */
	@Override
	public MemberDTO selectMemberByName(String userName) throws SearchWrongException{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberDTO member=null;
		String sql="select * from member where user_name=?";
		try {
		con=DBManager.getConnection();
		ps=con.prepareStatement(sql);
		ps.setString(1, userName);
		rs=ps.executeQuery();

		while(rs.next()) {
			member = new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getTimestamp(5), rs.getString(6));
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(con, ps, rs);
		}
		return member;
	}



	/*
	 * member 전체 검색
	 * select * from member;
	 */
	@Override
	public List<MemberDTO> selectAllMember() throws SearchWrongException{

		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MemberDTO> list = new ArrayList<>();

		String sql="select * from member";

		try {
			con=DBManager.getConnection();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();

			while(rs.next()) {
				MemberDTO member = new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getTimestamp(5), rs.getString(6));
				list.add(member);

			}
			System.out.println("전체 조회 완료");
		} catch (SQLException e) {

			e.printStackTrace();
			throw new SearchWrongException("DB에 문제가 있어 다시 진행해주세요.");
		}finally {
			DBManager.releaseConnection(con, ps, rs);
		}


		return list;
	}

	
	
	
	/*
	 * member pwd, name 수정
	 * 
	 */
	
	public int update(String userPwd, String userName) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		String sql = "update member set user_pw=?, user_name=?";
		
		try {
			con=DBManager.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, userPwd);
			ps.setString(2, userName);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("DB 오류가 발생했습니다.");
			//e.printStackTrace();
		}
		finally {
            DBManager.releaseConnection(con, ps); 
       }
		
		return result;
		
	}
	/*
	 * userpwd, username 변경
	 * update member set user_pw='1234', user_name='dd' where user_id='user1'
	 * 
	 * 
	 */
	@Override
	public int updateMemberInfo(String userId, String userPwd, String userName) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		String sql = "update member set user_pw = ?, user_name = ? where user_id = ?";
		
		try {
		con=DBManager.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, userPwd);
		ps.setString(2, userName);
		ps.setString(3, userId);
		
		result = ps.executeUpdate();
		
		}catch(SQLException e) {
			
		}
		
		return result;
	}
}






