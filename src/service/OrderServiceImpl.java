package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dao.OrderDAO;
import model.dao.OrderDAOImpl;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public class OrderServiceImpl implements OrderService {
	private static OrderService instance = new OrderServiceImpl();
	private OrderDAO orderDAO = OrderDAOImpl.getInstance();
	
	private OrderServiceImpl() {}
	public static OrderService getInstance() {
		return instance;
	}

	@Override
	public void placeOrder(OrdersDTO order) {
		int result = orderDAO.insert(order);
		
		if (result == 0) throw new OrderFailedException();
	}
	
	@Override
	public void cancelOrder(int orderId) {
		int result = orderDAO.updateStatus(orderId, Status.CANCELED);
		
		if (result == 0) throw new CancelFailedException();
	}
	
	@Override
	public List<OrdersDTO> findAllOrders() {
		List<OrdersDTO> orders = new ArrayList<>();
		try {
			orders = orderDAO.selectAll();
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) throw new OrderNotFoundException();
		
		return orders;
	}

}
