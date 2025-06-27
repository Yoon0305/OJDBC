package board.service;

import java.sql.SQLException;
import java.util.Scanner;

import board.Session;
import board.dao.BoardDAO;
import board.dto.BoardDTO;

public class BoardService {

	private BoardDAO boardDAO = new BoardDAO();
	private Scanner scanner = new Scanner(System.in);

	public void createBoard() {

		if (Session.loginUser == null) {
			System.out.println("=== �α����� �ʿ��� ���� �Դϴ� ===");
			return;
		} else {

			System.out.print("���� : ");
			String title = scanner.nextLine();

			System.out.println("������ �Է��ϼ���");
			System.out.print("===========================\n");
			String content = scanner.nextLine();

			BoardDTO boardDTO = new BoardDTO();

			boardDTO.setBrd_title(title);
			boardDTO.setBrd_content(content);
			boardDTO.setBrd_writer(Session.loginUser.getMem_id());

			try {
				boardDAO.createBoard(boardDTO);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} // else����, login session���� �α��� �� ����� �ۼ�
	} // �Խñ� �ۼ� �޼��� ����

	// �Խñ� ��� ����
	public void printBoardList() {

		try {
			boardDAO.printBoardList();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �Խñ� �ϳ� ����
	public void viewBoard(String loggedId) {

		System.out.println("������� �Խù� ��ȣ�� �Է��ϼ���");
		System.out.print(">>>>");
		int postNum = scanner.nextInt();

		try {
			boardDAO.viewBoard(postNum, loggedId);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}// �Խñ� �ϳ� ���� �޼��� ����

	public void updateBoard() {
		// TODO Auto-generated method stub

	}

	public void deleteBoard() {
		// TODO Auto-generated method stub

	}

} // BoardService Ŭ���� ����
