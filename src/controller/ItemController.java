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
        try {
            // 카테고리 데이터 가져오기
            List<CategoryDTO> categoryList = categoryService.getCategoryList();
            // 출력
            ItemView.printCategoryList(categoryList);

            // 
            System.out.print("\n 카테고리 번호를 입력하세요: ");
            int selectedCategoryId = Integer.parseInt(sc.nextLine());

            // 상품 데이터 가져오기
            List<ItemDTO> itemList = itemService.selectItemsByCategory(selectedCategoryId);
            // View
            ItemView.printItemList(selectedCategoryId, itemList);

        } catch (SQLException e) {
            ItemView.printErrorMessage("[조회실패] 오류입니다.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            ItemView.printErrorMessage("숫자 번호만 입력해주세요.");
        }
    }
}

