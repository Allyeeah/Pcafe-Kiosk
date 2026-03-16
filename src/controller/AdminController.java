package controller;

import java.util.List;

import model.dto.MemberDTO;
import service.AdminService;
import service.AdminServiceImpl;
import view.FailView;
import view.SuccessView;

public class AdminController {
	private static final AdminService adminService = AdminServiceImpl.getInstance();

		public static void selectMemberById(String userId) {
			try {
				MemberDTO member = adminService.selectMemberById(userId);
				SuccessView.selectMember(member);
			}catch(Exception e) {
				FailView.errorMessage(e.getMessage());
			}

		}


	public static void selectMemberByName(String userName) {
		try {
			MemberDTO member = adminService.selectMemberByName(userName);
			SuccessView.selectMember(member);
		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void selectAllMember(){
		try {
			List<MemberDTO> list = adminService.selectAllMember();
			SuccessView.selectPrint(list);
		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}

	}
}
