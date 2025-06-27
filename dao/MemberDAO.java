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

	// �ʵ�
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public int result = 0;

	// �⺻ ������

	public MemberDAO() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.180:1521:xe", "boardexam",
					"boardexam");

		} catch (ClassNotFoundException e) {
			System.out.println("����̹� ȣ��, ���� ���� �����Դϴ�.");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("(id,pw,url,������)�� ������ ���� �����Դϴ�.");
			e.printStackTrace();
		}

	} // �⺻ ������ ����

	// ȸ������ �޼���(sql�� �Ἥ DTO�� �׸񺰷� set)

	public void createMember(MemberDTO memberDTO) throws SQLException {

		String sql = "insert into member(mem_id, mem_m_pw, mem_m_email, mem_m_name, mem_m_tel) values (?,?,?,?,?)";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, memberDTO.getMem_id());
		preparedStatement.setString(2, memberDTO.getMem_m_pw());
		preparedStatement.setString(3, memberDTO.getMem_m_email());
		preparedStatement.setString(4, memberDTO.getMem_m_name());
		preparedStatement.setString(5, memberDTO.getMem_m_tel());

		result = preparedStatement.executeUpdate(); // ������ ���� ��� ���ڷ� �ޱ�

		if (result > 0) {
			System.out.println("=== ȸ������ ���� ===\n");
			// System.out.println(result + "���� ȸ��");
			connection.commit();

		} else {
			System.out.println("=== ȸ������ ���� ===\n");
			connection.rollback();
		}
		preparedStatement.close(); // sql ���� �ݰ� �޼��� ����

	} // ȸ�� ���� �޼��� ����

	// �α��� �޼��� ( 1. �α��� ������ MemberDTO ��ȯ. ��ȯ�Ϸ��� ���� �����ؼ� ��� �� ���� �ʿ�)
	// 2. MemberService���� ��ĳ�ʷ� ID,PW �Է¹޾Ƽ� ��)
	public MemberDTO login(String inputId, String inputPw) throws SQLException {
		MemberDTO memberDTO = null; // �ʱ�ȭ + Ÿ�� ����

		String sql = "select * from member where mem_id = ? and mem_m_pw = ?";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, inputId);
		preparedStatement.setString(2, inputPw);

		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			memberDTO = new MemberDTO(); // �־ ��ü ����

			memberDTO.setMem_id(resultSet.getString("mem_id"));
			memberDTO.setMem_m_pw(resultSet.getString("mem_m_pw"));
			memberDTO.setMem_m_email(resultSet.getString("mem_m_email"));
			memberDTO.setMem_m_name(resultSet.getString("mem_m_name"));
			memberDTO.setMem_m_tel(resultSet.getString("mem_m_tel"));

		} // �α����� ������ �� �ҷ����� ��Ȳ�� ����.
			// resultSet���� �ҷ��� ���� �������� service�� ���ư��� session ����
		resultSet.close();
		preparedStatement.close();
		return memberDTO;

	} // �α��� �޼��� ����

	// ȸ�� ���� ���� �޼��� (�α����� ����ڸ� ����), PK�� ID�� �������� ������ ���� ����
	public void updateMember(MemberDTO memberDTO) throws SQLException {

		String sql = "update member set mem_m_pw = ?, mem_m_email = ?, mem_m_name = ?, mem_m_tel = ? where mem_id = ?";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, memberDTO.getMem_m_pw());
		preparedStatement.setString(2, memberDTO.getMem_m_email());
		preparedStatement.setString(3, memberDTO.getMem_m_name());
		preparedStatement.setString(4, memberDTO.getMem_m_tel());

		preparedStatement.setString(5, memberDTO.getMem_id());

		result = preparedStatement.executeUpdate(); // ���� ����. service���� ������ dto�� �ȾƼ� ����
													// id ������ ��ü ���� ���� ����.
		if (result > 0) {
			System.out.println("=== ȸ�� ������ �����Ͽ����ϴ� ===");
			// System.out.println(result + "�� ������ ���� ����");
			connection.commit();
		} else {
			System.out.println("=== ȸ�� ���� ���� ���� ===");
			connection.rollback();
		}
		preparedStatement.close();

	} // ȸ�� ���� ���� �޼��� ����

	// ȸ�� ���� Ż��(����) �޼���
	public void deleteMember(String delMem_id) throws SQLException {

		delMem_id = Session.loginUser.getMem_id(); // ���ǻ��� �α��ε� ���� service���� ���� ��������.

		// ���� �� ���
		String backupSql = "insert into deletedMember select * from member where mem_id = ?";

		preparedStatement = connection.prepareStatement(backupSql);

		preparedStatement.setString(1, delMem_id);

		result = preparedStatement.executeUpdate(); // ��� ����

		if (result > 0) {
			// System.out.println("��� ����");
		}

		preparedStatement.close();

		// member���̺��� ��¥ ����
		String deleteSql = "delete from member where mem_id = ?";

		preparedStatement = connection.prepareStatement(deleteSql);

		preparedStatement.setString(1, delMem_id); // ������ �����ϰ� �ִ� �α��� ���ǻ��� id�� ?������

		result = preparedStatement.executeUpdate(); // ���� ����

		if (result > 0) {
			System.out.println("=== ������ �Ϸ�Ǿ����ϴ� ===");
			connection.commit();
		} else {
			System.out.println("=== ���� �����Ͽ����ϴ� ===");
			connection.rollback();
		}
		preparedStatement.close();

	}// ȸ�� Ż��(����) �޼��� ����

	// (�����ڿ�) ȸ�� ��� ��ü ���� �޼���
	public void readAllMember() throws SQLException {

		String sql = "select mem_m_name, mem_id, mem_m_email from member";
		// ��й�ȣ�� ��ȭ��ȣ�� ���������̹Ƿ� �����ڶ� ���� x
		// ȸ�� Ż�� �α����� ���¿����� �����ϱ� ������ ip,pw�� ����� �����
		// �����ڿ��� �����ؼ� id�� ��ȸ ��, ���̺��� ���� ������ �� �ֵ��� ��.

		statement=connection.createStatement();
		
		resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			
			System.out.println("1. �̸�\t" + resultSet.getString("mem_m_name") + "\t");
			System.out.println("2. ���̵�\t" + resultSet.getString("mem_id") + "\t");
			System.out.println("3. �̸���\t" + resultSet.getString("mem_m_email") + "\t\n");
			System.out.println("============================================\n");
			
			}
		
		

	} //readAllMember �޼��� ����

} // MemberDAO Ŭ���� ����
