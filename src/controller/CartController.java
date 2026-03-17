package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dto.ItemDTO;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import mvc.session.Session;
import mvc.session.SessionSet;
import service.ItemService;
import service.ItemServiceImpl;
import service.OrderService;
import service.OrderServiceImpl;
import view.CartView;
import view.FailView;
import view.SuccessView;

public class CartController {
	private static final ItemService itemService = ItemServiceImpl.getInstance();
	private static final OrderService orderService = OrderServiceImpl.getInstance();

	public static void putCart(String id, String itemCode, int qty) {
		try {
			//상품코드에 해당 상품찾기
			ItemDTO item = itemService.selectItemByCode(itemCode);

			//id에 해당하는 세션찾기
			SessionSet ss = SessionSet.getInstance();
			Session session = ss.get(id);

			//세션에서 장바구니 찾기
			Map<ItemDTO, Integer> cart = (Map<ItemDTO,Integer>)session.getAttribute("cart"); //상품 , 수량 저장

			//장바구니가 없으면 장바구니 생성
			if(cart == null) {
				cart = new HashMap<>();
				session.setAttribute("cart", cart);
			}



			//장바구니에서 상품찾기
			Integer oldQuantity = cart.get(item);//item는 장바구니 Map의 key
			if(oldQuantity != null) { //장바구니에 이미 상품이 있다면
				qty += oldQuantity; //수량을 누적 => quantity = quantity + oldQuantity
			}

			cart.put(item, qty); //장바구니에 상품 넣기

			SuccessView.printMessage("장바구니에 담았습니다");

		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

   /**
    * 장바구니 보기
    * */
	public static void viewCart(String id) {
		SessionSet ss = SessionSet.getInstance();
		Session session = ss.get(id);

		Map<ItemDTO, Integer> cart = (Map<ItemDTO, Integer>) session.getAttribute("cart");
		if (cart == null) { // 장바구니가 없는 고객
			FailView.errorMessage("장바구니가 비었습니다");
		} else {
			CartView.printViewCart(id, cart);
		}
	}

	public static void orderFromCart(String userId, Map<ItemDTO, Integer> cart) {
		OrdersDTO orders = new OrdersDTO(userId);
		List<OrderDetailDTO> orderDetails = orders.getOrderDetails();

		for (ItemDTO itemkey : cart.keySet()) {
			int qty = cart.get(itemkey);//map에서 key=Goods에 해당하는 value=수량 조회
			OrderDetailDTO orderLine = new OrderDetailDTO(
					0, 0, itemkey.getItemId(), itemkey.getItemCode(), itemkey.getItemName(), itemkey.getPrice(), qty);
			orderDetails.add(orderLine);
		}

		boolean orderSuccess = OrderController.getInstance().startOrder(orders);

		if (orderSuccess) {
			clearCart(userId);
		}
	}

	private static void clearCart(String userId) {
		SessionSet ss = SessionSet.getInstance();
		Session session = ss.get(userId);
		session.removeAttribute("cart");
	}
}












