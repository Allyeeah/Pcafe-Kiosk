package service;

import java.util.List;

import model.dto.OrdersDTO;

public interface OrderService {
	
	void placeOrder(OrdersDTO order);
	
	void cancelOrder(int orderId);
	
	List<OrdersDTO> findAllOrders();
	
	List<OrdersDTO> findOrdersByUserId(String userId);
}
