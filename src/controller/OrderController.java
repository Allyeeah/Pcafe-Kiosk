package controller;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import service.OrderService;
import service.OrderServiceImpl;

import java.util.List;

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
			System.out.println("[모든 주문 조회]");
			System.out.println(orderService.findAllOrders());
		} catch (OrderNotFoundException e) {
			System.out.println("[조회 실패] " + e.getMessage());
		}
	}

	public void listOrdersByUserId() {
		try {
			// TODO - 세션에서 현재 로그인한 사용자 아이디 가져오기
			String userId = "ljg";
			// TODO - 별도의 View로 출력
			System.out.println("[내 주문 조회]");
			System.out.println(orderService.findOrdersByUserId(userId));
		} catch (OrderNotFoundException e) {
			System.out.println("[조회 실패] " + e.getMessage());
		}
	}

	public void listOrderDetailsByItemId(int itemId) {
		try {
			// TODO - 별도의 View로 출력
			System.out.println("[아이템 주문 조회]");
			System.out.println(orderService.findOrderDetailsByItemId(itemId));
		} catch (OrderNotFoundException e) {
			System.out.println("[조회 실패] " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		OrderController orderController = new OrderController();

		OrdersDTO order = new OrdersDTO();
		order.setUserId("ljg");
		List<OrderDetailDTO> details = List.of( //dto 추가에 따라서 수정
				new OrderDetailDTO(0, 0, 1, "101", "신라면", 4500, 2),
				new OrderDetailDTO(0, 0, 3, "203", "짜계치", 3500, 4)
		);
		order.setOrderDetails(details);
		order.updateTotalAmount();
//		orderController.startOrder(order);
//		orderController.listAllOrders();
//		orderController.listOrdersByUserId();
//		orderController.listOrderDetailsByItemId(1);
	}
}
