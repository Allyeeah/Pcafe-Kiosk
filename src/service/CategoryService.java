package service;

import java.sql.SQLException;
import java.util.List;
import model.dto.CategoryDTO;

public interface CategoryService {
    
    // 카테고리 전체 목록을 조회
    List<CategoryDTO> getCategoryList() throws SQLException;
    
}