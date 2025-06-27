package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import board.dto.BoardDTO;

public class BoardDAO {
	// 필드
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public int result = 0;

	// 기본 생성자

public BoardDAO() {
	
	try {

		Class.forName("oracle.jdbc.driver.OracleDriver");

		connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.180:1521:xe", "boardexam", "boardexam");

	} catch (ClassNotFoundException e) {
		System.out.println("드라이버 호출, 연결 관련 오류입니다.");
		e.printStackTrace();

	} catch (SQLException e) {
		System.out.println("(id,pw,url,쿼리문)의 구조적 문제 오류입니다.");
		e.printStackTrace();
	}
	
} // 기본 생성자 종료

	// 게시글 작성
	public void createBoard(BoardDTO boardDTO) throws SQLException {
		
		String sql = "insert into board(brd_postnum, brd_title, brd_content, brd_writer) values(board_seq.nextval,?,?,?)";
		
		preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1,boardDTO.getBrd_title());
		preparedStatement.setString(2,boardDTO.getBrd_content());
		preparedStatement.setString(3,boardDTO.getBrd_writer());
		
		result = preparedStatement.executeUpdate(); // 검증
		
		if (result >0) {
			System.out.println("게시글이 정상 등록되었습니다.");
			// System.out.println(result+"개의 게시글 등록 성공");
			connection.commit();
		} else {
			System.out.println("게시글 등록 실패했습니다.");
			connection.rollback();
		
		}
		preparedStatement.close();
	} // 게시글 작성 메서드 종료

	
	// 게시글 목록 보기
	public void printBoardList() throws SQLException {

		String sql = "select brd_postnum, brd_title, brd_date from board order by brd_postnum";
		
		preparedStatement = connection.prepareStatement(sql);
		
		resultSet = preparedStatement.executeQuery();
		
		System.out.println("=== 게시글 목록 ===\n");
		
		while (resultSet.next()) {
			System.out.println("게시글 번호 : "+ resultSet.getInt("brd_postnum"));
			System.out.println("게시글 제목 : "+ resultSet.getString("brd_title"));
			System.out.println("게시 날짜 : "+ resultSet.getDate("brd_date"));
		}	
		resultSet.close();
		preparedStatement.close();
		
	}

	public void viewBoard(int postNum, String loggedId) throws SQLException {
		
		
		String sql = "select * from board where brd_postnum = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1, postNum);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
		
			System.out.println("게시글 번호 : " + resultSet.getInt("brd_postnum")); 
			System.out.println("게시글 제목 : " + resultSet.getString("brd_title")); 
			System.out.println("게시글 내용 : " + resultSet.getString("brd_content")); 
			System.out.println("작성자 ID : " + loggedId); 
			System.out.println("게시일 : "+ resultSet.getDate("brd_date"));		
		}
		resultSet.close();
		preparedStatement.close();
		
	}

} // BoardDAO 클래스 종료
