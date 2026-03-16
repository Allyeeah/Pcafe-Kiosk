package model.dto;

public class OrderDetailDTO {
    private int orderDetailId;  // order_detail_id
    private int orderId;        // order_id
    private int itemId;         // item_id
    private String itemCode;    // item_code
    private String itemName;    // 상품명 * orderdatil 테이블에는 없음
    private int unitPrice;      // unit_price 가격
    private int qty;            // qty 수량
    //총금액 필요시 unitPrice * qty
    public OrderDetailDTO() {}

    public OrderDetailDTO(int orderDetailId, int orderId, int itemId, String itemCode, String itemName, int unitPrice, int qty) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }


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
    public int getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(itemCode)
                .append("] ")
                .append(itemName)
                .append("-")
                .append(unitPrice)
                .append("원 / ")
                .append(qty)
                .append("개");
        return builder.toString();
    }
}