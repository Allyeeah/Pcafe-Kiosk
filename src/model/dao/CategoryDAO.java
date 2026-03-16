package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.CategoryDTO;

public interface CategoryDAO {

	public List<CategoryDTO> selectAll() throws SQLException;
	public int insert(String category) throws SQLException;
	public int update(String category, int categoryId) throws SQLException;
	public int delete(int categoryId) throws SQLException;
	CategoryDTO selectByName(String categoryName) throws SQLException;
	CategoryDTO selectByCategoryId(int categoryId) throws SQLException;
}
