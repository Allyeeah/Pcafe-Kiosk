package view;

import java.util.Scanner;

import model.dao.MemberDAO;
import model.dao.MemberDAOImpl;
import model.dto.MemberDTO;

public class StartView {
    private Scanner scan = new Scanner(System.in);
    private MemberDAO memberDAO = MemberDAOImpl.getInstance(); 

    public void menu() {
        int num;

        while (true) {
            System.out.println("********** PCafe 키오스크 **********");
            System.out.println("    1. 회원가입");
            System.out.println("    2. 로그인");
            System.out.println("    3. 프로그램 종료");
            System.out.println("*********************************");
            System.out.print("번호 입력 : ");

                     num = scan.nextInt();
            scan.nextLine(); 
            if (num == 1) {
                signUp(); 
            } else if (num == 2) {
                signIn(); 
            } else if (num == 3) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("없는 번호 입니다.\n");
            }
        }
    }

    // 1. 회원가입 입력
    private void signUp() {
        System.out.println("\n--- [회원가입] ---");
        System.out.print("아이디: ");
        String id = scan.nextLine();

        System.out.print("비밀번호(4자리): ");
        String pwd = scan.nextLine();

        System.out.print("이름: ");
        String name = scan.nextLine();
        
        System.out.print("관리자로 가입하시겠습니까? (Y/N): ");
        String isAdminInput = scan.nextLine().trim().toUpperCase();
        String isAdmin = isAdminInput.equals("Y") ? "Y" : "N";

        //test
        //
        MemberDTO memberDTO = new MemberDTO(id, pwd, name, isAdmin, null);

        // DAO insert 메서드가 int 반환이라 가정
        int result = memberDAO.insert(memberDTO);

        if (result > 0) {
            System.out.println("회원가입이 완료되었습니다.\n");
        } else {
            System.out.println("회원가입 실패: 아이디 중복 또는 오류.\n");
        }
    }
    // 2. 로그인 입력
    private void signIn() {
        System.out.println("\n--- [로그인] ---");
        System.out.print("아이디: ");
        String id = scan.nextLine();
        System.out.print("비밀번호: ");
        String pwd = scan.nextLine();

           }


    
    
    public static void main(String[] args) {
        StartView startView = new StartView();
        startView.menu();
    }
}
