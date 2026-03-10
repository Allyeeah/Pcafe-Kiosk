package model.dto;

public class OrdersDTO {
    private int orderId;
    private String userId;
    private java.sql.Timestamp orderDate;
    private String status;

    public OrdersDTO() {}

    public OrdersDTO(int orderId, String userId, java.sql.Timestamp orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
    }

    // getter / setter
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
