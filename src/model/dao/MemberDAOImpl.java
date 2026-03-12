package model.dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.DBManager;
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
		String sql="select * from Member where user_id=? and user_pw=?";
		
		try {
		con=DBManager.getConnection();
		ps=con.prepareStatement(sql);
		ps.setString(1, userId);
		ps.setString(2, userPwd);
		
		rs=ps.executeQuery();
		
		if(rs.next()) {
			member = new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getTimestamp(5));
			
		}
		
		
		}finally{
			DBManager.releaseConnection(con,ps,rs);
		}
		
		return member;
	}

	

	
}
