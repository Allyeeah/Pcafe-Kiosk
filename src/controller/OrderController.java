package controller;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dto.OrdersDTO;
import service.OrderService;
import service.OrderServiceImpl;

public class OrderController {
	private OrderService orderService = OrderServiceImpl.getInstance();

	public void startOrder(OrdersDTO order) {
		try {
			orderService.placeOrder(order);

			// TODO - 별도의 View로 출력
			System.out.println("주문 성공");
		} catch (OrderFailedException e) {
			System.out.println("[주문 실패] " + e.getMessage());
		}
	}
	
	public void cancelOrder(int orderId) {
		try {
			orderService.cancelOrder(orderId);
			
			// TODO - 별도의 View로 출력
			System.out.println("주문 취소 성공");
		} catch (CancelFailedException e) {
			System.out.println("[주문 실패] " + e.getMessage());
		}
	}
	
	public void listAllOrders() {
		try {
			// TODO - 별도의 View로 출력
			System.out.println(orderService.findAllOrders());
		} catch (OrderNotFoundException e) {
			System.out.println("[조회 실패] " + e.getMessage());
		}
	}
	
	public void listOrdersByUserId() {
		try {
			// TODO - 별도의 View로 출력
			System.out.println();
		} catch (OrderNotFoundException e) {
			System.out.println("[조회 실패] " + e.getMessage());
		}
	}
}
