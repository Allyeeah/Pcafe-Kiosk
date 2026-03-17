package view;

import java.util.List;
import java.util.Scanner;

import controller.ItemController;
import exception.InvalidMenuException;
import model.dto.CategoryDTO;
import model.dto.ItemDTO;

public class ItemView {
	private static final Scanner sc = new Scanner(System.in);

    // 카테고리 목록 출력
    public static void printCategoryList(List<CategoryDTO> categoryList) {
        System.out.println("\n========= Pcafe 카테고리 목록 =========");
        if (categoryList == null || categoryList.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
        } else {
            for (CategoryDTO category : categoryList) {
                System.out.println(category.getCategoryId() + ". " + category.getCategoryName());
            }
        }
        System.out.println("===================================");
    }

    // 상품 목록 출력
    public static void printItemList(int categoryId, List<ItemDTO> itemList) {
        System.out.println("\n=========== [" + categoryId + "번 카테고리] 상품 목록 =============");
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("해당 카테고리에는 상품이 없습니다.");
        } else {
            for (ItemDTO item : itemList) {
                System.out.println(item.toString());
            }
        }
        System.out.println("=============================================");
    }


    // 관리자 전체상품 조회 추가
	public static void printAllItems(List<ItemDTO> itemList) {
	    System.out.println("\n================ [ Pcafe 전체 상품 목록] ===================");

	    if (itemList == null || itemList.isEmpty()) {
	        System.out.println("  등록된 상품이 없습니다.");
	    } else {

	    	for (ItemDTO item : itemList) {
	            System.out.printf(" 카테고리No:%d | 코드:%s | 상품이름:%s | 가격:%d원\n",
	               // item.getItemId(),
	                item.getCategoryId(),
	                item.getItemCode(),
	                item.getItemName(),
	                item.getPrice()
	            );
	        }
	    }
	    System.out.println("========================================================");
	}

	public static void adminItemMenu() {
		while (true) {
			System.out.println("-- 관리자 상품 관리메뉴 --");
			System.out.println(" 1. 전체 상품조회 | 2. 상품등록 | 3. 상품수정  |  4. 상품삭제  |  0. 뒤로 가기");
			System.out.print(" ❯ 메뉴 선택 : ");

			try {
				int menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
					case 1:
						System.out.println("\n[관리자] 전체 상품 목록을 조회합니다.");
						ItemController.selectAllItems();
						break;
					case 2:
						ItemView.insertItemView();
						break;
					case 3:
						ItemView.updateItemView(); //
						break;
					case 4:
						ItemView.deleteItemView(); //
						break;
					case 0:
						System.out.println("\n 이전 메뉴로 돌아갑니다.");
						return;
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

    public static void insertItemView() {
		System.out.println("\n--- [Pcafe 새 상품 등록] ---");
	    ItemController.selectAllItems();

	    // 2. 상품 정보 입력받기
	    System.out.print(" ❯ 등록할 카테고리번호 선택 : ");
	    int categoryId = Integer.parseInt(sc.nextLine());

	    System.out.print(" ❯ 상품코드 입력 (예: A00) : ");
	    String itemCode = sc.nextLine();

	    System.out.print(" ❯ 상품이름 입력 : ");
	    String itemName = sc.nextLine();

	    System.out.print(" ❯ 상품가격 입력 : ");
	    int price = Integer.parseInt(sc.nextLine());

	    ItemDTO newItem = new ItemDTO();
	    newItem.setCategoryId(categoryId);
	    newItem.setItemCode(itemCode);
	    newItem.setItemName(itemName);
	    newItem.setPrice(price);

		ItemController.insertItem(newItem);
    }



	//관리자 상품 수정
    public static void updateItemView() {
    	System.out.println("\n--- [Pcafe 상품 정보 수정] ---");

        ItemController.selectAllItems();

        System.out.print(" ❯ 수정할 상품의 코드 입력(예: A00) : ");
        String itemCode = sc.nextLine();

        System.out.print(" ❯ 새로운 상품 이름 입력 : ");
        String itemName = sc.nextLine();

        System.out.print(" ❯ 새로운 상품 가격 입력 : ");
        int price = Integer.parseInt(sc.nextLine());

    	ItemDTO updateItem = new ItemDTO();
        updateItem.setItemCode(itemCode);
        updateItem.setItemName(itemName);
        updateItem.setPrice(price);

		ItemController.updateItem(updateItem);
    }

    public static void deleteItemView() {
	    System.out.println("\n--- [Pcafe 상품 정보 삭제] ---");

	    ItemController.selectAllItems();

	    System.out.print(" ❯ 삭제할 상품의 코드 입력(예: A00) : ");
	    String itemCode = sc.nextLine();

	    ItemDTO deleteItem = new ItemDTO();
	    deleteItem.setItemCode(itemCode);

		ItemController.deleteItem(deleteItem);
    }

}