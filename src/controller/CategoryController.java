package controller;

import java.sql.SQLException;
import java.util.List;

import exception.CategoryException;
import model.dto.CategoryDTO;
import service.CategoryService;
import service.CategoryServiceImpl;
import view.FailView;

public class CategoryController {
	 private static CategoryController instance = new CategoryController();
	 private CategoryService categoryService = CategoryServiceImpl.getInstance();
	 
	 public CategoryController() {}
	 public static CategoryController getInstance() {
		return instance;
	}
	 
	 //카테고리 조회
	 public void selectAll() {
		 try {
			List<CategoryDTO> list = categoryService.getCategoryList();
			SuccessView.selectCategoryPrint(list);
		 }catch(Exception e) {
			 FailView.errorMessage(e.getMessage());
		 }
	 }
	
	 public void insert(String category) {
		    try {
		        categoryService.insert(category);
		        // 성공 시 출력
		        System.out.println(category + " 카테고리가 추가되었습니다.");
		    } catch (CategoryException e) {
		        // 중복 오류 등 비즈니스 로직 에러
		        FailView.errorMessage(e.getMessage());
		    } catch (SQLException e) {
		        // DB 관련 에러 
		        FailView.errorMessage("DB 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
		    }
		}
	 
	public void update(String category, int cacategoryId)  {
		try {
			categoryService.update(category, cacategoryId);
			System.out.println("카테고리가 수정되었습니다.");
		}catch(CategoryException e) {
			FailView.errorMessage("[카테고리 수정 실패] " + e.getMessage());
		}
		catch (SQLException e) {
	        // DB 관련 에러 
	        FailView.errorMessage("DB 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
	    }
	}
	
	public void delete(int categoryId) {
		try {
			categoryService.delete(categoryId);
			System.out.println("카테고리가 삭제되었습니다");
		}catch(CategoryException e) {
			FailView.errorMessage("[카테고리 삭제 실패]"+ e.getMessage());
		}
		catch (SQLException e) {
	        // DB 관련 에러 
	        FailView.errorMessage("DB 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
	    }
	}
	
}
