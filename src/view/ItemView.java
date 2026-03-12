package view;

import java.util.List;

import model.dto.CategoryDTO;
import model.dto.ItemDTO;

public class ItemView {
    
    // 카테고리 목록 출력
    public static void printCategoryList(List<CategoryDTO> categoryList) {
        System.out.println("\n=== Pcafe 카테고리 목록 ===");
        if (categoryList == null || categoryList.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
        } else {
            for (CategoryDTO category : categoryList) {
                System.out.println(category.getCategoryId() + ". " + category.getCategoryName());
            }
        }
        System.out.println("=======================");
    }

    // 상품 목록 출력
    public static void printItemList(int categoryId, List<ItemDTO> itemList) {
        System.out.println("\n=== [" + categoryId + "번 카테고리] 상품 목록 ===");
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("해당 카테고리에는 상품이 없습니다.");
        } else {
            for (ItemDTO item : itemList) {
                System.out.println(item.toString());
            }
        }
        System.out.println("=============================================");
    }

  
    public static void printErrorMessage(String message) {
        System.out.println("[오류] " + message);
    }
}