package board.dto;

public class MemberDTO {
	
	private String mem_id;
	private String mem_m_pw;
	private String mem_m_email;
	private String mem_m_name;
	private String mem_m_tel;
	
	
	public String getMem_id() {
		return mem_id;
	}
	public String getMem_m_pw() {
		return mem_m_pw;
	}
	public String getMem_m_email() {
		return mem_m_email;
	}
	public String getMem_m_name() {
		return mem_m_name;
	}
	public String getMem_m_tel() {
		return mem_m_tel;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public void setMem_m_pw(String mem_m_pw) {
		this.mem_m_pw = mem_m_pw;
	}
	public void setMem_m_email(String mem_m_email) {
		this.mem_m_email = mem_m_email;
	}
	public void setMem_m_name(String mem_m_name) {
		this.mem_m_name = mem_m_name;
	}
	public void setMem_m_tel(String mem_m_tel) {
		this.mem_m_tel = mem_m_tel;
	}

}
