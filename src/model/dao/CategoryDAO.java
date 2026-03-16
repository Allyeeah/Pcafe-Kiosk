package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.CategoryDTO;

public interface CategoryDAO {

	List<CategoryDTO> selectAll() throws SQLException;

	int insert(String category) throws SQLException;

	int update(String category, int categoryId) throws SQLException;

	int delete(int categoryId) throws SQLException;

	CategoryDTO selectByName(String categoryName) throws SQLException;

	CategoryDTO selectByCategoryId(int categoryId) throws SQLException;
}
