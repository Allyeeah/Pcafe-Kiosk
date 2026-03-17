package service;

import java.sql.SQLException;
import java.util.List;

import model.dao.ItemDAO;
import model.dao.ItemDAOImpl;
import model.dto.ItemDTO;

public class ItemServiceImpl implements ItemService {
	private static final ItemServiceImpl instance = new ItemServiceImpl();
	private final ItemDAO itemDAO = ItemDAOImpl.getInstance();

	private ItemServiceImpl() {}
	public static ItemServiceImpl getInstance() {
		return instance;
	}

	@Override
	public List<ItemDTO> itemSelect() throws SQLException {
		return itemDAO.ItemSelect();
	}

	@Override
	public List<ItemDTO> selectItemsByCategory(int categoryId) throws SQLException {
		return itemDAO.selectItemsByCategory(categoryId);
	}

	@Override
	public int insertItem(ItemDTO newItem) throws SQLException {
		return itemDAO.insertItem(newItem);
	}

	@Override
	public int updateItem(ItemDTO updateItem) throws SQLException {
		return itemDAO.updateItem(updateItem);
	}

	//삭제
	@Override
	public int deleteItem(ItemDTO deleteItem) throws SQLException {
		return itemDAO.deleteItem(deleteItem);
	}

	/**
	 * 상품번호에 해당하는 상품검색
	 * */
	@Override
	public ItemDTO selectItemByCode(String itemCode) throws  SQLException{
		ItemDTO item = itemDAO.selectItemByCode(itemCode);
		if(item==null) {
			throw new SQLException(itemCode + " 현재 상품이 없습니다.");
		}
		return item;
	}





}
