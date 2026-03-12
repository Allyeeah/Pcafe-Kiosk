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
}

