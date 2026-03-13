package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

        // item_code로 특정 상품을 조회하는 쿼리
        String sql = "SELECT * FROM item join category using(category_id) WHERE UPPER(item_code) = UPPER(?)";

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

    @Override
    public List<ItemDTO> selectItemsByCodes(List<String> itemCodes) throws SQLException {
        List<ItemDTO> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // itemCodes에 있는 코드로 모든 상품을 조회하는 쿼리
        String placeholders = String.join(", ", Collections.nCopies(itemCodes.size(), "?"));
        String sql = "SELECT * FROM item join category using(category_id) WHERE UPPER(item_code) IN ("
                + placeholders + ")";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < itemCodes.size(); i++) {
                pstmt.setString(i + 1, itemCodes.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(new ItemDTO(
                        rs.getInt("item_id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        }catch(SQLException e) {
			System.out.println("DB 오류가 발생했습니다.");
        } finally {
            DBManager.releaseConnection(conn, pstmt, rs);
        }

        return items;
    }
    
    //상품등록 dao
    @Override
    public int insertItem(ItemDTO newItem) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0; 
        
        String sql = "INSERT INTO item (category_id, item_code, item_name, price) VALUES (?, ?, ?, ?)";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, newItem.getCategoryId());
            pstmt.setString(2, newItem.getItemCode());
            pstmt.setString(3, newItem.getItemName());
            pstmt.setInt(4, newItem.getPrice());
           
            result = pstmt.executeUpdate(); 
        }catch(SQLException e) {
			System.out.println("DB 오류가 발생했습니다.");    
        } finally {
            
            DBManager.releaseConnection(conn, pstmt);
        }
        
        return result; 
    }
    //관리자 상품 수정
	@Override
	public int updateItem(ItemDTO updateItem) throws SQLException {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		String sql = "UPDATE item SET item_code = ?, item_name = ?, price = ? WHERE item_code = ?";
		try {
			conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
			
			//?의 개수만큼 순서대로 setXxx설정 필요.
			pstmt.setString(1, updateItem.getItemCode()); 
	        pstmt.setString(2, updateItem.getItemName()); 
	        pstmt.setInt(3, updateItem.getPrice());       
	        pstmt.setString(4, updateItem.getItemCode());      
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
				System.out.println("DB 오류가 발생했습니다.");
				
		}finally {
			DBManager.releaseConnection(conn, pstmt);
		}
		return result;
	
	}
	//관리자 상품 삭제 
	@Override
	public int deleteItem(ItemDTO deleteItem) throws SQLException {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="delete from item where item_code = ?";
		try {
			conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1,deleteItem.getItemCode());
			
            result = pstmt.executeUpdate();
           
		} catch(SQLException e) {
			System.out.println("DB 오류가 발생했습니다.");
		}finally {
			DBManager.releaseConnection(conn, pstmt);
		}
		
		return result;
	}
}