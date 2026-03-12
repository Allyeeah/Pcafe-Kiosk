package controller;

import java.util.List;

import model.dto.MemberDTO;

public class SuccessView {
	public static void selectPrint(List<MemberDTO> list) {
		for(MemberDTO member : list) {
			System.out.println(member);
		}
	}
	
	public static void messagePrint(String message) {
		System.out.println(message);
		
	}

	public static void selectMember(MemberDTO member) {
		System.out.println(member);
		
	}
	
}
