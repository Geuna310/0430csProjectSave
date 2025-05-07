Use camp_database;

CREATE TABLE member_table(
	member_no int NOT NULL AUTO_INCREMENT PRIMARY KEY, -- 계정의 고유번호
    member_id varchar(20) NOT NULL unique, 				-- ID
    member_password varchar(255) NOT NULL, 				-- 비밀번호
    member_name varchar(5) NOT NULL, 					-- 본명
    member_nickname varchar(15) NOT NULL, 				-- 닉네임
    member_phone varchar(13) NOT NULL unique, 			-- 전화번호
    member_email varchar(35) NOT NULL unique, 			-- 이메일
    member_birth varchar(10) NOT NULL, 					-- 출생년도
    member_gender varchar(3) NOT NULL, 					-- 성별
	member_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null, -- 가입날짜
    member_image_url VARCHAR(255),						-- 프로필 사진
    is_business bit not null default 0
 );
 
ALTER TABLE member_table
MODIFY COLUMN member_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null;

 CREATE TABLE admin_table(
	admin_no int NOT NULL AUTO_INCREMENT PRIMARY KEY, 		-- 계정의 고유번호
    admin_id varchar(20) NOT NULL unique, 					-- ID
	admin_password varchar(255) NOT NULL, 					-- 비밀번호
    admin_name varchar(5) NOT NULL, 						-- 본명
    admin_phone varchar(13) NOT NULL unique, 				-- 전화번호
    admin_email varchar(35) NOT NULL unique, 				-- 이메일
    admin_birth varchar(10) NOT NULL, 						-- 출생년도
    admein_role varchar(20) not null default 'admin',		-- 권한
	admin_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null 	-- 생성날짜
 );
 
ALTER TABLE admin_table
drop COLUMN admein_role;
 
 
 INSERT INTO admin_table (admin_id, admin_password, admin_name, admin_phone, admin_email, admin_birth, admin_role)
VALUES ('qwer', 'qwer', '홍길동', '010-1234-5679', 'admin2@test.com', '1990-01-01','admin');

select * from admin_table;
 
 
 
 
 
 

DESCRIBE warehouses_table;
 
 
SELECT * FROM member_table;

SHOW CREATE TABLE member_table;

DESCRIBE member_table;
