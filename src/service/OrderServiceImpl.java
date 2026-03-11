package service;

import exception.OrderFailedException;
import model.dao.OrderDAO;
import model.dao.OrderDAOImpl;
import model.dto.OrdersDTO;

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

}
