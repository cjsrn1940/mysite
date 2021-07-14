
--테이블 삭제
drop table guestbook;

--시퀀스 삭제
drop sequence seq_guestbook_id;

--테이블 생성
create table guestbook(
    no	            number(5),
    name   	        varchar2(80),
    password		varchar2(20),
    content	        varchar2(2000),
    reg_date        date,
    PRIMARY KEY(no)	
);

--시퀀스 생성
CREATE SEQUENCE seq_guestbook_id
INCREMENT BY 1 
START WITH 1 ;

--insert문
insert into guestbook values(seq_guestbook_id.nextval, '이효리', '1111', '1111', sysdate);
insert into guestbook values(seq_guestbook_id.nextval, '정우성', '2222', '2222', sysdate);
insert into guestbook values(seq_guestbook_id.nextval, '유재석', '3333', '3333', sysdate);
insert into guestbook values(seq_guestbook_id.nextval, '이정재', '4444', '4444', sysdate);
insert into guestbook values(seq_guestbook_id.nextval, '강동원', '5555', '5555', sysdate);
insert into guestbook values(seq_guestbook_id.nextval, '하지원', '6666', '6666', sysdate);

commit;


--select문
select  no,
        name,
        password,
        content,
        reg_date
from guestbook;
  