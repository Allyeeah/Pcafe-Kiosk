package service;

import java.sql.SQLException;
import java.util.List;

import model.dao.ItemDAO;
import model.dao.ItemDAOImpl;
import model.dto.ItemDTO;

public class ItemServiceImpl implements ItemService {
	private static ItemServiceImpl instance = new ItemServiceImpl(); 
	private ItemDAO itemDAO = ItemDAOImpl.getInstance();
	
	public ItemServiceImpl() {}
	public static ItemServiceImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<ItemDTO> itemSelect() throws SQLException {
		// TODO Auto-generated method stub
		return itemDAO.ItemSelect();
	}

	@Override
	public List<ItemDTO> selectItemsByCategory(int categoryId) throws SQLException {
		// TODO Auto-generated method stub
		return itemDAO.selectItemsByCategory(categoryId);
	}
	
	/**
	 * 상품번호에 해당하는 상품검색
	 * */
	public ItemDTO selectItemByCode(String itemCode) throws  SQLException{
		ItemDTO item = itemDAO.selectItemByCode(itemCode);
		if(item==null) throw new SQLException(itemCode + " 현재 상품이 없습니다.");
		return item;
	}
	
	

}
