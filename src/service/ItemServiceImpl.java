package service;

import java.sql.SQLException;
import java.util.List;

import model.dao.ItemDAO;
import model.dao.ItemDAOImpl;
import model.dto.ItemDTO;

public class ItemServiceImpl implements ItemService {

	private ItemDAO itemDAO = ItemDAOImpl.getInstance();
	
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

	@Override
	public int insertItem(ItemDTO newItem)throws SQLException {
		// TODO Auto-generated method stub
		return itemDAO.insertItem(newItem);
	}

}
