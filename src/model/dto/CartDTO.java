package model.dto;

public class CartDTO {
    private int cartId;
    private String userId;
    private int itemId;
    private int qty;

    public CartDTO() {}

    public CartDTO(int cartId, String userId, int itemId, int qty) {
        this.cartId = cartId;
        this.userId = userId;
        this.itemId = itemId;
        this.qty = qty;
    }

    // getter / setter
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
}
