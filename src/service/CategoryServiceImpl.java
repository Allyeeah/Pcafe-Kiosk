package service;

import java.sql.SQLException;
import java.util.List;

import model.dao.CategoryDAO;
import model.dao.CategoryDAOImpl;
import model.dto.CategoryDTO;


public class CategoryServiceImpl implements CategoryService {
	private static CategoryService instance = new CategoryServiceImpl();
	private CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();

	public static CategoryService getInstance() {
		return instance;
	}
	@Override
	public List<CategoryDTO> getCategoryList() throws SQLException {
		
		return categoryDAO.selectAll();
	}

	@Override
	public int insert(String category) throws SQLException {
		return categoryDAO.insert(category);
	}
	@Override
	public int update(String category, int categoryId) throws SQLException {
		return categoryDAO.update(category, categoryId);
	}
	@Override
	public int delete(int categoryId) throws SQLException {
		return categoryDAO.delete(categoryId);
	}
	
}
