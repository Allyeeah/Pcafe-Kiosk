package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.AdminController;
import controller.ItemController;
import controller.OrderController;
import exception.NotFoundException;
import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import mvc.session.Session;
import mvc.session.SessionSet;
import service.MemberService;

public class MenuView {
	private static Scanner sc = new Scanner(System.in);
	private static MemberDAO memberDAO = MemberDAOImpl.getInstance();
	static MemberService memberService = new MemberService(); //혜진추가
	
	public static void menu() {
		while(true) {
			SessionSet ss = SessionSet.getInstance();
		//System.out.println("ss.getSet() = "+ss.getSet());
			
			MenuView.printMenu();
			
			int menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1 :
				MenuView.register(); // 가입
				break;
			case 2 :
			    MenuView.login();// 로그인
				break;

			case 9 : 
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);

			}
		}

	}
	
	public static void printMenu() {
		System.out.println("=== Pcafe ===");
		System.out.println("1. 회원가입   |   2. 로그인   |  9. 종료");
		
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

	
	
	
	public static void printUserMenu(String userId) {
		while(true) {
			SessionSet ss = SessionSet.getInstance();
			System.out.println(ss.getSet()); //Set객체
			
			System.out.println("-----" + "["+userId+"]"+ " 로그인 중 -----");
			System.out.println(" 1.로그아웃 |  2.상품보기  |  3.주문하기  | 4. 주문내역보기  |  5.장바구니담기  |  6.장바구니보기 ");
			int menu =Integer.parseInt( sc.nextLine());
			switch(menu) {
				case 1 :
					logout(userId);
					return; //
					//break;
					
				case 2 :
					ItemController.itemSelect(userId);//전체 상품조회
					break;
				case 3 :
					printInputOrder(userId);
					break;
				case 4 :
					OrderController.getInstance().listOrdersByUserId(userId);
					break;
				case 5 :
					MenuView.putCart(userId);
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
	
	//관리자 메인메뉴 추가 - 오혜진 0313
	public static void printAdminMainMenu(String adminId) {
	    while (true) {
	    	SessionSet ss = SessionSet.getInstance();
	        System.out.println("\n----- Pcafe 관리자 메인 메뉴 -----");
	        System.out.println(" 1.로그아웃 | 2.사용자 관리 | 3.카테고리 관리 | 4.상품 관리 | 5.매출 관리 ");
	        System.out.print("메뉴 선택: ");
	        
	        try {
	            int menu = Integer.parseInt(sc.nextLine());
	            switch (menu) {
	                case 1:
	                    System.out.println("로그아웃 되었습니다.");
	                    return; // 메인 돌아감

	                case 2:
	                    printAdminMenu(adminId); 
	                    break;

	                case 3:
	                    //카테고리관리 
	                    break;

	                case 4:
	                    //상품
	                    break;

	                case 5:
	                 //
	                    break;

	                default:
	                    System.out.println("잘못된 번호입니다. 다시 선택해주세요.");
	                    break;
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("숫자만 입력 가능합니다.");
	        }
	    }
	}
	
	


	public static void printAdminMenu(String userId) {
		while(true) {
			SessionSet ss = SessionSet.getInstance();
			//	System.out.println(ss.getSet()); //Set객체
			
			System.out.println("-- 관리자 메뉴 --");
			System.out.println("1. ID로 검색   |  2.이름으로 검색  | 3.전체 검색  | 9. 이전메뉴");
			
			int menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1 :
				MenuView.selectMemberById(); //ID로 검색
				break;
			case 2 :
				MenuView.selectMemberByName();// 이름으로 검색
				break;
				
			case 3 :
				AdminController.selectAllMember(); //전체 검색
				break;
			case 9 :
				System.out.println("관리자 메인 메뉴로 돌아갑니다.");
				return; // 다시 pCafe메인 printMenu()화면으로
				
			}
			
		}
	}
	public static void selectMemberById() {
		System.out.println("사용자 ID를 입력해 주세요. > ");
		String userId = sc.nextLine();

		AdminController.selectMemberById(userId);

	}

	public static void selectMemberByName() {
		System.out.println("사용자의 이름을 입력해주세요. > ");
		String userName = sc.nextLine();

		AdminController.selectMemberByName(userName);
	}

	/**
	 * 로그인 메뉴
	 * */
	//관리자 메뉴추가에 따라서 관리자, 사용자에 따라 보여지는 method 분리 -0313 오혜진
	public static void login() {
		 System.out.print("아이디 : ");
		 String userId = sc.nextLine();
		 
		 System.out.print("비밀번호 : ");
		 String userPw = sc.nextLine();
		 try {
		        MemberDTO loginMember = memberService.login(userId, userPw);

		        if ("Y".equalsIgnoreCase(loginMember.getIsAdmin())) {
		            System.out.println("\n[관리자] 관리자 계정으로 로그인하셨습니다");
		            printAdminMainMenu(loginMember.getUserId());
		        } else {
		            System.out.println("\n[사용자] " + loginMember.getUserName() + "님 로그인하셨습니다");
		            printUserMenu(loginMember.getUserId());
		        }

		    } catch (NotFoundException e) {
		        System.out.println("\n로그인 실패: " + e.getMessage());
		        
		    } catch (SQLException e) {
		        System.out.println("\n 오류입니다.");
		    }


	      

	}
	
	/*
	 * 로그아웃
	 */
	public static void logout(String userId) {
		Session session = new Session(userId);
		SessionSet ss = SessionSet.getInstance();
		ss.remove(session);	
		System.out.println("로그아웃 되었습니다");
	}
		
	
	/**
	 * 주문하기
	 * */
    public static void printInputOrder(String userId) {
    	System.out.print("주문상품번호 : ");
    	String itemCode = sc.nextLine().toUpperCase();
		 
		System.out.print("주문수량 : ");
		int qty = Integer.parseInt(sc.nextLine());
			 
		OrdersDTO orders = new OrdersDTO(0, userId, null, null, 0);
		OrderDetailDTO orderDetail = new OrderDetailDTO(0, 0, 0, itemCode, null, 0, qty);
		orders.getOrderDetails().add(orderDetail);

		OrderController.getInstance().startOrder(orders);
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