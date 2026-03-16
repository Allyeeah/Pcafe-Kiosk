package view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.OrderController;
import model.dto.ItemDTO;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import mvc.session.Session;
import mvc.session.SessionSet;

public class EndView {
	  /**
	   * 상품 전체 출력
	   * */
		public static void printItemList(List<ItemDTO> list) {
			System.out.println("-----상품 "+ list.size() +"개 -------------");
			for(ItemDTO item : list) {
				System.out.println(item);
			}

			System.out.println();
		}



		public static void printMessage(String message) {
			System.out.println(message);
		}

		/**
		 * 장바구니 보기
		 * */
		public static void printViewCart(String userId , Map<ItemDTO,Integer> cart) {
			System.out.println("장바구니내용....");

			for(ItemDTO item: cart.keySet()) {
				String itemCode = item.getItemCode();//상품코드
				String itemName = item.getItemName();//상품이름
				int price = item.getPrice();//상품가격

				int quantity = cart.get(item);//key에 해당하는 value즉 수량
				System.out.println(itemCode+" : "+itemName+" : "+price+" \t "+quantity);
			}


			Scanner sc = new Scanner(System.in);
			System.out.println("1.주문하기  |  9.나가기");
			int choice = Integer.parseInt(sc.nextLine());
			switch(choice) {
			case 1:

				 OrdersDTO orders = new OrdersDTO(userId);

				 List<OrderDetailDTO> orderLineList = orders.getOrderDetails();

				 for(ItemDTO itemkey : cart.keySet()) {
					 int qty = cart.get(itemkey);//map에서 key=Goods에 해당하는 value=수량 조회
					 OrderDetailDTO orderLine = new OrderDetailDTO(
							 0, 0, itemkey.getItemId(), itemkey.getItemCode() , itemkey.getItemName(), itemkey.getPrice(), qty);
					 orderLineList.add(orderLine);
				 }


				 System.out.println("orderLineList 개수 : " + orderLineList.size());

				 OrderController.getInstance().startOrder(orders);// 주문 + 주문상세

				 //장바구니비우기
				 SessionSet ss = SessionSet.getInstance();
				 Session session = ss.get(userId);
				 session.removeAttribute("cart");
				break;

			case 9:
				break;
			}

			//System.out.println(id);

		}

		/**
		 * 주문 상세보기
		 * */
		public static void printOrderByUserId(List<OrdersDTO> orderList) {
		   for(OrdersDTO order : orderList) {
			   System.out.println(order.getOrderId()+ " | " + order.getOrderDate() +" | " + order.getTotalAmount());

			   for(OrderDetailDTO orderLine : order.getOrderDetails()) {
				   System.out.println("  ▶ "+orderLine);
			   }
			   System.out.println();
		   }

		}
}
