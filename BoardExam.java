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

			System.out.println("===== �Խ��� ���α׷� =====\n");

			if (Session.loginUser == null) {

				System.out.println("1. ȸ������ \n");
				System.out.println("2. �α��� \n");
				System.out.println("3. ���� \n");
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
					System.out.println("=== ���α׷��� �����մϴ� ===");
					run = false;
					break;

				default:
					System.out.println("=== 1~3�� ���ڸ� �Է����ּ��� ===");
					return;

				} // switch(initSelct) ����

			} // if (�α���x) ����

			else { // (�α���o)

				String loggedId = Session.loginUser.getMem_id();
						
				System.out.println("1. �Խù� �ۼ�");
				System.out.println("2. �Խù� ��� ����");
				System.out.println("3. �Խù� ����");
				System.out.println("4. �Խù� ����");
				System.out.println("5. �Խù� ����");
				System.out.println("6. ȸ������ ����");
				System.out.println("7. ȸ�� Ż��");
				System.out.println("8. �α׾ƿ�");
				System.out.println("0. ����");
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

				// �Խù� ���� loginSelect case

				case "6":
					memberService.updateMember();
					break;

				case "7":
					memberService.deleteMember();
					break;

				case "305": // �޴����� ���� �ȵǾ��ִ� ��ũ�� �޴� (�����ڿ�)
					memberService.readAllMember();
					break;
					
				case "8":
					Session.loginUser = null;
					System.out.println("=== �α׾ƿ� �Ǿ����ϴ� ===");
					break;

				case "0":
					System.out.println("=== ���α׷� �����մϴ� ===");
					run = false;
					break;

				default:
					System.out.println("=== 0~8�������� �Է����ּ��� ===");

				} // switch(�α���o) ����

			} // else (�α���o) ����

		} // while(run)����

	} // BoardExam ���� �޼��� ����

} // BoardExam Ŭ���� ����
