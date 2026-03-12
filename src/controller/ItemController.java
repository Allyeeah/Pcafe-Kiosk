package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.dto.CategoryDTO;
import model.dto.ItemDTO;
import service.CategoryService;
import service.CategoryServiceImpl;
import service.ItemService;
import service.ItemServiceImpl;
import view.ItemView; 

public class ItemController {

    private static CategoryService categoryService = new CategoryServiceImpl();
    private static ItemService itemService = new ItemServiceImpl();
    private static Scanner sc = new Scanner(System.in);

    public static void itemSelect() {
    	while (true) {
            try {
                // 1. 카테고리 데이터 가져오기
                List<CategoryDTO> categoryList = categoryService.getCategoryList();
                // 2. ItemView를 통해 카테고리 목록 출력
                ItemView.printCategoryList(categoryList);

                // 3. 사용자 입력 받기
                System.out.println("\n ───────────────────────────────────");
                System.out.println("  [0] 입력시 메인 메뉴로 돌아갑니다.          ");
                System.out.println(" ───────────────────────────────────");
                System.out.print(" ❯ Pcafe 카테고리 번호 입력 : ");

                // 사용자의 입력값이 출력문 바로 옆이 아닌 한 칸 띄워진 곳에 찍히도록 구성
                int selectedCategoryId = Integer.parseInt(sc.nextLine());

                if (selectedCategoryId == 0) {
                    System.out.println("\n ↪ 메인 메뉴로 이동합니다...");
                    break; 
                }

                // 4. 상품 데이터 가져오기
                List<ItemDTO> itemList = itemService.selectItemsByCategory(selectedCategoryId);
                
                // 5. ItemView를 통해 상품 목록 출력
                ItemView.printItemList(selectedCategoryId, itemList);

                // 6. [핵심] 조회가 끝난 후 다시 물어보기
                System.out.println("\n ──────────────────────────────────────────────");
                System.out.println("  [1] 다른 카테고리 더보기   |   [2] 메인 메뉴로 이동");
                System.out.print(" ──────────────────────────────────────────────\n");
                System.out.print(" ❯ 선택 : ");
                int subMenu = Integer.parseInt(sc.nextLine());

                if (subMenu == 2) {
                    System.out.println("메인 메뉴로 이동합니다.");
                    break; // 루프 종료
                }

        } catch (SQLException e) {
            ItemView.printErrorMessage("[조회실패] 오류입니다.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            ItemView.printErrorMessage("숫자 번호만 입력해주세요.");
        }
    }
}

}