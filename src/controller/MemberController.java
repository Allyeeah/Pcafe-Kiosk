package controller;

import model.dto.MemberDTO;
import service.MemberService;
import view.FailView;
import view.MenuView;

public class MemberController {
    static MemberService memberService = new MemberService();


    public static MemberDTO login(String userId, String userPwd) {
        MemberDTO member = null; 
        try {
            // 1. 서비스에서 로그인 시도 (실패 시 서비스 내부에서 Exception을 던져야 함)
            member = memberService.login(userId, userPwd);
            
            // 2. 로그인 성공 시: 권한(isAdmin)에 따른 메뉴 분기
            if (member != null) {
                if ("Y".equalsIgnoreCase(member.getIsAdmin())) {
                    MenuView.printAdminMenu(userId);
                } else {
                    MenuView.printUserMenu(userId);
                }
            }
            
        } catch (Exception e) {
            // e.printStackTrace(); 
            // FailView를 통해 사용자에게 깔끔한 메시지만 전달
            FailView.errorMessage(e.getMessage());
        }
        
        return member; 
    }


	public static void updateMemberInfo(String userPw, String userName) {
		// TODO Auto-generated method stub
		
	}
}