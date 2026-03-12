package controller;

import java.lang.reflect.Member;

import model.dto.MemberDTO;
import service.MemberService;
import view.FailView;
import view.MenuView;

public class MemberController {
	static MemberService memberService = new MemberService();
	// 로그인
	public static void login(String userId, String userPwd) {
		try {
			MemberDTO member = memberService.login(userId, userPwd);
			MenuView.printUserMenu(userId);
		}catch(Exception e) {
			//e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
	}
}
