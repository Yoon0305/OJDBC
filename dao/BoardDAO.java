package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import board.dto.BoardDTO;

public class BoardDAO {
	// �ʵ�
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public int result = 0;

	// �⺻ ������

public BoardDAO() {
	
	try {

		Class.forName("oracle.jdbc.driver.OracleDriver");

		connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.180:1521:xe", "boardexam", "boardexam");

	} catch (ClassNotFoundException e) {
		System.out.println("����̹� ȣ��, ���� ���� �����Դϴ�.");
		e.printStackTrace();

	} catch (SQLException e) {
		System.out.println("(id,pw,url,������)�� ������ ���� �����Դϴ�.");
		e.printStackTrace();
	}
	
} // �⺻ ������ ����

	// �Խñ� �ۼ�
	public void createBoard(BoardDTO boardDTO) throws SQLException {
		
		String sql = "insert into board(brd_postnum, brd_title, brd_content, brd_writer) values(board_seq.nextval,?,?,?)";
		
		preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1,boardDTO.getBrd_title());
		preparedStatement.setString(2,boardDTO.getBrd_content());
		preparedStatement.setString(3,boardDTO.getBrd_writer());
		
		result = preparedStatement.executeUpdate(); // ����
		
		if (result >0) {
			System.out.println("�Խñ��� ���� ��ϵǾ����ϴ�.");
			// System.out.println(result+"���� �Խñ� ��� ����");
			connection.commit();
		} else {
			System.out.println("�Խñ� ��� �����߽��ϴ�.");
			connection.rollback();
		
		}
		preparedStatement.close();
	} // �Խñ� �ۼ� �޼��� ����

	
	// �Խñ� ��� ����
	public void printBoardList() throws SQLException {

		String sql = "select brd_postnum, brd_title, brd_date from board order by brd_postnum";
		
		preparedStatement = connection.prepareStatement(sql);
		
		resultSet = preparedStatement.executeQuery();
		
		System.out.println("=== �Խñ� ��� ===\n");
		
		while (resultSet.next()) {
			System.out.println("�Խñ� ��ȣ : "+ resultSet.getInt("brd_postnum"));
			System.out.println("�Խñ� ���� : "+ resultSet.getString("brd_title"));
			System.out.println("�Խ� ��¥ : "+ resultSet.getDate("brd_date"));
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
		
			System.out.println("�Խñ� ��ȣ : " + resultSet.getInt("brd_postnum")); 
			System.out.println("�Խñ� ���� : " + resultSet.getString("brd_title")); 
			System.out.println("�Խñ� ���� : " + resultSet.getString("brd_content")); 
			System.out.println("�ۼ��� ID : " + loggedId); 
			System.out.println("�Խ��� : "+ resultSet.getDate("brd_date"));		
		}
		resultSet.close();
		preparedStatement.close();
		
	}

} // BoardDAO Ŭ���� ����
