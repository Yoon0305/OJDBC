create table member (
mem_id varchar2(15) primary key,

mem_m_pw varchar2(15),
mem_m_email varchar2(30),
mem_m_name varchar2(15),
mem_m_tel varchar2(20)

)

-- 삭제용 drop table member

create table board (
brd_postnum number primary key,

brd_title nvarchar2(30),
brd_content nvarchar2(1000),
brd_writer varchar2(15),
brd_date date default sysdate
)

-- drop table board
-- drop sequence board_seq

create sequence board_seq start with 1 increment by 1 nocache nocycle

-- drop sequence board_seq

create table deletedBoard as select * from board where 1=0

create table deletedMember as select * from member where 1=0



insert into member(mem_id, mem_m_pw, mem_m_email, mem_m_name, mem_m_tel) values ('kkw','1234','kkw@ab.com','김기원','010-1111-1111')

insert into board(brd_postnum, brd_title, brd_content, brd_writer) values (board_seq.nextval,'덥네요','등교하느라 수고했습니다','김기원')


select * from board

select * from member

select * from deletedMember