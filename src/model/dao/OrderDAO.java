package model.dao;

import java.util.List;

import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;

public interface OrderDAO {
	
	int insert(OrdersDTO order);
	
	int delete(OrdersDTO order);
	
	List<OrdersDTO> selectAll();
	
	List<OrdersDTO> selectByUserId(String userId);
	
	List<OrderDetailDTO> selectByItemId(String itemId);
}
