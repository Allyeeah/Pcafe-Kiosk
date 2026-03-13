package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exception.CancelFailedException;
import exception.OrderFailedException;
import exception.OrderNotFoundException;
import model.dao.ItemDAO;
import model.dao.ItemDAOImpl;
import model.dao.OrderDAO;
import model.dao.OrderDAOImpl;
import model.dto.ItemDTO;
import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public class OrderServiceImpl implements OrderService {
	private static OrderService instance = new OrderServiceImpl();
	private OrderDAO orderDAO = OrderDAOImpl.getInstance();
	private ItemDAO itemDAO = ItemDAOImpl.getInstance();

	private OrderServiceImpl() {}
	public static OrderService getInstance() {
		return instance;
	}

	@Override
	public void placeOrder(OrdersDTO order) {
		int result = 0;
		try {
			List<String> itemCodes = order.getOrderDetails().stream()
					.map(OrderDetailDTO::getItemCode)
					.map(String::toUpperCase)
					.distinct()
					.toList();

			List<ItemDTO> items = itemDAO.selectItemsByCodes(itemCodes);

			Map<String, ItemDTO> itemMap = items.stream()
					.collect(Collectors.toMap(item -> item.getItemCode().toUpperCase(), item -> item));

			for (OrderDetailDTO detail : order.getOrderDetails()) {
				ItemDTO item = itemMap.get(detail.getItemCode());
				if (item == null) {
					throw new OrderFailedException("입력한 상품 코드가 존재하지 않습니다.");
				}

				detail.setItemId(item.getItemId());
				detail.setItemName(item.getItemName());
				detail.setUnitPrice(item.getPrice());
			}
			order.updateTotalAmount();

			result = orderDAO.insert(order);
		} catch (SQLException e) {
			throw new OrderFailedException(e.getMessage());
		}

		if (result == 0) throw new OrderFailedException();
	}
	
	@Override
	public void cancelOrder(int orderId) {
        int result = 0;
        try {
            result = orderDAO.updateStatus(orderId, Status.CANCELED);
        } catch (SQLException e) {
            throw new CancelFailedException(e.getMessage());
        }

        if (result == 0) throw new CancelFailedException();
	}
	
	@Override
	public List<OrdersDTO> findAllOrders() {
		List<OrdersDTO> orders = new ArrayList<>();
		try {
			orders = orderDAO.selectAll();
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) throw new OrderNotFoundException();
		
		return orders;
	}
	
	@Override
	public List<OrdersDTO> findOrdersByUserId(String userId) {
		List<OrdersDTO> orders = new ArrayList<>();
		try {
			orders = orderDAO.selectByUserId(userId);
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) throw new OrderNotFoundException();
		
		return orders;
	}

	@Override
	public List<OrderDetailDTO> findOrderDetailsByItemCode(String itemCode) {
		List<OrderDetailDTO> details = new ArrayList<>();
		try {
			details = orderDAO.selectByItemCode(itemCode);
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (details.isEmpty()) throw new OrderNotFoundException();

		return details;
	}

	public int getTotalPrice(List<OrderDetailDTO> details) {
		return details.stream()
				.mapToInt(value -> value.getUnitPrice() * value.getQty())
				.sum();
	}
}
