package board;

import java.sql.SQLException;
import java.util.Scanner;

import board.dao.BoardDAO;
import board.service.BoardService;
import board.service.MemberService;

public class BoardExam {

	public static void main(String[] args) throws SQLException {

		Scanner scanner = new Scanner(System.in);

		MemberService memberService = new MemberService();

		BoardService boardService = new BoardService();

		boolean run = true;

		while (run) {

			System.out.println("===== 게시판 프로그램 =====\n");

			if (Session.loginUser == null) {

				System.out.println("1. 회원가입 \n");
				System.out.println("2. 로그인 \n");
				System.out.println("3. 종료 \n");
				System.out.print(">>>>");

				String initSelect = scanner.next();

				switch (initSelect) {

				case "1":
					memberService.register();
					break;

				case "2":
					memberService.login();
					break;

				case "3":
					System.out.println("=== 프로그램을 종료합니다 ===");
					run = false;
					break;

				default:
					System.out.println("=== 1~3의 숫자만 입력해주세요 ===");
					return;

				} // switch(initSelct) 종료

			} // if (로그인x) 종료

			else { // (로그인o)

				String loggedId = Session.loginUser.getMem_id();
						
				System.out.println("1. 게시물 작성");
				System.out.println("2. 게시물 목록 보기");
				System.out.println("3. 게시물 보기");
				System.out.println("4. 게시물 삭제");
				System.out.println("5. 게시물 수정");
				System.out.println("6. 회원정보 수정");
				System.out.println("7. 회원 탈퇴");
				System.out.println("8. 로그아웃");
				System.out.println("0. 종료");
				System.out.print(">>>>");

				String loginSelect = scanner.next();

				switch (loginSelect) {

				case "1":
					boardService.createBoard();
					break;

				case "2":
					boardService.printBoardList();
					break;

				case "3":
					boardService.viewBoard(loggedId);
					break;

				case "4":
					boardService.updateBoard();
					break;

				case "5":
					boardService.deleteBoard();
					break;

				// 게시물 관련 loginSelect case

				case "6":
					memberService.updateMember();
					break;

				case "7":
					memberService.deleteMember();
					break;

				case "305": // 메뉴에서 공개 안되어있는 시크릿 메뉴 (관리자용)
					memberService.readAllMember();
					break;
					
				case "8":
					Session.loginUser = null;
					System.out.println("=== 로그아웃 되었습니다 ===");
					break;

				case "0":
					System.out.println("=== 프로그램 종료합니다 ===");
					run = false;
					break;

				default:
					System.out.println("=== 0~8번까지만 입력해주세요 ===");

				} // switch(로그인o) 종료

			} // else (로그인o) 종료

		} // while(run)종료

	} // BoardExam 메인 메서드 종료

} // BoardExam 클래스 종료
