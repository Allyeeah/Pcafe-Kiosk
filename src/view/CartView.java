package view;

import controller.CartController;
import model.dto.ItemDTO;

import java.util.Map;
import java.util.Scanner;

public class CartView {
	private static final Scanner sc = new Scanner(System.in);
	/**
	 * 장바구니 보기
	 */
	public static void printViewCart(String userId, Map<ItemDTO, Integer> cart) {
		System.out.println("장바구니내용....");

		for (ItemDTO item : cart.keySet()) {
			String itemCode = item.getItemCode();//상품코드
			String itemName = item.getItemName();//상품이름
			int price = item.getPrice();//상품가격

			int quantity = cart.get(item);//key에 해당하는 value즉 수량
			System.out.println(itemCode + " : " + itemName + " : " + price + " \t " + quantity);
		}

		System.out.println("1.주문하기  |  9.나가기");
		int choice = Integer.parseInt(sc.nextLine());
		switch (choice) {
			case 1:
				CartController.orderFromCart(userId, cart);
				break;

			case 9:
				break;
		}

	}

	public static void putCartView(String id) {
		System.out.println("--장바구니 담기 작업 --");
		System.out.print("상품번호 : ");
		String ItemCode = sc.nextLine();
		System.out.print("수량 : ");
		int qty = Integer.parseInt(sc.nextLine());

		CartController.putCart(id,ItemCode,qty);
	}
}
