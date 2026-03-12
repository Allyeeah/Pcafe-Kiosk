package view;

import java.util.Scanner;

import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;

public class MenuView {
	private static Scanner sc = new Scanner(System.in);
	private static MemberDAO memberDAO = MemberDAOImpl.getInstance();
	
	public static void menu() {
		while(true) {
			//SessionSet ss = SessionSet.getInstance();
//			System.out.println("ss.getSet() = "+ss.getSet());
			
			MenuView.printMenu();
			
			int menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1 :
				MenuView.register(); // 가입
				break;
			case 2 :
				//MenuView.login();// 로그인
				break;

			case 9 : 
				System.exit(0);
			}
		}

	}
	
	
	private static void register() {
		System.out.println("\n--- [회원가입] ---");
		System.out.print("아이디: ");
		String id = sc.nextLine();

		System.out.print("비밀번호(4자리): ");
		String pwd = sc.nextLine();

		System.out.print("이름: ");
		String name = sc.nextLine();

		System.out.print("관리자로 가입하시겠습니까? (Y/N): ");
		String isAdminInput = sc.nextLine().trim().toUpperCase();
		String isAdmin = isAdminInput.equals("Y") ? "Y" : "N";

		//test
		//
		MemberDTO memberDTO = new MemberDTO(id, pwd, name, isAdmin, null);

		// DAO insert 메서드가 int 반환이라 가정
		int result = memberDAO.insert(memberDTO);

		if (result > 0) {
			System.out.println("회원가입이 완료되었습니다.\n");
		} else {
			System.out.println("회원가입 실패: 아이디 중복 또는 오류.\n");
		}
	
	}


	public static void printMenu() {
		System.out.println("=== Pcafe ===");
		System.out.println("1. 회원가입   |   2. 로그인   |  9. 종료");
		
	}
	
	
	public static void printUserMenu(String userId) {
		while(true) {
//			SessionSet ss = SessionSet.getInstance();
//			System.out.println(ss.getSet()); //Set객체
			
			System.out.println("-----" +userId+ " 로그인 중 -----");
			System.out.println(" 1.로그아웃 |  2.상품보기  |  3.주문하기  | 4. 주문내역보기  |  5.장바구니담기  |  6.장바구니보기 ");
			int menu =Integer.parseInt( sc.nextLine());
			switch(menu) {
				case 1 :
//					logout(userId);// 
					return; //함수를 빠져나가라.
					//break;
					
				case 2 :
//					ItemController.ItemSelect();//전체 상품조회
					break;
				case 3 :
					printInputOrder(userId);
					break;
				case 4 :
//					OrderController.selectOrdersByUserId(userId);
					break;
				case 5 :
					MenuView.putCart(userId);// 
					break;	
		
				case 6 : 
					viewCart(userId);
					break;
				}
		}
		
	}
	
	public static void printSubMenu() {
		System.out.println("1. 수정   |  2.탈퇴   | 9. 나가기");
	}
	
	public static void printAdminMenu() {
		System.out.println("-- 관리자 메뉴 --");
		System.out.println("1. ID로 검색   |  2.이름으로 검색  | 3.전체 검색  |  9. 나가기");
		
	}
	
	/**
	 * 로그인 메뉴
	 * */
	public static void login() {
		 System.out.print("아이디 : ");
		 String userId = sc.nextLine();
		 
		 System.out.print("비번 : ");
		 String userPw = sc.nextLine();
		 
		 //MemberController.login(userId, userPw); 
	}
	
	/**
	 * 로그아웃
	 * */
	/*public static void logout(String userId) {
		Session session = new Session(userId);
		SessionSet ss = SessionSet.getInstance();
		ss.remove(session);	
	}*/
	
	public static void loginMenucategory() {
		System.out.println("1. 스낵   |  2.라면   | 3. 음료");
		int categorychoice =Integer.parseInt( sc.nextLine());
		switch(categorychoice) {
			case 1 :
				//스낵디비
				
			case 2 :
				//라면디비
			case 3 :
				//음료디비

			}
	}
	
	
	
	/**
	 * 주문하기
	 * */
    public static void printInputOrder(String userId) {
    	
    	
    	System.out.print("주문상품번호 : ");
    	int itemId = Integer.parseInt(sc.nextLine());
		 
		 System.out.print("주문수량 : ");
		int qty = Integer.parseInt(sc.nextLine());
		 
		 
			 
		OrdersDTO orders = new OrdersDTO(0, userId, null, null, 0);
		OrderDetailDTO orderdetail = new OrderDetailDTO(0, 0, itemId, null, 0, qty);
		 //orders.getOrderLineList().add(orderdetail);
		 
		 //OrderController.insertOrders(orders);	 
    }
    
    /**
     * 장바구니 담기
     * */
    public static void putCart(String id) {
		System.out.println("--장바구니 담기 작업 --");
		System.out.print("상품번호 : ");
		String goodsId = sc.nextLine();
		System.out.print("수량 : ");
		int qty = Integer.parseInt(sc.nextLine());
		
		//CartController.putCart(id,goodsId,qty);
	
		
	}
	
    /**
     * 장바구니 보기
     * */
	public static void viewCart(String id) {
		//CartController.viewCart(id);
		
		
		
	}
}



