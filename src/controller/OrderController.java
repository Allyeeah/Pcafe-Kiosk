package controller;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import service.OrderService;
import service.OrderServiceImpl;
import view.FailView;
import view.OrderView;

import java.util.List;

public class OrderController {
	private static OrderController instance = new OrderController();
	private OrderService orderService = OrderServiceImpl.getInstance();
	private OrderView orderView = new OrderView();

	private OrderController() {}
	public static OrderController getInstance() {
		return instance;
	}

	public void startOrder(OrdersDTO order) {
		try {
			orderService.placeOrder(order);

			orderView.orderSuccessMessage(order);
		} catch (OrderFailedException e) {
			FailView.errorMessage("[주문 실패] " + e.getMessage());
		}
	}

	public void cancelOrder(int orderId) {
		try {
			orderService.cancelOrder(orderId);

			System.out.println(orderId + " 주문이 성공적으로 취소되었습니다.");
		} catch (CancelFailedException e) {
			FailView.errorMessage("[취소 실패] " + e.getMessage());
		}
	}

	public void listAllOrders() {
		try {
			orderView.printAllOrders(orderService.findAllOrders());
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

	public void listOrdersByUserId(String userId) {
		try {
			orderView.printUserOrders(orderService.findOrdersByUserId(userId));
		} catch (OrderNotFoundException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void listOrderDetailsByItemCode(String itemCode) {
		try {
			List<OrderDetailDTO> details = orderService.findOrderDetailsByItemCode(itemCode);

			System.out.println("[아이템 주문 조회]");
			details.forEach(System.out::println);

			orderView.printTotalPrice(orderService.getTotalPrice(details));
		} catch (OrderNotFoundException e) {
			FailView.errorMessage("[조회 실패] " + e.getMessage());
		}
	}

}
