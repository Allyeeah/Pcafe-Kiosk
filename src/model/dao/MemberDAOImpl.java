package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.DBManager;
import model.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {
	private static MemberDAO instance = new MemberDAOImpl();

	public static MemberDAO getInstance() {
		return instance;
	}
	
	@Override
	public int insert(MemberDTO memberDTO) {
		Connection con = null;
		PreparedStatement pstmt = null;
	    String sql = "INSERT INTO member (user_id, user_pw, user_name) VALUES (?, ?, ?)";
	    int re = 0;
	    
	    try {
	    	con = DBManager.getConnection();
	        pstmt = con.prepareStatement(sql); //sql
	        
	        pstmt.setString(1, memberDTO.getUserId());
	        pstmt.setString(2, memberDTO.getUserPw());
	        pstmt.setString(3, memberDTO.getUserName());
	        
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
}
