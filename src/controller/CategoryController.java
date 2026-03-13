package controller;

import java.sql.SQLException;

import exception.CategoryException;
import service.CategoryService;
import service.CategoryServiceImpl;
import view.FailView;

public class CategoryController {
	 private static CategoryController instance = new CategoryController();
	 private CategoryService categoryService = CategoryServiceImpl.getInstance();
	 
	 private CategoryController() {}
	 public static CategoryController getInstance() {
		return instance;
	}
	
	public void insert(String category) throws SQLException {
		try {
			categoryService.insert(category);
			System.out.println(category + " 카테고리가 추가되었습니다");
		}catch (CategoryException e) {
			FailView.errorMessage("[카테고리 추가 실패]" + e.getMessage());
		}
	}
	
	public void update(String category, int cacategoryId) throws SQLException {
		try {
			categoryService.update(category, cacategoryId);
			System.out.println("카테고리가 수정되었습니다.");
		}catch(CategoryException e) {
			FailView.errorMessage("[카테고리 수정 실패]" + e.getMessage());
		}
	}
	
	public void delete(int categoryId) throws SQLException {
		try {
			categoryService.delete(categoryId);
			System.out.println("카테고리가 삭제되었습니다");
		}catch(CategoryException e) {
			FailView.errorMessage("[카테고리 삭제 실패]"+ e.getMessage());
		}
	}
	
}
