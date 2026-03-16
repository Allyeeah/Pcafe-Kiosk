package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.OrderDetailDTO;
import model.dto.OrdersDTO;
import model.dto.OrdersDTO.Status;

public interface OrderDAO {

	int insert(OrdersDTO order) throws SQLException;

	int updateStatus(int orderId, Status status) throws SQLException;

	List<OrdersDTO> selectAll() throws SQLException;

	List<OrdersDTO> selectByUserId(String userId) throws SQLException;

	List<OrdersDTO> selectByDate(String date) throws SQLException;

	OrdersDTO selectById(int orderId) throws SQLException;

	List<OrderDetailDTO> selectByItemCode(String itemCode) throws SQLException;

}
