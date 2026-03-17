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

	public boolean startOrder(OrdersDTO order) {
		try {
			orderService.placeOrder(order);
			OrderView.orderSuccessMessage(order);
			return true;
		} catch (OrderFailedException e) {
			FailView.errorMessage("[주문 실패] " + e.getMessage());
		}

		return false;
	}

	public void cancelOrder(String userId, int orderId) {
		try {
			orderService.cancelOrder(userId, orderId);
			OrderView.orderCancelMessage(orderId);
		} catch (CancelFailedException e) {
			FailView.errorMessage("[취소 실패] " + e.getMessage());
		}
	}

	public void reorder(String userId, int orderId) {
		try {
			OrdersDTO order = orderService.reorder(userId, orderId);
			OrderView.orderSuccessMessage(order);
		} catch (OrderFailedException e) {
			FailView.errorMessage("[재주문 실패] " + e.getMessage());
		}
	}


	public void listAllOrders() {
		try {
			List<OrdersDTO> list = orderService.findAllOrders();
			OrderView.printAllOrders(list);
			OrderView.printTotalPrice(orderService.getTotalOrderPrice(list));
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

	public void listOrdersByUserId(String userId) {
		try {
			List<OrdersDTO> list = orderService.findOrdersByUserId(userId);
			OrderView.printAllOrders(list);
			OrderView.printOrderListMenu(userId);
		} catch (OrderNotFoundException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void listOrdersByDate(String date) {
		try {
			List<OrdersDTO> list = orderService.findOrdersByDate(date);
			OrderView.printAllOrders(list);
			OrderView.printTotalPrice(orderService.getTotalOrderPrice(list));
		} catch (OrderNotFoundException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void listOrderDetailsByItemCode(String itemCode) {
		try {
			List<OrderDetailDTO> details = orderService.findOrderDetailsByItemCode(itemCode);
			int totalPrice = orderService.getTotalItemPrice(details);
			OrderView.printOrderDetails(details, totalPrice);
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

}
