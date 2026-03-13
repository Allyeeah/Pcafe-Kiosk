package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import model.dto.CategoryDTO;

public class CategoryDAOImpl implements CategoryDAO {
	private static CategoryDAO instance = new CategoryDAOImpl();
	
	public static CategoryDAO getInstance() {
		return instance;
	}
	
	@Override
    public List<CategoryDTO> selectAll() throws SQLException {
        List<CategoryDTO> categoryList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        // 카테고리만 전체 조회 
        String sql = "SELECT category_id, category_name FROM category ORDER BY category_id";
        
        try {
             conn = DBManager.getConnection();
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CategoryDTO category = new CategoryDTO(
                    rs.getInt("category_id"),
                    rs.getString("category_name")
                );
                categoryList.add(category); 
            }
        } finally {
             DBManager.releaseConnection(conn, pstmt, rs); 
        }
        
        return categoryList;
    }
	
	// 카테고리 삽입(추가)
	@Override
	public int insert(String category) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql = "insert into category (category_name) values (?)";
		try {
		con=DBManager.getConnection();
		ps=con.prepareStatement(sql);
		ps.setString(1, category);
		
		result = ps.executeUpdate();
		 
	        System.out.println("카테고리가 추가되었습니다.");
		}catch(SQLException e) {
			System.out.println("카테고리 중복입니다. ");
		}finally {
            DBManager.releaseConnection(con, ps); 
       }
		return result;
	}
	
	// 카테고리 수정
	@Override
	public int update(String category, int categoryId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		String sql="update category set category_name = ? where category_id = ?";
		
		try {
			con=DBManager.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, category);
			ps.setInt(2, categoryId);
			
			result = ps.executeUpdate();
			System.out.println("카테고리가 변경되었습니다.");
			
		}catch(SQLException e) {
			System.out.println("입력하신 카테고리 번호가 없습니다.");
		}
		finally {
            DBManager.releaseConnection(con, ps); 
       }
		return result;
	}
	
	// 카테고리 삭제
	@Override
	public int delete(int categoryId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		String sql="delete from category where category_id = ?";
		
		try {
			con=DBManager.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			
			result = ps.executeUpdate();
			System.out.println("카테고리가 변경되었습니다.");
			
		}catch(SQLException e) {
			System.out.println("입력하신 카테고리 번호가 없습니다.");
		}
		finally {
            DBManager.releaseConnection(con, ps); 
       }
		return result;
	} 
	
	
	
}



