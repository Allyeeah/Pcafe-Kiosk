package service;

import model.dto.OrdersDTO;

public interface OrderService {
	
	void placeOrder(OrdersDTO order);
	
	void cancelOrder(int orderId);
}
