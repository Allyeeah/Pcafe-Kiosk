package controller;

import java.util.List;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import service.OrderService;
import service.OrderServiceImpl;
import view.FailView;
import view.OrderView;

public class OrderController {
	private static final OrderController instance = new OrderController();
	private final OrderService orderService = OrderServiceImpl.getInstance();

	private OrderController() {}
	public static OrderController getInstance() {
		return instance;
	}

	public void startOrder(OrdersDTO order) {
		try {
			orderService.placeOrder(order);
			OrderView.orderSuccessMessage(order);
		} catch (OrderFailedException e) {
			FailView.errorMessage("[주문 실패] " + e.getMessage());
		}
	}

	public void cancelOrder(String userId, int orderId) {
		try {
			orderService.cancelOrder(userId, orderId);
			OrderView.orderCancelMessage(orderId);
		} catch (CancelFailedException e) {
			FailView.errorMessage("[취소 실패] " + e.getMessage());
		}
	}

	public void listAllOrders() {
		try {
			OrderView.printAllOrders(orderService.findAllOrders());
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

	public void listOrdersByUserId(String userId) {
		try {
			OrderView.printAllOrders(orderService.findOrdersByUserId(userId));
			OrderView.printOrderListMenu(userId);
		} catch (OrderNotFoundException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void listOrdersByDate(String date) {
		try {
			OrderView.printAllOrders(orderService.findOrdersByDate(date));
		} catch (OrderNotFoundException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void listOrderDetailsByItemCode(String itemCode) {
		try {
			List<OrderDetailDTO> details = orderService.findOrderDetailsByItemCode(itemCode);
			int totalPrice = orderService.getTotalPrice(details);
			OrderView.printOrderDetails(details, totalPrice);
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

}
