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
	private static final OrderService instance = new OrderServiceImpl();
	private final OrderDAO orderDAO = OrderDAOImpl.getInstance();
	private final ItemDAO itemDAO = ItemDAOImpl.getInstance();

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

		if (result == 0) {
			throw new OrderFailedException();
		}
	}

	@Override
	public void cancelOrder(String userId, int orderId) {
        int result = 0;
        try {
			OrdersDTO order = orderDAO.selectById(orderId);
			if (order == null) {
				throw new OrderNotFoundException();
			}
			if (order.getStatus() == Status.CANCELED) {
				throw new CancelFailedException("이미 취소된 주문입니다.");
			}
			if (!userId.equals(order.getUserId())) {
				throw new CancelFailedException("자신의 주문만 취소할 수 있습니다.");
			}

            result = orderDAO.updateStatus(orderId, Status.CANCELED);
        } catch (SQLException e) {
            throw new CancelFailedException(e.getMessage());
        }

        if (result == 0) {
			throw new CancelFailedException();
		}
	}

	@Override
	public OrdersDTO reorder(String userId, int orderId) {
		OrdersDTO order = null;
		int result = 0;
		try {
			order = orderDAO.selectById(orderId);
			if (order == null) {
				throw new OrderNotFoundException();
			}
			if (!userId.equals(order.getUserId())) {
				throw new OrderFailedException("본인의 주문번호가 아닙니다.");
			}

			result = orderDAO.insert(order);
		} catch (SQLException e) {
			throw new OrderFailedException(e.getMessage());
		}

		if (result == 0) {
			throw new OrderFailedException();
		}

		return order;
	}

	@Override
	public List<OrdersDTO> findAllOrders() {
		List<OrdersDTO> orders = new ArrayList<>();
		try {
			orders = orderDAO.selectAll();
			orders.sort((o1, o2) -> Integer.compare(o1.getOrderId(), o2.getOrderId())); //주문번호 오름차순정렬
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) {
			throw new OrderNotFoundException();
		}

		return orders;
	}

	@Override
	public List<OrdersDTO> findOrdersByUserId(String userId) {
		List<OrdersDTO> orders = new ArrayList<>();
		try {
			orders = orderDAO.selectByUserId(userId);
			orders.sort((o1, o2) -> Integer.compare(o1.getOrderId(), o2.getOrderId())); //주문번호 오름차순정렬
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) {
			throw new OrderNotFoundException();
		}

		return orders;
	}

	@Override
	public List<OrdersDTO> findOrdersByDate(String date) {
		List<OrdersDTO> orders;
		try {
			orders = orderDAO.selectByDate(date);
			orders.sort((o1, o2) -> Integer.compare(o1.getOrderId(), o2.getOrderId())); //주문번호 오름차순정렬
		} catch (SQLException e) {
			throw new OrderNotFoundException();
		}
		if (orders.isEmpty()) {
			throw new OrderNotFoundException();
		}

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
		if (details.isEmpty()) {
			throw new OrderNotFoundException();
		}

		return details;
	}

	@Override
	public int getTotalOrderPrice(List<OrdersDTO> orders) {
		return orders.stream()
					.mapToInt(OrdersDTO::getTotalAmount)
					.sum();
	}

	@Override
	public int getTotalItemPrice(List<OrderDetailDTO> details) {
		return details.stream()
				.mapToInt(value -> value.getUnitPrice() * value.getQty())
				.sum();
	}
}
