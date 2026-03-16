package service;

import java.sql.SQLException;
import java.util.List;

import exception.CategoryException;
import model.dao.CategoryDAO;
import model.dao.CategoryDAOImpl;
import model.dto.CategoryDTO;


public class CategoryServiceImpl implements CategoryService {
	private static final CategoryService instance = new CategoryServiceImpl();
	private final CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();

	private CategoryServiceImpl() {}
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
	//---->*** 여기부터 수정
	// CategoryServiceImpl.java
	@Override
	public void update(String category, int categoryId) throws SQLException, CategoryException {
	    // 1. 해당 ID가 실제로 존재하는지 조회
	    CategoryDTO existing = categoryDAO.selectByCategoryId(categoryId);

	    // 2. 만약 없다면 예외 발생!
	    if (existing == null) {
	        throw new CategoryException("[" + categoryId + "]번 카테고리는 존재하지 않습니다.");
	    }

	    // 3. 존재할 때만 수정 실행
	    int result = categoryDAO.update(category, categoryId);

	    // (선택사항) 만약 update 쿼리는 날아갔는데 영향받은 행이 0개일 때를 대비한 2중 방어
	    if (result == 0) {
	        throw new CategoryException("카테고리 수정에 실패했습니다.");
	    }
	}




	@Override
	public void delete(int categoryId) throws SQLException , CategoryException{
		// 1. 해당 ID가 실제로 존재하는지 조회
	    CategoryDTO existing = categoryDAO.selectByCategoryId(categoryId);

	    // 2. 만약 없다면 예외 발생!
	    if (existing == null) {
	        throw new CategoryException("[" + categoryId + "]번 카테고리는 존재하지 않습니다.");
	    }

	    // 3. 존재할 때만 수정 실행
	    int result = categoryDAO.delete(categoryId);

	    // (선택사항) 만약 update 쿼리는 날아갔는데 영향받은 행이 0개일 때를 대비한 2중 방어
	    if (result == 0) {
	        throw new CategoryException("카테고리 삭제에 실패했습니다.");
	    }
	}


}


