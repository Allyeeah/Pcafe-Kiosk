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

        // DTO
        MemberDTO memberDTO = new MemberDTO(id, pwd, name);
        
        // DAO
        memberDAO.insert(memberDTO);
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
