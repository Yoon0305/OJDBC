package board.service;

import java.sql.SQLException;
import java.util.Scanner;

import board.Session;
import board.dao.MemberDAO;
import board.dto.MemberDTO;

public class MemberService {

	// �ʵ�
	private MemberDAO memberDAO = new MemberDAO();
	private Scanner scanner = new Scanner(System.in);

	// ȸ������ �޼���

	public void register() {

		MemberDTO member = new MemberDTO(); //DTO�� �ֱ����� ��ü����

		System.out.println("������ ���̵� �Է��ϼ��� (�ִ� 15�ڸ�)");
		System.out.print(">>>>");
		member.setMem_id(scanner.next());

		System.out.println("��й�ȣ�� �Է��ϼ��� (�ִ� 15�ڸ�, ����+���� ����)");
		System.out.print(">>>>");
		member.setMem_m_pw(scanner.next());

		System.out.println("������ �̸����� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_email(scanner.next());

		System.out.println("�̸��� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_name(scanner.next());

		System.out.println("��ȭ��ȣ�� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_tel(scanner.next());

		try {
			memberDAO.createMember(member);		//DAO�� �ִ� �޼��忡�� table�� ������ (������ Ÿ��, ���� ��) Ȯ��

		} catch (Exception e) {
			System.out.println("�ߺ��� ID�ų�, �Է��Ͻ� ������ ����� �ùٸ��� �ʽ��ϴ�.");
			e.printStackTrace();
		}

	}// ȸ������ �޼��� ����

	// �α��� �޼���

	public void login() {

		System.out.println("ID�� �Է��ϼ���");
		System.out.print(">>>>");
		String inputId = scanner.next();

		System.out.println("PW�� �Է��ϼ���");
		System.out.print(">>>>");
		String inputPw = scanner.next();

		try { // id,pw�� ���� ���� ��Ȳ �����ؼ� try�� �̿�

			MemberDTO member = memberDAO.login(inputId, inputPw);
			// DAO�޼��忡�� �����ؼ� memberDTO�� ���� ���� member������ ����

			if (member != null) {

				Session.loginUser = member; // ���ǿ� �α��� ���� ����
				System.out.println("<<" + member.getMem_m_name() + "�� ȯ���մϴ�>>");

			} else {
				System.out.println("=== �α��� ���� (ID,PW)�� Ȯ�����ּ��� ===");
			}

		} catch (SQLException e) {
			System.out.println("ID,PW�� 15�ڸ��� ���� �� �����ϴ�. �ٽ� Ȯ�����ּ���.");
			e.printStackTrace();
		}

	}// �α��� �޼��� ����

	// ȸ�� ���� �޼���

	public void updateMember() {

		if (Session.loginUser == null) {
			System.out.println("== �α����� �ʿ��մϴ� ==");
			return;
		} // ȸ��Ż�� �����ϸ鼭 session�� nulló�� �Ǵ� ��� ���̻� ȸ�� �޴��� ������ ����� ��

		MemberDTO member = new MemberDTO(); // ȸ�� ���԰� ������ ���

		member.setMem_id(Session.loginUser.getMem_id()); // ID�� PK�̱� ������ ����X.
															// ID�� �������� �ٸ� ������ ȣ��

		System.out.println("������ ��� ��ȣ�� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_pw(scanner.next());

		System.out.println("������ �̸����� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_email(scanner.next());

		System.out.println("������ �̸��� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_name(scanner.next());

		System.out.println("������ ��ȭ ��ȣ�� �Է��ϼ���");
		System.out.print(">>>>");
		member.setMem_m_tel(scanner.next());

		try {
			
			memberDAO.updateMember(member);
			
			Session.loginUser = memberDAO.login(member.getMem_id(), member.getMem_m_pw());
			// ������ �����ִ� ������ ������ ������ ����
		} catch (Exception e) {

			e.printStackTrace();
		}

	} // ȸ�� ���� �޼��� ����

	
	//ȸ�� ���� �޼���
	
	public void deleteMember() {
		
		if (Session.loginUser == null) {
			System.out.println("== �α����� �ʿ��մϴ� ==");
			return;
		} 
		
		System.out.println("���� Ż���Ͻðڽ��ϱ�? ���ڸ� �Է����ּ��� (1. �� / 2. �ƴϿ�)");
		System.out.print(">>>>");
		String inputChoice = scanner.next();
		
		if(inputChoice.equals("2")) {
			System.out.println("Ż�� ����մϴ�.\n");
			System.out.println("=== ����ȭ������ ���ư��ϴ� ===");
			
		} else if(inputChoice.equals("1"))  {
			
			try {
				memberDAO.deleteMember(Session.loginUser.getMem_id());
				System.out.println("=== Ż�� �Ϸ�Ǿ����ϴ� ===");
				Session.loginUser = null;
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			System.out.println("1~2�� ���ڸ� �����ּ���");
			return;
		}
		


		
	}//ȸ�� ���� �޼��� ����

	// (�����ڿ�) ȸ�� ��� ��ü ���� �޼���
	public void readAllMember() throws SQLException {
		
		if (Session.loginUser == null) {
			System.out.println("== �α����� �ʿ��մϴ� ==");
			return;
		} 
			try {
				memberDAO.readAllMember();
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		
	} // (�����ڿ�) ȸ�� ��� ��ü ���� �޼��� ����

}// MemberService Ŭ���� ����
