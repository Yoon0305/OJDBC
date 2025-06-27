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
			System.out.println("=== 로그인이 필요한 서비스 입니다 ===");
			return;
		} else {

			System.out.print("제목 : ");
			String title = scanner.nextLine();

			System.out.println("본문을 입력하세요");
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

		} // else종료, login session통해 로그인 한 사람만 작성
	} // 게시글 작성 메서드 종료

	// 게시글 목록 보기
	public void printBoardList() {

		try {
			boardDAO.printBoardList();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 게시글 하나 보기
	public void viewBoard(String loggedId) {

		System.out.println("보고싶은 게시물 번호를 입력하세요");
		System.out.print(">>>>");
		int postNum = scanner.nextInt();

		try {
			boardDAO.viewBoard(postNum, loggedId);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}// 게시글 하나 보기 메서드 종료

	public void updateBoard() {
		// TODO Auto-generated method stub

	}

	public void deleteBoard() {
		// TODO Auto-generated method stub

	}

} // BoardService 클래스 종료
