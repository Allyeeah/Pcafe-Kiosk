package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.ItemDTO;

public interface ItemDAO {
	
	//전체상품조회 
	List<ItemDTO> ItemSelect() throws SQLException;
	
	//카테고리 해당하는 전체 상품정보 검색 (조인쿼리로 같이 가져올예정)
	List<ItemDTO> selectItemsByCategory(int categoryId) throws SQLException;

	//item_code로 특정 상품 조회
	ItemDTO selectItemByCode(String code) throws SQLException;

	List<ItemDTO> selectItemsByCodes(List<String> itemCodes) throws SQLException;

	int insertItem(ItemDTO newItem) throws SQLException;
}
