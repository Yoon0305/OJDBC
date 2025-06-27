package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import board.Session;
import board.dto.MemberDTO;

public class MemberDAO {

	// 필드
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public int result = 0;

	// 기본 생성자

	public MemberDAO() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.180:1521:xe", "boardexam",
					"boardexam");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 호출, 연결 관련 오류입니다.");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("(id,pw,url,쿼리문)의 구조적 문제 오류입니다.");
			e.printStackTrace();
		}

	} // 기본 생성자 종료

	// 회원가입 메서드(sql문 써서 DTO에 항목별로 set)

	public void createMember(MemberDTO memberDTO) throws SQLException {

		String sql = "insert into member(mem_id, mem_m_pw, mem_m_email, mem_m_name, mem_m_tel) values (?,?,?,?,?)";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, memberDTO.getMem_id());
		preparedStatement.setString(2, memberDTO.getMem_m_pw());
		preparedStatement.setString(3, memberDTO.getMem_m_email());
		preparedStatement.setString(4, memberDTO.getMem_m_name());
		preparedStatement.setString(5, memberDTO.getMem_m_tel());

		result = preparedStatement.executeUpdate(); // 데이터 삽입 결과 숫자로 받기

		if (result > 0) {
			System.out.println("=== 회원가입 성공 ===\n");
			// System.out.println(result + "명의 회원");
			connection.commit();

		} else {
			System.out.println("=== 회원가입 실패 ===\n");
			connection.rollback();
		}
		preparedStatement.close(); // sql 연결 닫고 메서드 종료

	} // 회원 가입 메서드 종료

	// 로그인 메서드 ( 1. 로그인 성공시 MemberDTO 반환. 반환하려면 값을 저장해서 들고갈 빈 변수 필요)
	// 2. MemberService에서 스캐너로 ID,PW 입력받아서 옴)
	public MemberDTO login(String inputId, String inputPw) throws SQLException {
		MemberDTO memberDTO = null; // 초기화 + 타입 선언

		String sql = "select * from member where mem_id = ? and mem_m_pw = ?";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, inputId);
		preparedStatement.setString(2, inputPw);

		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			memberDTO = new MemberDTO(); // 넣어갈 객체 생성

			memberDTO.setMem_id(resultSet.getString("mem_id"));
			memberDTO.setMem_m_pw(resultSet.getString("mem_m_pw"));
			memberDTO.setMem_m_email(resultSet.getString("mem_m_email"));
			memberDTO.setMem_m_name(resultSet.getString("mem_m_name"));
			memberDTO.setMem_m_tel(resultSet.getString("mem_m_tel"));

		} // 로그인이 가능한 때 불러오는 상황만 지정.
			// resultSet에서 불러온 값이 없을때는 service로 돌아가서 session 닫음
		resultSet.close();
		preparedStatement.close();
		return memberDTO;

	} // 로그인 메서드 종료

	// 회원 정보 수정 메서드 (로그인한 사용자만 가능), PK인 ID를 기준으로 나머지 정보 수정
	public void updateMember(MemberDTO memberDTO) throws SQLException {

		String sql = "update member set mem_m_pw = ?, mem_m_email = ?, mem_m_name = ?, mem_m_tel = ? where mem_id = ?";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, memberDTO.getMem_m_pw());
		preparedStatement.setString(2, memberDTO.getMem_m_email());
		preparedStatement.setString(3, memberDTO.getMem_m_name());
		preparedStatement.setString(4, memberDTO.getMem_m_tel());

		preparedStatement.setString(5, memberDTO.getMem_id());

		result = preparedStatement.executeUpdate(); // 수정 검증. service에서 받은걸 dto에 꽂아서 진행
													// id 제외한 전체 정보 수정 가능.
		if (result > 0) {
			System.out.println("=== 회원 정보를 수정하였습니다 ===");
			// System.out.println(result + "개 계정의 정보 수정");
			connection.commit();
		} else {
			System.out.println("=== 회원 정보 수정 실패 ===");
			connection.rollback();
		}
		preparedStatement.close();

	} // 회원 정보 수정 메서드 종료

	// 회원 정보 탈퇴(삭제) 메서드
	public void deleteMember(String delMem_id) throws SQLException {

		delMem_id = Session.loginUser.getMem_id(); // 세션상의 로그인된 정보 service에서 갖고 내려왔음.

		// 삭제 전 백업
		String backupSql = "insert into deletedMember select * from member where mem_id = ?";

		preparedStatement = connection.prepareStatement(backupSql);

		preparedStatement.setString(1, delMem_id);

		result = preparedStatement.executeUpdate(); // 백업 검증

		if (result > 0) {
			// System.out.println("백업 성공");
		}

		preparedStatement.close();

		// member테이블에서 진짜 삭제
		String deleteSql = "delete from member where mem_id = ?";

		preparedStatement = connection.prepareStatement(deleteSql);

		preparedStatement.setString(1, delMem_id); // 삭제를 진행하고 있는 로그인 세션상의 id가 ?값으로

		result = preparedStatement.executeUpdate(); // 삭제 검증

		if (result > 0) {
			System.out.println("=== 삭제가 완료되었습니다 ===");
			connection.commit();
		} else {
			System.out.println("=== 삭제 실패하였습니다 ===");
			connection.rollback();
		}
		preparedStatement.close();

	}// 회원 탈퇴(삭제) 메서드 종료

	// (관리자용) 회원 목록 전체 보기 메서드
	public void readAllMember() throws SQLException {

		String sql = "select mem_m_name, mem_id, mem_m_email from member";
		// 비밀번호와 전화번호는 개인정보이므로 관리자라도 노출 x
		// 회원 탈퇴가 로그인한 상태에서만 가능하기 때문에 ip,pw을 까먹은 사람은
		// 관리자에게 문의해서 id를 조회 후, 테이블에서 직접 삭제할 수 있도록 함.

		statement=connection.createStatement();
		
		resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			
			System.out.println("1. 이름\t" + resultSet.getString("mem_m_name") + "\t");
			System.out.println("2. 아이디\t" + resultSet.getString("mem_id") + "\t");
			System.out.println("3. 이메일\t" + resultSet.getString("mem_m_email") + "\t\n");
			System.out.println("============================================\n");
			
			}
		
		

	} //readAllMember 메서드 종료

} // MemberDAO 클래스 종료
