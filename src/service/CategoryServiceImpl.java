package service;

import java.sql.SQLException;
import java.util.List;

import model.dao.CategoryDAO;
import model.dao.CategoryDAOImpl;
import model.dto.CategoryDTO;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDAO categoryDAO = new CategoryDAOImpl();
	
	@Override
	public List<CategoryDTO> getCategoryList() throws SQLException {
		
		return categoryDAO.selectAll();
	}

}
