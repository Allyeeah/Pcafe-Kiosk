package view;

import controller.AdminController;
import controller.MemberController;
import controller.OrderController;
import exception.InvalidMenuException;
import model.dto.MemberDTO;
import mvc.session.SessionSet;

import java.util.List;
import java.util.Scanner;

public class MemberView {
	private static final Scanner sc = new Scanner(System.in);
	private static final OrderController orderController = OrderController.getInstance();

	public static void printMembers(List<MemberDTO> list) {
		for(MemberDTO member : list) {
			System.out.println(member);
		}
	}

	public static void printMember(MemberDTO member) {
		System.out.println(member);
	}

	public static void registerView() {
		System.out.println("\n--[회원가입 ---");
		System.out.print("아이디: ");
		String id = sc.nextLine().trim();
		if (id.isEmpty()) { //공백예외처리
			throw new InvalidMenuException("아이디는 공백일 수 없습니다.");
		}

		System.out.print("비밀번호(4자리): ");
		String pwd = sc.nextLine().trim();
		if (pwd.length() != 4) { //4자리 출력 추가
			throw new InvalidMenuException("비밀번호는 4자리로 입력하세요.");
		}

		System.out.print("이름: ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) { //공백예외처리
			throw new InvalidMenuException("이름은 공백일 수 없습니다.");
		}

		System.out.print("관리자로 가입하시겠습니까? (Y/N): ");
		String isAdminInput = sc.nextLine().trim().toUpperCase();
		String isAdmin = isAdminInput.equals("Y") ? "Y" : "N";

		MemberDTO memberDTO = new MemberDTO(id, pwd, name, isAdmin, null, null);

		MemberController.register(memberDTO);
	}

	public static void loginView() {
		System.out.print("아이디 : ");
		String userId = sc.nextLine();

		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();

		MemberController.login(userId, userPw);
	}

	public static void myPageView(String userId) {
		SessionSet ss = SessionSet.getInstance();

		System.out.println("마이페이지 메뉴 조회");
		while (true) {
			if(ss.getSet().isEmpty()) {
				return;
			}

			try {
				System.out.println("1. 주문 내역 보기 | 2. 사용자 정보 수정 | 3. 탈퇴 | 0. 이전메뉴");

				int mypagemenu = Integer.parseInt(sc.nextLine());
				switch (mypagemenu) {
					case 1:
						//주문 내역 보기
						orderController.listOrdersByUserId(userId);
						break;
					case 2:
						//사용자 정보 수정
						MemberView.updateMemberInfoView();
						break;
					case 3:
						//탈퇴
						MemberView.withdrawMemberView();
						break;
					case 0:
						System.out.println("이전 메뉴로 돌아갑니다.");
						return;
					default:
						throw new InvalidMenuException("선택하신 [" + mypagemenu + "]번은 없는 번호입니다.");
				}

			} catch (NumberFormatException e) {
				FailView.errorMessage("숫자만 입력 가능합니다.");

			} catch (InvalidMenuException e) {
				FailView.errorMessage(e.getMessage());
			}
		}
	}

	public static void updateMemberInfoView() {
		try { //입력예외처리 추가
			System.out.println("--- [회원 정보 수정] --");

			System.out.print("사용자의 Id를 입력해주세요. > ");
			String userId = sc.nextLine().trim();
			if (userId.isEmpty()) {
				throw new InvalidMenuException("아이디는 공백일 수 없습니다.");
			}
			System.out.print("수정하실 비밀번호(4자리)를 입력해주세요. > ");
			String userPw = sc.nextLine().trim();
			if (userPw.length() != 4) {
				throw new InvalidMenuException("비밀번호는 반드시 4자리여야 합니다.");
			}
			System.out.print("수정하실 이름을 입력해주세요. > ");
			String userName = sc.nextLine().trim();
			if (userName.isEmpty()) {
				throw new InvalidMenuException("이름은 공백일 수 없습니다.");
			}
			//컨트롤러 호출
			MemberController.updateMemberInfo(userId, userPw, userName);
			System.out.println("정보 수정이 완료되었습니다.");

		} catch (InvalidMenuException e) {
			// 공백이나 비밀번호 자릿수 오류 처리
			FailView.errorMessage(e.getMessage());
		} catch (Exception e) {
			// DB 오류 등 기타 예외 처리
			FailView.errorMessage("수정 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	public static void withdrawMemberView()  {
		System.out.print("사용자의 Id를 입력해주세요. > ");
		String userId = sc.nextLine();
		System.out.print("사용자의 비밀번호를 입력해주세요. > ");
		String userPw = sc.nextLine();

		MemberController.withdrawMember(userId, userPw);
	}

	public static void printAdminMemberMenu() {
		while (true) {
			try {
				System.out.println("-- 관리자 메뉴 --");
				System.out.println("1. ID로 검색   |  2.이름으로 검색  | 3.전체 검색  | 0. 이전메뉴");
				System.out.print("메뉴 선택 > ");
				int menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
					case 1:
						selectMemberById(); //ID로 검색
						break;
					case 2:
						selectMemberByName();// 이름으로 검색
						break;

					case 3:
						AdminController.selectAllMember(); //전체 검색
						break;
					case 0:
						System.out.println("관리자 메인 메뉴로 돌아갑니다.");
						return; // 다시 pCafe메인 printMenu()화면으로
					default:
						throw new InvalidMenuException("선택하신 [" + menu + "]번은 없는 번호입니다.");

				}
			} catch (NumberFormatException e) {
				FailView.errorMessage("숫자만 입력 가능합니다.");

			} catch (InvalidMenuException e) {
				FailView.errorMessage(e.getMessage());
			}

		}
	}

	private static void selectMemberById() {
		try
		{System.out.print("사용자 ID를 입력해 주세요. > ");
			String userId = sc.nextLine().trim();
			if (userId.isEmpty()) { //공백예외처리
				throw new InvalidMenuException("아이디는 공백일 수 없습니다.");
			}
			AdminController.selectMemberById(userId);
		}catch (InvalidMenuException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	private static void selectMemberByName() {
		try {
			System.out.print("사용자의 이름을 입력해주세요. > ");
			String userName = sc.nextLine();
			if (userName.isEmpty()) { //공백예외처리
				throw new InvalidMenuException("아이디는 공백일 수 없습니다.");
			}
			AdminController.selectMemberByName(userName);
		}catch (InvalidMenuException e) {
			FailView.errorMessage(e.getMessage());
		}

	}
}
