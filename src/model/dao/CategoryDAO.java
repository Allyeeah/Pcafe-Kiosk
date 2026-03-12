package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.CategoryDTO;

public interface CategoryDAO {

	public List<CategoryDTO> selectAll() throws SQLException;
	
}
