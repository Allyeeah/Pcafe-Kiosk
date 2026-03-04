package model.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import model.dto.MemberDTO;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	Connection con = null;
	PreparedStatement pstmt = null;
	
	
	private String driver;
    private String url;
    private String username;
    private String password;
	
	 public MemberDAO() {
	        try {
	            Properties prop = new Properties();
	            InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
	            prop.load(is);

	            this.driver = prop.getProperty("driver");
	            this.url = prop.getProperty("url");
	            this.username = prop.getProperty("user");
	            this.password = prop.getProperty("password");

	            Class.forName(driver); 
	            this.con = getConnection(); //0304 혜진추가
	            
	            getConnection();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	    public Connection getConnection() {
	        Connection con = null;
	        try {
	            con = DriverManager.getConnection(url, username, password);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return con;
	    }


	    public void finally_ck(PreparedStatement pstmt, Connection con, ResultSet rs) {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public void insert(MemberDTO memberDTO) {
	    String sql = "INSERT INTO member (user_id, user_pw, user_name) VALUES (?, ?, ?)";
	    
	    try {
	        pstmt = con.prepareStatement(sql); //sql
	        pstmt.setString(1, memberDTO.getUserId());
	        pstmt.setString(2, memberDTO.getUserPw());
	        pstmt.setString(3, memberDTO.getUserName());
	        pstmt.executeUpdate();
	        System.out.println("회원가입 성공입니다.");
	    } catch (SQLException e) {
	        System.out.println("아이디 중복입니다. ");
	        e.printStackTrace();
	    }
	}
}
