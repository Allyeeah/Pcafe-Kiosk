package model.dto;

public class OrderDetailDTO {
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private int quantity;

    public OrderDetailDTO() {}

    public OrderDetailDTO(int orderDetailId, int orderId, int itemId, int quantity) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // getter / setter
    public int getOrderDetailId() {
        return orderDetailId;
    }
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}