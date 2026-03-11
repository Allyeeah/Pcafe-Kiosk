package model.dto;

import java.util.List;

public class OrdersDTO {
    private int orderId;
    private String userId;
    private String orderDate;
    private Status status;
    
    private List<OrderDetailDTO> details;

    public OrdersDTO() {}

    public OrdersDTO(int orderId, String userId, String orderDate, Status status) {
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
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public List<OrderDetailDTO> getOrderDetails() {
    	return details;
    }
    public void setOrderDetails(List<OrderDetailDTO> details) {
    	this.details = details;
    }
        
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrdersDTO [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", orderDate=");
		builder.append(orderDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", details=");
		builder.append(details);
		builder.append("]\n");
		return builder.toString();
	}

	public enum Status {
    	COMPLETE("주문 완료"),
    	CANCELED("주문 취소");
    	
    	private final String label;
    	
    	Status(String label) {
    		this.label = label;
    	}
    	
    	public String label() {
    		return label;
    	}
    }
}
