package service;

import java.sql.SQLException;
import java.util.List;

import exception.CategoryException;
import model.dao.CategoryDAO;
import model.dao.CategoryDAOImpl;
import model.dto.CategoryDTO;


public class CategoryServiceImpl implements CategoryService {
	private static CategoryService instance = new CategoryServiceImpl();
	private CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();

	public static CategoryService getInstance() {
		return instance;
	}
	//카테고리 전체 조회
	@Override
	public List<CategoryDTO> getCategoryList() throws SQLException {
		return categoryDAO.selectAll();
	}

	@Override
	public int insert(String category) throws SQLException, CategoryException {
	    // 1. 먼저 해당 카테고리가 있는지 DB에서 조회
	    CategoryDTO existing = categoryDAO.selectByName(category);
	    
	    if (existing != null) {
	        // 2. 이미 존재한다면 예외를 던짐 (여기서 catch하지 않고 위로 던짐)
	        throw new CategoryException(category + "은(는) 이미 존재하는 카테고리입니다.");
	    }
	    
	    // 3. 존재하지 않을 때만 실행
	    return categoryDAO.insert(category);
	    
	}
	//---->*** 여기부터 수정!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public int update(String category, int categoryId) throws SQLException , CategoryException{
		// 1. 해당 ID가 실제로 존재하는지 조회
	    CategoryDTO existing = categoryDAO.selectByCategoryId(categoryId);
	    
	    if (existing != null) {
	        // 2. 이미 존재한다면 예외를 던짐 (여기서 catch하지 않고 위로 던짐)
	        throw new CategoryException(category + "은(는) 이미 존재하는 카테고리입니다.");
	    }
		return categoryDAO.update(category, categoryId);
	}
	@Override
	public int delete(int categoryId) throws SQLException , CategoryException{
		return categoryDAO.delete(categoryId);
	}
	
}
