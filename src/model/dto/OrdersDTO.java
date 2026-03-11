package model.dto;

import java.util.List;

public class OrdersDTO {
    private int orderId;
    private String userId;
    private String orderDate;
    private Status status;     
    private int totalAmount;  //총금액 추가
    
    private List<OrderDetailDTO> details;

    public OrdersDTO() {}

 
    public OrdersDTO(int orderId, String userId, String orderDate, Status status, int totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getter / Setter
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public int getTotalAmount() { return totalAmount; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderDetailDTO> getOrderDetails() { return details; }
    public void setOrderDetails(List<OrderDetailDTO> details) { this.details = details; }
        
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrdersDTO [orderId=").append(orderId)
               .append(", userId=").append(userId)
               .append(", orderDate=").append(orderDate)
               .append(", status=").append(status)
               .append(", totalAmount=").append(totalAmount)
               .append(", details=").append(details)
               .append("]\n");
        return builder.toString();
    }

    // enum
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

        //추가
        public static Status fromLabel(String label) {
            for (Status s : Status.values()) {
                if (s.label().equals(label)) return s;
            }
            return COMPLETE; 
        }
    }
}