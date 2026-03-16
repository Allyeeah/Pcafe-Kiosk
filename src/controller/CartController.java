package controller;

import java.util.HashMap;
import java.util.Map;

import model.dto.ItemDTO;
import mvc.session.Session;
import mvc.session.SessionSet;
import service.ItemService;
import service.ItemServiceImpl;
import view.EndView;
import view.FailView;

public class CartController {
	private static ItemService itemService = new ItemServiceImpl();

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

			EndView.printMessage("장바구니에 담았습니다");

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

		Map<ItemDTO,Integer> cart = (Map<ItemDTO, Integer>) session.getAttribute("cart");
		if(cart == null ) { // 장바구니가 없는 고객
			FailView.errorMessage("장바구니가 비었습니다");
		}else {
			EndView.printViewCart(id , cart);
		}
	}
}












