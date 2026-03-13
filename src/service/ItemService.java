package service;

import java.sql.SQLException;
import java.util.List;

import model.dto.ItemDTO;

public interface ItemService {

	// 1. 전체 상품 조회 기능
    List<ItemDTO> itemSelect() throws SQLException;
    
    // 2. 카테고리별 상품 조회 기능 (JOIN 활용)
    List<ItemDTO> selectItemsByCategory(int categoryId) throws SQLException;

    // 3.상품등록
    int insertItem(ItemDTO newItem) throws SQLException;
}
