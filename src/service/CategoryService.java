package service;

import java.sql.SQLException;
import java.util.List;
import model.dto.CategoryDTO;

public interface CategoryService {
    
    // 카테고리 전체 목록을 조회
    List<CategoryDTO> getCategoryList() throws SQLException;
    
    //카테고리 삽입
	int insert(String category) throws SQLException;
	
	// 카테고리 수정
	void update(String category, int categoryId) throws SQLException;
	
	// 카테고리 삭제
	void delete(int categoryId) throws SQLException;
}