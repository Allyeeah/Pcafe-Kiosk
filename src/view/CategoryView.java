package view;

import controller.CategoryController;
import exception.InvalidMenuException;
import model.dto.CategoryDTO;

import java.util.List;
import java.util.Scanner;

public class CategoryView {
	private static final Scanner sc = new Scanner(System.in);
	private static final CategoryController categoryController = CategoryController.getInstance();

	public static void printAllCategories(List<CategoryDTO> list) {
		for (CategoryDTO item : list) {
			System.out.println(item);
		}
	}

	public static void printCategoryMenu() {
		while(true) {
			try {
				System.out.println("-- 카테고리 메뉴 --");
				System.out.println("1. 카테고리 조회 | 2. 카테고리 등록 | 3. 카테고리 수정 | 4. 카테고리 삭제 | 0 이전 메뉴");
				System.out.print("메뉴 선택 > ");
				int menu = Integer.parseInt(sc.nextLine());

				switch (menu) {
					case 1:
						//List<ItemDTO> list = ItemServiceImpl.getInstance().itemSelect(); //카테고리 조회
						// 2. 가져온 리스트를 ItemView의 메서드에 인자로 전달합니다.
						categoryController.selectAll();
						break;
					case 2:
						insertCategory(); //카테고리 등록
						break;
					case 3:
						updateCategory();// 카테고리 수정
						break;

					case 4:
						deleteCategory(); //카테고리 삭제
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

	private static void deleteCategory() {
		System.out.print("삭제할 카테고리 번호를 입력해주세요. > ");
		int categoryId = Integer.parseInt(sc.nextLine());
		categoryController.delete(categoryId);
	}

	private static void updateCategory(){
		System.out.print("카테고리 번호를 입력해주세요. > ");
		int categoryId = Integer.parseInt(sc.nextLine());

		System.out.print("수정하실 카테고리명을 입력해주세요. >");
		String category = sc.nextLine();

		categoryController.update(category, categoryId);

	}

	private static void insertCategory() {
		System.out.print("추가하실 카테고리명을 입력해주세요. > ");
		String category = sc.nextLine();
		categoryController.insert(category);
	}
}
