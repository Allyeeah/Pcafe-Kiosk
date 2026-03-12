package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
	public int updateStatus(int orderId, Status status) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update orders set status = ? where order_id = ?";
	    int re = 0;
	    
	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1, status.label());
	    	ps.setInt(2, orderId);
	    	re = ps.executeUpdate();
	    	
	    } catch (SQLException e) {
	        re = 0;
	    } finally {
	    	DBManager.releaseConnection(con, null);
	    }
	    
	    return re;
	}

	@Override
	public List<OrdersDTO> selectAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from orders";
		
		List<OrdersDTO> orders = new ArrayList<>();
	    
	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		//dto 수정ㄴㄴ
	    		OrdersDTO order = new OrdersDTO(
	    			    rs.getInt(1), 
	    			    rs.getString(2), 
	    			    rs.getString(3), 
	    			    Status.fromLabel(rs.getString(4)),
	    			    rs.getInt(5)
	    			);
	    		order.setOrderDetails(selectOrderDetails(con, order.getOrderId()));
	    		
	    		orders.add(order);
	    	}
	    } finally {
	    	DBManager.releaseConnection(con, ps, rs);
	    }
	    
	    return orders;
	}

	@Override
	public List<OrdersDTO> selectByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from orders where user_id = ?";
		
		List<OrdersDTO> orders = new ArrayList<>();
	    
	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1, userId);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		//변경된 dto에 맞춰서 수정 - 오혜진
	    		OrdersDTO order = new OrdersDTO(
	                    rs.getInt("order_id"), 
	                    rs.getString("user_id"), 
	                    rs.getString("order_date"), 
	                    Status.fromLabel(rs.getString(4)),
	                    rs.getInt("total_amount") 
	                );
	    		order.setOrderDetails(selectOrderDetails(con, order.getOrderId()));
	    		
	    		orders.add(order);
	    	}
	    } finally {
	    	DBManager.releaseConnection(con, ps, rs);
	    }
	    
	    return orders;
	}

	@Override
	public List<OrderDetailDTO> selectByItemId(int itemId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_detail_id, order_id, item_id, item_code, item_name, unit_price, qty " +
				"from order_detail join item using(item_id) where item_id = ?";
		
		List<OrderDetailDTO> details = new ArrayList<>();
	    
	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setInt(1, itemId);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		//dto 수정에 따라서 변경2 - 오혜진
	    		OrderDetailDTO detail = new OrderDetailDTO(
	                    rs.getInt(1),    
	                    rs.getInt(2),   
	                    rs.getInt(3),
						rs.getString(4),
	                    rs.getString(5),
	                    rs.getInt(6),
	                    rs.getInt(7)
	                );
	    		
	    		details.add(detail);
	    	}
	    } finally {
	    	DBManager.releaseConnection(con, ps, rs);
	    }
	    
	    return details;
	}

	private int insertOrder(Connection con, OrdersDTO order) throws SQLException {
		String sql = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int re = 0;
		
		try {
			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setString(1, order.getUserId());
			ps.setInt(2, order.getTotalAmount());

	        re = ps.executeUpdate();
	        rs = ps.getGeneratedKeys();
	        
	        if (rs.next()) order.setOrderId(rs.getInt(1));
		} finally {
			DBManager.releaseConnection(null, ps, rs);
		}
		
		return re;
	}
	
	private int[] insertOrderDetails(Connection con, OrdersDTO order) throws SQLException{
		String sql = "INSERT INTO order_detail (order_id, item_id, unit_price, qty) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = null;
		int[] re = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			for (OrderDetailDTO detail : order.getOrderDetails()) {
				ps.setInt(1, order.getOrderId());
				ps.setInt(2, detail.getItemId());
				ps.setInt(3, detail.getUnitPrice()); 
				ps.setInt(4, detail.getQty());//추가
				
				ps.addBatch();
				ps.clearParameters();
			}
			re = ps.executeBatch();
		} finally {
			DBManager.releaseConnection(null, ps);
		}
		
		return re;
	}
	
	private List<OrderDetailDTO> selectOrderDetails(Connection con, int orderId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_detail_id, order_id, item_id, item_code, item_name, unit_price, qty " +
				"from order_detail join item using(item_id) where order_id = ?";
		
		List<OrderDetailDTO> details = new ArrayList<>();
	    
	    try {
	    	ps = con.prepareStatement(sql);
	    	ps.setInt(1, orderId);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		details.add(new OrderDetailDTO(
	                    rs.getInt(1),
	                    rs.getInt(2), 
	                    rs.getInt(3),
						rs.getString(4),
	                    rs.getString(5), // itemName
	                    rs.getInt(6),    // unitPrice
	                    rs.getInt(7)     // qty
	                ));
	    	}
	    } finally {
	    	DBManager.releaseConnection(null, ps, rs);
	    }
	    
	    return details;
	}
	
	public static void main(String[] args) {
		OrdersDTO order = new OrdersDTO();
		order.setUserId("ljg");
		List<OrderDetailDTO> details = List.of( //dto 추가에 따라서 수정
				new OrderDetailDTO(0, 0, 1, "101", "신라면", 4500, 1),
			    new OrderDetailDTO(0, 0, 3, "203", "짜계치", 3500, 2)
				);
		order.setOrderDetails(details);
		order.updateTotalAmount();
		
//		getInstance().insert(order);
//		getInstance().updateStatus(5, Status.CANCELED);
		try {
//			System.out.println(getInstance().selectAll());
//			System.out.println(getInstance().selectByUserId("ljg"));
			System.out.println(getInstance().selectByItemId(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
