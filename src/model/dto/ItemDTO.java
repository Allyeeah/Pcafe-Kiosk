package model.dto;

public class ItemDTO {
    private int itemId;
    private String itemCode;
    private String itemName;
    private int price;
    private int categoryId;

    public ItemDTO() {}

    public ItemDTO(int itemId, String itemCode, String itemName, int price, int categoryId) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.categoryId = categoryId;
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
}