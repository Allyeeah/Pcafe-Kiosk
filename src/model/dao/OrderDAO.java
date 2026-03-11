package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public interface OrderDAO {
	
	int insert(OrdersDTO order);
	
	int updateStatus(int orderId, Status status);
	
	List<OrdersDTO> selectAll() throws SQLException;
	
	List<OrdersDTO> selectByUserId(String userId) throws SQLException;
	
	List<OrderDetailDTO> selectByItemId(String itemId) throws SQLException;

}
