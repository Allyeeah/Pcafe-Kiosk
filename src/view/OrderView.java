package view;

import java.util.List;
import java.util.Scanner;

import controller.ItemController;
import controller.OrderController;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import util.StringUtil;

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

        System.out.println("\n==================================================================================================");
        System.out.println("                                        주문 내역 조회");
        System.out.println("==================================================================================================");
        System.out.println("주문번호   주문자ID       주문일시              상태           총금액       주문상품");
        System.out.println("--------------------------------------------------------------------------------------------------");

        int totalPrice = 0;
        for (OrdersDTO order : orders) {
            totalPrice += order.getTotalAmount();
            StringBuilder items = new StringBuilder();
            List<OrderDetailDTO> details = order.getOrderDetails();
            if (details != null && !details.isEmpty()) {
                for (int i = 0; i < details.size(); i++) {
                    if (i > 0) items.append(", ");
                    items.append(details.get(i).getItemName())
                         .append("(").append(details.get(i).getQty()).append(")");
                }
            }

            System.out.printf(
                    "%-10d %-14s %-21s %-10s %,9d원  %s%n",
                    order.getOrderId(),
                    order.getUserId(),
                    order.getOrderDate() == null ? "-" : order.getOrderDate(),
                    order.getStatus() == null ? "-" : order.getStatus().label(),
                    order.getTotalAmount(),
                    items.toString()
            );
        }
        System.out.println("==================================================================================================");
        printTotalPrice(totalPrice);
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
            System.out.println("│ 주문 상세 내역이 없습니다.                   │");
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
        ItemController.selectAllItems();
        System.out.print("조회할 상품 코드> ");
        String input = sc.nextLine();
        orderController.listOrderDetailsByItemCode(input);
    }

    public static void printOrderListMenu(String userId) {
        while (true) {
            System.out.println(" 1.주문취소 |  2.재주문  |  0.이전메뉴");
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu) {
                case 1:
                    printOrderCancelMenu(userId);
                    return;
                case 2:
                    printReorderMenu(userId);
                    return;
                case 0:
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 다시 선택해주세요.");
            }
        }
    }

    public static void printOrderCancelMenu(String userId) {
        System.out.println("취소할 주문의 주문번호를 입력해주세요.");
        int orderId = Integer.parseInt(sc.nextLine());
        orderController.cancelOrder(userId, orderId);
    }

    private static void printReorderMenu(String userId) {
        System.out.println("재주문할 주문의 주문번호를 입력해주세요.");
        int orderId = Integer.parseInt(sc.nextLine());
        orderController.reorder(userId, orderId);
    }

}
