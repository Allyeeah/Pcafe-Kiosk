package controller;

import java.util.List;

import model.dto.MemberDTO;
import service.AdminService;
import service.AdminServiceImpl;
import view.FailView;
import view.MemberView;

public class AdminController {
	private static final AdminService adminService = AdminServiceImpl.getInstance();

		public static void selectMemberById(String userId) {
			try {
				MemberDTO member = adminService.selectMemberById(userId);
				MemberView.printMember(member);
			}catch(Exception e) {
				FailView.errorMessage(e.getMessage());
			}

		}


	public static void selectMemberByName(String userName) {
		try {
			MemberDTO member = adminService.selectMemberByName(userName);
			MemberView.printMember(member);
		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void selectAllMember(){
		try {
			List<MemberDTO> list = adminService.selectAllMember();
			MemberView.printMembers(list);
		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}

	}
}
