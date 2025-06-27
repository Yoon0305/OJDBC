package board.service;

import java.sql.SQLException;
import java.util.Scanner;

import board.Session;
import board.dao.MemberDAO;
import board.dto.MemberDTO;

public class MemberService {

	// 필드
	private MemberDAO memberDAO = new MemberDAO();
	private Scanner scanner = new Scanner(System.in);

	// 회원가입 메서드

	public void register() {

		MemberDTO member = new MemberDTO(); //DTO에 넣기위한 객체생성

		System.out.println("가입할 아이디를 입력하세요 (최대 15자리)");
		System.out.print(">>>>");
		member.setMem_id(scanner.next());

		System.out.println("비밀번호를 입력하세요 (최대 15자리, 영어+숫자 형식)");
		System.out.print(">>>>");
		member.setMem_m_pw(scanner.next());

		System.out.println("가입할 이메일을 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_email(scanner.next());

		System.out.println("이름을 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_name(scanner.next());

		System.out.println("전화번호를 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_tel(scanner.next());

		try {
			memberDAO.createMember(member);		//DAO에 있는 메서드에서 table에 들어가는지 (데이터 타입, 길이 등) 확인

		} catch (Exception e) {
			System.out.println("중복된 ID거나, 입력하신 데이터 양식이 올바르지 않습니다.");
			e.printStackTrace();
		}

	}// 회원가입 메서드 종료

	// 로그인 메서드

	public void login() {

		System.out.println("ID를 입력하세요");
		System.out.print(">>>>");
		String inputId = scanner.next();

		System.out.println("PW를 입력하세요");
		System.out.print(">>>>");
		String inputPw = scanner.next();

		try { // id,pw가 없는 예외 상황 가정해서 try문 이용

			MemberDTO member = memberDAO.login(inputId, inputPw);
			// DAO메서드에서 실행해서 memberDTO에 들고온 값을 member변수에 저장

			if (member != null) {

				Session.loginUser = member; // 세션에 로그인 정보 저장
				System.out.println("<<" + member.getMem_m_name() + "님 환영합니다>>");

			} else {
				System.out.println("=== 로그인 실패 (ID,PW)을 확인해주세요 ===");
			}

		} catch (SQLException e) {
			System.out.println("ID,PW은 15자리를 넘을 수 없습니다. 다시 확인해주세요.");
			e.printStackTrace();
		}

	}// 로그인 메서드 종료

	// 회원 수정 메서드

	public void updateMember() {

		if (Session.loginUser == null) {
			System.out.println("== 로그인이 필요합니다 ==");
			return;
		} // 회원탈퇴 진행하면서 session이 null처리 되는 경우 더이상 회원 메뉴에 권한이 없어야 함

		MemberDTO member = new MemberDTO(); // 회원 가입과 동일한 방식

		member.setMem_id(Session.loginUser.getMem_id()); // ID는 PK이기 때문에 수정X.
															// ID를 기준으로 다른 정보들 호출

		System.out.println("변경할 비밀 번호를 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_pw(scanner.next());

		System.out.println("변경할 이메일을 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_email(scanner.next());

		System.out.println("변경할 이름을 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_name(scanner.next());

		System.out.println("변경할 전화 번호를 입력하세요");
		System.out.print(">>>>");
		member.setMem_m_tel(scanner.next());

		try {
			
			memberDAO.updateMember(member);
			
			Session.loginUser = memberDAO.login(member.getMem_id(), member.getMem_m_pw());
			// 세션이 갖고있는 정보도 수정된 정보로 변경
		} catch (Exception e) {

			e.printStackTrace();
		}

	} // 회원 수정 메서드 종료

	
	//회원 삭제 메서드
	
	public void deleteMember() {
		
		if (Session.loginUser == null) {
			System.out.println("== 로그인이 필요합니다 ==");
			return;
		} 
		
		System.out.println("정말 탈퇴하시겠습니까? 숫자를 입력해주세요 (1. 예 / 2. 아니오)");
		System.out.print(">>>>");
		String inputChoice = scanner.next();
		
		if(inputChoice.equals("2")) {
			System.out.println("탈퇴를 취소합니다.\n");
			System.out.println("=== 이전화면으로 돌아갑니다 ===");
			
		} else if(inputChoice.equals("1"))  {
			
			try {
				memberDAO.deleteMember(Session.loginUser.getMem_id());
				System.out.println("=== 탈퇴 완료되었습니다 ===");
				Session.loginUser = null;
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			System.out.println("1~2의 숫자만 눌러주세요");
			return;
		}
		


		
	}//회원 삭제 메서드 종료

	// (관리자용) 회원 목록 전체 보기 메서드
	public void readAllMember() throws SQLException {
		
		if (Session.loginUser == null) {
			System.out.println("== 로그인이 필요합니다 ==");
			return;
		} 
			try {
				memberDAO.readAllMember();
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		
	} // (관리자용) 회원 목록 전체 보기 메서드 종료

}// MemberService 클래스 종료
