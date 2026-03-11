package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import common.DBManager;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;

public class OrderDAOImpl implements OrderDAO {
	private static OrderDAO instance = new OrderDAOImpl();
	
	private OrderDAOImpl() {}
	public static OrderDAO getInstance() {
		return instance;
	}

	@Override
	public int insert(OrdersDTO order) {
		Connection con = null;
	    int re = 0;
	    
	    try {
	    	con = DBManager.getConnection();
	    	con.setAutoCommit(false);
	    	
	    	re = insertOrder(con, order);
	    	if (re == 0) throw new SQLException();
	    	
	    	order.setOrderId(getLastInsertId(con));
	    	
	    	re = insertOrderDetails(con, order);
	    	if (re == 0) throw new SQLException();
	    	
	    	con.commit();
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	        try {
				if (con != null) con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	        re = 0;
	        
	    } finally {
	    	DBManager.releaseConnection(con, null);
	    }
	    
	    return re;
	}

	@Override
	public int delete(OrdersDTO order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrdersDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdersDTO> selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetailDTO> selectByItemId(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int getLastInsertId(Connection con) throws SQLException {
		String sql = "select last_insert_id()";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int re = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) re = rs.getInt(1);
		} finally {
			DBManager.releaseConnection(null, pstmt, rs);
		}
		
		return re;
	}

	private int insertOrder(Connection con, OrdersDTO order) throws SQLException {
		String sql = "INSERT INTO orders (user_id) VALUES (?)";
		PreparedStatement pstmt = null;
		int re = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, order.getUserId());

	        re = pstmt.executeUpdate();
		} finally {
			DBManager.releaseConnection(null, pstmt);
		}
		
		return re;
	}
	
	private int insertOrderDetails(Connection con, OrdersDTO order) throws SQLException{
		String sql = "INSERT INTO order_detail (order_id, item_id, quantity) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		int re = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			List<OrderDetailDTO> details = order.getOrderDetails();
			for (OrderDetailDTO detail : details) {
				pstmt.setInt(1, order.getOrderId());
				pstmt.setInt(2, detail.getItemId());
				pstmt.setInt(3, detail.getQuantity());
				
				re = pstmt.executeUpdate();
				if (re == 0) return re;
			}
		} finally {
			DBManager.releaseConnection(null, pstmt);
		}
		
		return re;
	}
}
