package view;

import controller.*;
import exception.InvalidMenuException;
import mvc.session.SessionSet;

import java.util.Scanner;

public class MenuView {
	private static final Scanner sc = new Scanner(System.in);

	public static void menu() {
		while (true) {
			try {
				MenuView.printMenu();

				int menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
					case 1:
						MemberView.registerView(); // 가입
						break;
					case 2:
						MemberView.loginView();// 로그인
						break;
					case 9:
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
						break;
					default:
						throw new InvalidMenuException("선택하신[" + menu + "]번은 없는 번호입니다. 다시 입력해주세요.");
				}

			} catch (NumberFormatException e) {
				FailView.errorMessage("숫자만 입력 가능합니다.");
			} catch (InvalidMenuException e) {
				// 번호 이탈
				FailView.errorMessage(e.getMessage());
			}
		}
	}

	public static void printMenu() {
		System.out.println("=== Pcafe ===");
		System.out.println("1. 회원가입   |   2. 로그인   |  9. 종료");
	}

	public static void printUserMenu(String userId) {
		while(true) {
			SessionSet ss = SessionSet.getInstance();
			// session 비어있는지 체크 , 비어잇으면 -> return
			if(ss.getSet().isEmpty()) {
				return;
			}
			System.out.println("-----" + "["+userId+"]"+ " 로그인 중 -----");
			System.out.println(" 1.로그아웃 | 2.상품보기 | 3.주문하기 | 4.장바구니담기 | 5.장바구니보기 | 6. 마이페이지");
			int menu =Integer.parseInt( sc.nextLine());
			switch(menu) {
				case 1 :
					logout(userId);
					return;
				case 2 :
					ItemController.itemSelect(userId);//전체 상품조회
					break;
				case 3 :
					OrderView.printInputOrder(userId);
					break;
				case 4 :
					CartView.putCartView(userId);
					break;
				case 5 :
					CartController.viewCart(userId);
					break;
				case 6:
					MemberView.myPageView(userId);
					break;
				default:
					  throw new InvalidMenuException("선택하신[" + menu + "]번은 없는 번호입니다. 다시 입력해주세요.");
				}
		}

	}

	//관리자 메인메뉴 추가 - 오혜진 0313
	public static void printAdminMenu(String adminId) {
	    while (true) {
	        System.out.println("\n----- Pcafe 관리자 메인 메뉴 -----");
	        System.out.println(" 1.로그아웃 | 2.사용자 관리 | 3.카테고리 관리 | 4.상품 관리 | 5.매출 관리 ");
	        System.out.print("메뉴 선택 > ");

	        try {
	            int menu = Integer.parseInt(sc.nextLine());
	            switch (menu) {
	                case 1:
	                	logout(adminId);
	                    return; // 메인 돌아감

	                case 2:
	                    MemberView.printAdminMemberMenu();
	                    break;

	                case 3:
						CategoryView.printCategoryMenu();
	                    break;

	                case 4:
	                	ItemView.adminItemMenu();
	                    break;

	                case 5:
						OrderView.printAdminOrderMenu();
	                    break;

	                default:
		                throw new InvalidMenuException("선택하신[" + menu + "]번은 없는 번호입니다. 다시 입력해주세요.");

	            }
	        } catch (NumberFormatException e) {
	            FailView.errorMessage("숫자만 입력 가능합니다.");

	        } catch (InvalidMenuException e) {
	            FailView.errorMessage(e.getMessage());
	        }
	    }
	}

	/*
	 * 로그아웃
	 */
	private static void logout(String userId) {
		SessionSet ss = SessionSet.getInstance();
		ss.remove(userId);
		System.out.println("로그아웃 되었습니다");
	}
}