package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import common.DBManager;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public class OrderDAOImpl implements OrderDAO {
	private static OrderDAO instance = new OrderDAOImpl();
	
	private OrderDAOImpl() {}
	public static OrderDAO getInstance() {
		return instance;
	}

	@Override
	public int insert(OrdersDTO order) {
		Connection con = null;
	    int orderResult = 0;
	    int[] detailResult = null;
	    
	    try {
	    	con = DBManager.getConnection();
	    	con.setAutoCommit(false);
	    	
	    	orderResult = insertOrder(con, order);
	    	if (orderResult == 0) throw new SQLException();
	    	
	    	detailResult = insertOrderDetails(con, order);
	    	if (IntStream.of(detailResult).anyMatch(t -> t == 0)) throw new SQLException();
	    	
	    	con.commit();
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	        try {
				if (con != null) con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	        orderResult = 0;
	        
	    } finally {
	    	DBManager.releaseConnection(con, null);
	    }
	    
	    return orderResult;
	}
	
	@Override
	public int updateStatus(OrdersDTO order, Status status) {
		// TODO Auto-generated method stub
		return 0;
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

	private int insertOrder(Connection con, OrdersDTO order) throws SQLException {
		String sql = "INSERT INTO orders (user_id) VALUES (?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int re = 0;
		
		try {
			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setString(1, order.getUserId());

	        re = ps.executeUpdate();
	        rs = ps.getGeneratedKeys();
	        
	        if (rs.next()) order.setOrderId(rs.getInt(1));
		} finally {
			DBManager.releaseConnection(null, ps, rs);
		}
		
		return re;
	}
	
	private int[] insertOrderDetails(Connection con, OrdersDTO order) throws SQLException{
		String sql = "INSERT INTO order_detail (order_id, item_id, quantity) VALUES (?, ?, ?)";
		PreparedStatement ps = null;
		int[] re = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			for (OrderDetailDTO detail : order.getOrderDetails()) {
				ps.setInt(1, order.getOrderId());
				ps.setInt(2, detail.getItemId());
				ps.setInt(3, detail.getQuantity());
				
				ps.addBatch();
				ps.clearParameters();
			}
			re = ps.executeBatch();
		} finally {
			DBManager.releaseConnection(null, ps);
		}
		
		return re;
	}
	/*
	public static void main(String[] args) {
		OrdersDTO order = new OrdersDTO();
		order.setUserId("ljg");
		List<OrderDetailDTO> details = List.of(
				new OrderDetailDTO(0, 0, 1, 2),
				new OrderDetailDTO(0, 0, 2, 3)
				);
		order.setOrderDetails(details);
		
		getInstance().insert(order);
	}*/
}
