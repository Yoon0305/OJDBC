package board.dto;

import java.sql.Date;

public class BoardDTO {
	
	private int brd_postnum;
	private String brd_title;
	private String brd_content;
	private String brd_writer;
	private Date brd_date;
	
	
	public int getBrd_postnum() {
		return brd_postnum;
	}
	public String getBrd_title() {
		return brd_title;
	}
	public String getBrd_content() {
		return brd_content;
	}
	public String getBrd_writer() {
		return brd_writer;
	}
	public Date getBrd_date() {
		return brd_date;
	}
	public void setBrd_postnum(int brd_postnum) {
		this.brd_postnum = brd_postnum;
	}
	public void setBrd_title(String brd_title) {
		this.brd_title = brd_title;
	}
	public void setBrd_content(String brd_content) {
		this.brd_content = brd_content;
	}
	public void setBrd_writer(String brd_writer) {
		this.brd_writer = brd_writer;
	}
	public void setBrd_date(Date brd_date) {
		this.brd_date = brd_date;
	}

}
