package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import common.DBManager;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public class OrderDAOImpl implements OrderDAO {
	private static final OrderDAO instance = new OrderDAOImpl();

	private OrderDAOImpl() {}
	public static OrderDAO getInstance() {
		return instance;
	}

	@Override
	public int insert(OrdersDTO order) throws SQLException {
		Connection con = null;
	    int orderResult = 0;
	    int[] detailResult = null;

	    try {
	    	con = DBManager.getConnection();
	    	con.setAutoCommit(false);

	    	orderResult = insertOrder(con, order);
	    	if (orderResult == 0) {
				throw new SQLException();
			}

	    	detailResult = insertOrderDetails(con, order);
	    	if (IntStream.of(detailResult).anyMatch(t -> t == 0)) {
				throw new SQLException();
			}

	    	con.commit();

	    } catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
	        orderResult = 0;
	    } finally {
	    	DBManager.releaseConnection(con, null);
	    }

	    return orderResult;
	}

	@Override
	public int updateStatus(int orderId, Status status) throws SQLException {
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
		String sql = "select order_id, user_id, order_date, status, total_amount, order_detail_id, item_id, item_code, item_name, unit_price, qty " +
				"from orders join order_detail using(order_id) join item using(item_id) where status = '주문 완료'";

		List<OrdersDTO> orders;

	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	rs = ps.executeQuery();

			orders = getOrdersFromResultSet(rs);
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
		String sql = "select order_id, user_id, order_date, status, total_amount, order_detail_id, item_id, item_code, item_name, unit_price, qty " +
				"from orders join order_detail using(order_id) join item using(item_id)" +
				"where user_id = ?";

		List<OrdersDTO> orders;

	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1, userId);
	    	rs = ps.executeQuery();

			orders = getOrdersFromResultSet(rs);
	    } finally {
	    	DBManager.releaseConnection(con, ps, rs);
	    }

	    return orders;
	}

	@Override
	public List<OrdersDTO> selectByDate(String date) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_id, user_id, order_date, status, total_amount, order_detail_id, item_id, item_code, item_name, unit_price, qty " +
				"from orders join order_detail using(order_id) join item using(item_id)" +
				"where date(order_date) = ?";

		List<OrdersDTO> orders;

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setDate(1, Date.valueOf(date));
			rs = ps.executeQuery();

			orders = getOrdersFromResultSet(rs);
		} finally {
			DBManager.releaseConnection(con, ps, rs);
		}

		return orders;
	}

	private List<OrdersDTO> getOrdersFromResultSet(ResultSet rs) throws SQLException {
		Map<Integer, OrdersDTO> orderMap = new HashMap<>();

		while (rs.next()) {
			int orderId = rs.getInt("order_id");

			OrderDetailDTO detail = new OrderDetailDTO(
					rs.getInt("order_detail_id"),
					orderId,
					rs.getInt("item_id"),
					rs.getString("item_code"),
					rs.getString("item_name"),
					rs.getInt("unit_price"),
					rs.getInt("qty")
			);

			if (orderMap.containsKey(orderId)) {
				orderMap.get(orderId).getOrderDetails().add(detail);
			} else {
				OrdersDTO order = new OrdersDTO(
						orderId,
						rs.getString("user_id"),
						rs.getString("order_date"),
						Status.fromLabel(rs.getString("status")),
						rs.getInt("total_amount")
				);
				order.getOrderDetails().add(detail);
				orderMap.put(orderId, order);
			}
		}

		return new ArrayList<>(orderMap.values());
	}

	@Override
	public OrdersDTO selectById(int orderId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_id, user_id, order_date, status, total_amount, order_detail_id, item_id, item_code, item_name, unit_price, qty " +
				"from orders join order_detail using(order_id) join item using(item_id) " +
				"where order_id = ?";

		OrdersDTO order = null;

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();

			order = getOrdersFromResultSet(rs).getFirst();
		} finally {
			DBManager.releaseConnection(con, ps, rs);
		}

		return order;
	}

	@Override
	public List<OrderDetailDTO> selectByItemCode(String itemCode) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_detail_id, order_id, item_id, item_code, item_name, unit_price, qty " +
				"from order_detail join item using(item_id) where item_code = UPPER(?)";

		List<OrderDetailDTO> details = new ArrayList<>();

	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1, itemCode);
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
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, order.getUserId());
			ps.setInt(2, order.getTotalAmount());

	        re = ps.executeUpdate();
	        rs = ps.getGeneratedKeys();

	        if (rs.next()) {
				order.setOrderId(rs.getInt(1));
				String sql2 = "SELECT order_date FROM orders WHERE order_id = ?";
				ps = con.prepareStatement(sql2);
				ps.clearParameters();
				ps.setInt(1, order.getOrderId());

				rs = ps.executeQuery();

				if (rs.next()) {
					order.setOrderDate(rs.getString("order_date"));
				}
			}
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

}
