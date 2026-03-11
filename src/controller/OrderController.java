package controller;

import java.util.ArrayList;
import java.util.List;

import exception.OrderFailedException;
import model.dto.OrderDetailDTO;
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
}
