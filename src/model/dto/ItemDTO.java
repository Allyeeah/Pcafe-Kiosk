package model.dto;

public class ItemDTO {
    private int itemId;
    private String itemCode;
    private String itemName;
    private int price;
    private int categoryId;
    private String categoryName; //카테고리이름추가

    public ItemDTO() {}

    public ItemDTO(int itemId, String itemCode, String itemName, int price, int categoryId, String categoryName) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // getter / setter
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

//	@Override
//	public String toString() {
//		return "ItemDTO [상품ID=" + itemId + ", 상품코드=" + itemCode + ", 상품이름=" + itemName + ", 가격=" + price
//				+ ", 카테고리ID=" + categoryId + "]";
//	}

	@Override
    public String toString() {
        return "[" + categoryName + "] " + itemName + " (코드: " + itemCode + ") - " + price + "원 입니다.";
    }


}