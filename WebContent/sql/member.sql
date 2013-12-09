create table member (
no int not null auto_increment primary key,
id varchar(30) not null,
password varchar(50) not null,
gubun char(1),
email varchar(50),
handphone varchar(50),
profile text,
signature text,
regist_date date,
last_login time,
pro_img varchar(100),
apply_cnt int,
selected_cnt int,
done_cnt int
)

-----
SELECT * FROM (
SELECT @ROWNUM := @ROWNUM + 1 AS ROW, MEMBER.* FROM MEMBER,(SELECT @ROWNUM := 0) R)
WHERE ROW > 5 and ROW < 15;

---------
SELECT RM,T2.* FROM(
SELECT @RNUM:=@RNUM+1 AS RM, T1.* 
FROM (SELECT * FROM MEMBER ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R 
)  AS T2 
WHERE RM >=11  AND RM <= 20


---------
create table project (
no int not null auto_increment primary key,
writer_id varchar(50) not null,
writer_nick varchar(50) not null,
cate char(2),
status char(2),
title text,
context text,
amount varchar(10),
jobdate varchar(10),
regist_date date,
modify_date date,
apply_cnt int
)

------------
create table apply (
no int not null auto_increment primary key,
user_id varchar(50) not null,
apply_no varchar(50) not null,
context text,
regist_date date
)

------------
create table message (
no int not null auto_increment primary key,
from_id varchar(50) not null,
to_id varchar(50) not null,
newYN char(1),
context text,
send_date date,
read_date date
)
