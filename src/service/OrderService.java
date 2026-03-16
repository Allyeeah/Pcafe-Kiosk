package service;

import java.util.List;

import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;

public interface OrderService {

	void placeOrder(OrdersDTO order);

	void cancelOrder(int orderId);

	List<OrdersDTO> findAllOrders();

	List<OrdersDTO> findOrdersByUserId(String userId);

	List<OrdersDTO> findOrdersByDate(String date);

	List<OrderDetailDTO> findOrderDetailsByItemCode(String itemCode);

	int getTotalPrice(List<OrderDetailDTO> details);
}
