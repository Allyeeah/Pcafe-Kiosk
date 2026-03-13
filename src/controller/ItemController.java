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
import view.MenuView; 

public class ItemController {

    private static CategoryService categoryService = new CategoryServiceImpl();
    private static ItemService itemService = new ItemServiceImpl();
    private static Scanner sc = new Scanner(System.in);

    public static void itemSelect(String userId) {
    	while (true) {
            try {
                // 카테고리 데이터 가져오기
                List<CategoryDTO> categoryList = categoryService.getCategoryList();
                // ItemView를 통해 카테고리 목록 출력
                ItemView.printCategoryList(categoryList);

                // 사용자 입력 받기
                System.out.println(" ───────────────────────────────────");
                System.out.println("  [0] 입력시 메인 메뉴로 돌아갑니다.          ");
                System.out.println(" ───────────────────────────────────");
                System.out.print(" ❯ Pcafe 카테고리 번호 입력 : ");

                int selectedCategoryId = Integer.parseInt(sc.nextLine());

                if (selectedCategoryId == 0) {
                    System.out.println("\n ↪ 메인 메뉴로 이동합니다...");
                    break; 
                }

                // 4. 상품 데이터 가져오기
                List<ItemDTO> itemList = itemService.selectItemsByCategory(selectedCategoryId);
                
                // 5. ItemView를 통해 상품 목록 출력
                ItemView.printItemList(selectedCategoryId, itemList);

                
                System.out.println("────────────────────────────────────────────────────────────────");
                System.out.println("  [1] 다른 카테고리 보기 | [2] 주문하기 | [3] 장바구니 담기 | [4] 메인메뉴로 이동");
                System.out.print(" ────────────────────────────────────────────────────────────────\n");
                System.out.print(" ❯ 선택 : ");
                int subMenu = Integer.parseInt(sc.nextLine());

                if (subMenu == 1) {
                   
                } else if (subMenu == 2) {
                    System.out.println("주문 화면으로 이동합니다.");
                    MenuView.printInputOrder(userId); // 주문하기
                } else if (subMenu == 3) {
                    System.out.println("장바구니에 상품을 담습니다.");
                    MenuView.putCart(userId); 
                } else if (subMenu == 4) {
                    System.out.println("메인 메뉴로 이동합니다.");
                    break; 
                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택해 주세요.");
                }
                

        } catch (SQLException e) {
            ItemView.printErrorMessage("[조회실패] 오류입니다.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            ItemView.printErrorMessage("숫자 번호만 입력해주세요.");
        }
    }
    }
    
    public static void selectAllItems() {
        try {
         
            List<ItemDTO> itemList = itemService.itemSelect();
            ItemView.printAllItems(itemList);
            
        } catch (SQLException e) {
            System.out.println("\n[오류] 상품 목록을 불러오는 중 문제가 발생했습니다.");
            e.printStackTrace();
        }
    }
    

//관리자 상품 추가 메서드 
    public static void insertItem() {
        try {
        
            List<ItemDTO> itemList = itemService.itemSelect(); 
            ItemDTO newItem = ItemView.insertItemView(itemList);
 
             int result = itemService.insertItem(newItem); 
         
            if (result > 0) {
                System.out.println("\n[등록완료] '" + newItem.getItemName() + "' 상품이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("\n[시스템] 상품 등록에 실패했습니다.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n[오류] 카테고리 번호나 가격은 숫자만 입력해야 합니다.");
        } catch (Exception e) { 
            System.out.println("\n[오류] 상품 등록 중 문제가 발생했습니다.");
            e.printStackTrace();
        }
    }
}

