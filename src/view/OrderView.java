package view;

import model.dto.OrdersDTO;

import java.util.List;

public class OrderView {
    public void orderSuccessMessage(OrdersDTO order) {
        System.out.println(order.getUserId() + "님의 주문이 완료되었습니다.");
        printOrderedItems(order);
    }

    public void printUserOrders(List<OrdersDTO> orders) {
        System.out.println("[" + orders.getFirst().getUserId() + "] 님의 주문 내역");
        for (OrdersDTO order : orders) {
            System.out.println(order);
            printOrderedItems(order);
        }
    }

    public void printOrderedItems(OrdersDTO order) {
        System.out.println("------주문 상품------");
        order.getOrderDetails().forEach(System.out::println);
        System.out.println("총 금액: " + order.getTotalAmount() + "원");
        System.out.println("--------------------\n");
    }

    public void printAllOrders(List<OrdersDTO> orders) {
        orders.forEach(this::printOrderedItems);
    }

    public void printTotalPrice(int totalPrice) {
        System.out.println("총 금액: " + totalPrice + "원");
    }

}
