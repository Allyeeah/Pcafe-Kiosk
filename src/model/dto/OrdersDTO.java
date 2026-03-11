package model.dto;

import java.util.List;

public class OrdersDTO {
    private int orderId;
    private String userId;
    private String orderDate;
    private String status;
    
    private List<OrderDetailDTO> details;

    public OrdersDTO() {}

    public OrdersDTO(int orderId, String userId, String orderDate, String status) {
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
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<OrderDetailDTO> getOrderDetails() {
    	return details;
    }
    public void setOrderDetails(List<OrderDetailDTO> details) {
    	this.details = details;
    }
}
