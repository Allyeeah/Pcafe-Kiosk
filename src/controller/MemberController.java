package controller;

import exception.UpdateUserInfoException;
import model.dto.MemberDTO;
import service.MemberService;
import service.MemberServiceImpl;
import view.FailView;
import view.MenuView;

import java.sql.SQLException;

public class MemberController {
    private static final MemberService memberService = MemberServiceImpl.getInstance();

	public static void register(MemberDTO member) {
		try {
			memberService.register(member);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

    public static void login(String userId, String userPwd) {
        MemberDTO member = null;
        try {
            // 1. 서비스에서 로그인 시도 (실패 시 서비스 내부에서 Exception을 던져야 함)
            member = memberService.login(userId, userPwd);
         
            // 2. 로그인 성공 시: 권한(isAdmin)에 따른 메뉴 분기
            if (member != null) {
                if ("Y".equalsIgnoreCase(member.getIsAdmin())) {
	                System.out.println("\n[관리자] 계정으로 로그인하셨습니다");
                    MenuView.printAdminMenu(userId);
                } else {
	                System.out.println("\n[" + member.getUserName() +"]"+ "님 로그인하셨습니다");
                    MenuView.printUserMenu(userId);
                }
            }

        }
        catch (Exception e) {
            // FailView를 통해 사용자에게 깔끔한 메시지만 전달
            FailView.errorMessage(e.getMessage());
        }
    }


	public static void updateMemberInfo(String userId, String userPw, String userName) {
		try {
			memberService.updateMemberInfo(userId, userPw, userName);
			System.out.println("사용자 정보가 수정되었습니다");
		}catch(UpdateUserInfoException e) {
			FailView.errorMessage("[사용자 정보 수정 실패]"+ e.getMessage());
		}
		catch(SQLException e) {
			FailView.errorMessage("DB 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
		}
		
	}
	
	public static void withdrawMember(String userId, String userPwd) {
		try {
			memberService.withdrawMember(userId, userPwd);
			System.out.println("탈퇴되었습니다.");
			
		}catch(UpdateUserInfoException e) {
			FailView.errorMessage("[사용자 탈퇴 실패] 사용자 정보를 다시 입력해주세요.");
		}
		catch(SQLException e) {
			FailView.errorMessage("DB 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
			}
		
		}

	}
