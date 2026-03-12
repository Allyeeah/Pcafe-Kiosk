package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import model.dto.ItemDTO;

public class ItemDAOImpl implements ItemDAO {
	private static ItemDAOImpl instance = new ItemDAOImpl();

    private ItemDAOImpl() {}
    public static ItemDAOImpl getInstance() {
        return instance;
    }

	@Override
    public List<ItemDTO> ItemSelect() throws SQLException {
        List<ItemDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        // 전체 상품을 조회하는 쿼리
        String sql = "SELECT * FROM item ORDER BY item_id";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	ItemDTO item = new ItemDTO();
            	item.setItemId(rs.getInt("item_id"));
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getInt("price"));
                item.setCategoryId(rs.getInt("category_id"));
                list.add(item); 
            }
        } finally {
         
            DBManager.releaseConnection(conn, pstmt, rs);
        }
        
        return list; 
    }

    // 2. 카테고리별 상품 조회 (JOIN 쿼리)
    @Override
    public List<ItemDTO> selectItemsByCategory(int categoryId) throws SQLException {
        List<ItemDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 카테고리 이름을 함께 가져오는 JOIN 쿼리
        String sql = "SELECT i.item_id, i.item_code, i.item_name, i.price, i.category_id, c.category_name "
                   + "FROM item i "
                   + "JOIN category c ON i.category_id = c.category_id "
                   + "WHERE i.category_id = ?";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId); // 사용자 입력값
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
             
                ItemDTO item = new ItemDTO(
                    rs.getInt("item_id"),
                    rs.getString("item_code"),
                    rs.getString("item_name"),
                    rs.getInt("price"),
                    rs.getInt("category_id"),
                    rs.getString("category_name") 
                );
                list.add(item);
            }
        } finally {
            DBManager.releaseConnection(conn, pstmt, rs);
        }

        return list; 
    }

    @Override
    public ItemDTO selectItemByCode(String code) throws SQLException {
        ItemDTO item = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // item_id로 특정 상품을 조회하는 쿼리
        String sql = "SELECT * FROM item join category using(category_id) WHERE item_code = ?";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new ItemDTO(
                        rs.getInt("item_id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
            }
        } finally {
            DBManager.releaseConnection(conn, pstmt, rs);
        }

        return item;
    }
}