package view;

import controller.OrderController;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import util.StringUtil;

import java.util.List;
import java.util.Scanner;

public class OrderView {
    private static final OrderController orderController = OrderController.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void orderSuccessMessage(OrdersDTO order) {
        System.out.println("\n==============================================");
        System.out.println("           주문이 완료되었습니다.");
        System.out.println("==============================================");
        printOrderedItems(order);
    }

    public static void orderCancelMessage(int orderId) {
        System.out.println("\n==============================================");
        System.out.println(" 주문번호 " + orderId + "의 주문이 취소되었습니다.");
        System.out.println("==============================================");
    }

    public static void printAllOrders(List<OrdersDTO> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("\n조회된 주문 내역이 없습니다.");
            return;
        }

        System.out.println("\n==============================================================");
        System.out.println("                        주문 내역 조회");
        System.out.println("==============================================================");

        for (int i = 0; i < orders.size(); i++) {
            printOrderedItems(orders.get(i));

            if (i < orders.size() - 1) {
                System.out.println("--------------------------------------------------------------");
            }
        }
    }

    public static void printOrderedItems(OrdersDTO order) {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.printf ("│ 주문번호 : %-33d │%n", order.getOrderId());
        System.out.printf ("│ 주문자   : %-33s │%n", order.getUserId());
        System.out.printf ("│ 주문일시 : %-33s │%n", order.getOrderDate() == null ? "-" : order.getOrderDate());
        System.out.printf ("│ 상태     : %-29s │%n", order.getStatus() == null ? "-" : order.getStatus().label());
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.println("│ 상품명            단가        수량      금액 │");
        System.out.println("├──────────────────────────────────────────────┤");

        List<OrderDetailDTO> details = order.getOrderDetails();
        if (details == null || details.isEmpty()) {
            System.out.println("  주문 상세 내역이 없습니다.                      ");
        } else {
            for (OrderDetailDTO detail : details) {
                int linePrice = detail.getUnitPrice() * detail.getQty();
                System.out.printf(
                        "│ %s %8d원 %6d개 %8d원 │%n",
                        StringUtil.padRightByWidth(StringUtil.trimTextByWidth(detail.getItemName(), 13), 13),
                        detail.getUnitPrice(),
                        detail.getQty(),
                        linePrice
                );
            }
        }

        System.out.println("├──────────────────────────────────────────────┤");
        System.out.printf ("│ 총 금액 : %-33s │%n", String.format("%,d원", order.getTotalAmount()));
        System.out.println("└──────────────────────────────────────────────┘");
    }

    public static void printOrderDetails(List<OrderDetailDTO> details, int totalPrice) {
        if (details == null || details.isEmpty()) {
            System.out.println("\n조회된 주문 상세 내역이 없습니다.");
            return;
        }

        System.out.println("\n==============================================================");
        System.out.println("                 " + details.getFirst().getItemName() + " 주문 조회");
        System.out.println("==============================================================");
        System.out.println("주문번호   상품코드     상품명         단가     수량      금액");
        System.out.println("--------------------------------------------------------------");

        for (OrderDetailDTO detail : details) {
            int linePrice = detail.getUnitPrice() * detail.getQty();
            System.out.printf(
                    "%-10d %-12s %s %-8d %-6d %7d%n",
                    detail.getOrderId(),
                    detail.getItemCode(),
                    StringUtil.padRightByWidth(StringUtil.trimTextByWidth(detail.getItemName(), 14), 14),
                    detail.getUnitPrice(),
                    detail.getQty(),
                    linePrice
            );
        }

        System.out.println("--------------------------------------------------------------");
        printTotalPrice(totalPrice);
    }

    public static void printTotalPrice(int totalPrice) {
        System.out.println("총 금액: " + String.format("%,d원", totalPrice));
    }

    public static void printOrderDateMenu() {
        System.out.println("------일일 매출 조회------");
        System.out.print("조회할 날짜를 입력하세요 (yyyy-MM-dd): ");
        String input = sc.nextLine();

        orderController.listOrdersByDate(input);
    }

    public static void printOrderItemMenu() {
        System.out.println("------메뉴 리스트------");
        String input = sc.nextLine();
        orderController.listOrderDetailsByItemCode(input);
    }
}
