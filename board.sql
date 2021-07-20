--테이블 삭제
drop table board;

--시퀀스 삭제
drop sequence seq_board_no;

--table 생성
create table board (
    no          number,
    title       varchar2(500) not null,
    content     varchar2(4000),
    hit         number  default 0,
    reg_date    date not null,
    user_no     number not null,
    primary key(no),
    constraint board_fk foreign key (user_no)
    references users(no)
);
    
--시퀀스 생성
CREATE SEQUENCE seq_board_no
INCREMENT BY 1 
START WITH 1 ;

--insert
insert into board(no, title, content, reg_date, user_no) values(seq_board_no.nextval, '제목목', '가가가가', sysdate, 1);
insert into board(no, title, content, reg_date, user_no) values(seq_board_no.nextval, 'aaa', 'aaa', sysdate, 2);
insert into board(no, title, content, reg_date, user_no) values(seq_board_no.nextval, 'bbb', 'bbb', sysdate, 1);
    
--select
select b.no, b.title, u.name, b.hit, b.reg_date
from board b, users u
where b.user_no = u.no
order by b.reg_date desc;

select b.no, u.name, b.hit, b.reg_date, b.title, b.content
from board b, users u
where b.user_no = u.no;

--확인용
select *
from board;

commit;
rollback;
    