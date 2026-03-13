package view;

import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;

import java.util.List;

public class OrderView {
    public static void orderSuccessMessage(OrdersDTO order) {
        System.out.println(order.getUserId() + "님의 주문이 완료되었습니다.");
        printOrderedItems(order);
    }

    public static void orderCancelMessage(int orderId) {
        System.out.println("주문번호 " + orderId + "의 주문이 취소되었습니다.");
    }

    public static void printUserOrders(List<OrdersDTO> orders) {
        System.out.println("[" + orders.getFirst().getUserId() + "] 님의 주문 내역");
        for (OrdersDTO order : orders) {
            System.out.println(order);
            printOrderedItems(order);
        }
    }

    public static void printAllOrders(List<OrdersDTO> orders) {
        System.out.println("------전체 주문 조회------");
        orders.forEach(OrderView::printOrderedItems);
    }

    public static void printOrderedItems(OrdersDTO order) {
        System.out.println("------주문 상품------");
        order.getOrderDetails().forEach(System.out::println);
        printTotalPrice(order.getTotalAmount());
        System.out.println("--------------------\n");
    }

    public static void printOrderDetails(List<OrderDetailDTO> details, int totalPrice) {
        System.out.println("------" + details.getFirst().getItemName() + " 주문 조회------");
        details.forEach(System.out::println);
        printTotalPrice(totalPrice);
    }

    public static void printTotalPrice(int totalPrice) {
        System.out.println("총 금액: " + totalPrice + "원");
    }

}
