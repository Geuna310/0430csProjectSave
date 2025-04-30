Use camp_database;

show tables;

-- 캠핑장 정보 테이블
CREATE TABLE campsites_table (
    campsite_no int AUTO_INCREMENT PRIMARY KEY,      		-- 캠핑장 고유번호
    campsite_name VARCHAR(100) NOT NULL,                 	-- 캠핑장 이름
    campsite_location VARCHAR(255) NOT NULL,             	-- 캠핑장 주소
    campsite_description TEXT,                           	-- 캠핑장 설명(소개글)
    campsite_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 등록일
    campsite_business_number VARCHAR(20), 					-- 사업자번호
    campsite_mapimage VARCHAR(255),							-- 캠핑장 배치도 (넣을지 안넣을지 등록할때 정할 수 있게 not null 사용안함.)
    campsite_checkIn TIME NOT NULL,                    		-- 체크인 시간 (예: 14:00:00)
	campsite_checkOut TIME NOT NULL,                    	-- 체크아웃 시간 (예: 11:00:00)
    member_no int NOT NULL, 								-- 계정 고유번호
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE -- 사용자 외래키 (사용자 정보 삭제시 자동 삭제) 
);
-- 캠핑장 이미지 테이블 (여러 이미지 등록 가능)
CREATE TABLE campsites_images_table (
    campsite_image_no bigint AUTO_INCREMENT PRIMARY KEY,       -- 이미지 고유번호
    campsite_image_url VARCHAR(255) NOT NULL,                   -- 이미지 URL
    campsite_image_sort_order INT DEFAULT 0,                    -- 이미지 정렬 순서
    campsite_no int NOT NULL,                             		-- 연결된 캠핑장 고유번호
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE -- 캠핑장 외래키 (캠핑장 정보 삭제 시 자동 삭제)
);


-- 캠핑장 구역 테이블
CREATE TABLE sections_table (
    section_no int AUTO_INCREMENT PRIMARY KEY,             	-- 구역 고유번호
    section_name VARCHAR(100) NOT NULL,                     -- 구역 이름
    section_description TEXT,                               -- 구역 소개
    section_map VARCHAR(255),								-- 구역 배치도(넣을지 안넣을지 등록할때 정할 수 있게 not null 사용안함.)
    campsite_no int NOT NULL,                           	-- 연결된 캠핑장 고유번호 (외래키)
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE -- 캠핑장 외래키 (캠핑장 정보 삭제 시 자동 삭제)
);

-- 객실 정보 테이블 (캠핑장 1개에 여러 객실 가능)
CREATE TABLE rooms_table (
    room_no int AUTO_INCREMENT PRIMARY KEY,             -- 객실 고유번호
    room_name VARCHAR(100) NOT NULL,                    -- 객실 이름
    room_price int NOT NULL,                            -- 1박 가격 (비성수기)
    room_peak_price int NOT NULL,                      	-- 1박 가격 (성수기)
    room_capacity int NOT NULL,                         -- 최대 수용 인원
    room_onoff ENUM('on', 'off') DEFAULT 'on',    		-- 예약 상태 (기본적으로 on)
    section_no int NOT NULL,							-- 연결된 구역 고유번호 (외래키)
    FOREIGN KEY (section_no) REFERENCES sections_table(section_no) ON DELETE CASCADE -- 구역 외래키 (캠핑장 정보 삭제 시 자동 삭제)
);
-- 객실 이미지 테이블 (객실 1개에 여러 이미지)
CREATE TABLE room_images_table (
    room_image_no bigint AUTO_INCREMENT PRIMARY KEY,       -- 이미지 고유번호
    room_image_url VARCHAR(255) NOT NULL,                   -- 이미지 URL
    room_image_sort_order INT DEFAULT 0,                    -- 이미지 정렬 순서
    room_no int NOT NULL,                             		-- 연결된 객실 고유번호
    FOREIGN KEY (room_no) REFERENCES rooms_table(room_no) ON DELETE CASCADE -- 객실 외래키 (객실 정보 삭제 시 자동 삭제)
);



-- 편의시설 목록 테이블 (전기, 와이파이, 바비큐 등)
CREATE TABLE facilities_table (
	facility_no int AUTO_INCREMENT PRIMARY KEY,       -- 시설 고유번호
    facility_name VARCHAR(100) NOT NULL               	  -- 시설 이름 (예: 전기, 온수, 와이파이 등)
);

-- 캠핑장과 편의시설을 연결해주는 테이블
CREATE TABLE campsite_facilities (
    campsite_no int NOT NULL,
    facility_no int NOT NULL,
    PRIMARY KEY (campsite_no, facility_no),
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE,
    FOREIGN KEY (facility_no) REFERENCES facilities_table(facility_no) ON DELETE CASCADE
);


-- 카테고리 테이블(환경적 요소, ex)물이 맑은, 단풍이 피는 )
CREATE TABLE category_table (
    category_no int AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- 캠핑장과 카테고리를 연결해주는 테이블
CREATE TABLE campsite_categories (
    campsite_no int NOT NULL,
    category_no int NOT NULL,
    PRIMARY KEY (campsite_no, category_no),
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE,
    FOREIGN KEY (category_no) REFERENCES category_table(category_no) ON DELETE CASCADE
);

-- 구역 타입(글램핑, 카라반)
CREATE TABLE section_types_table (
    section_type_no int AUTO_INCREMENT PRIMARY KEY,
    section_type_name VARCHAR(100) NOT NULL
);

-- 캠핑장과 구역에 구역 타입(글램핑, 카라반)을 연결해주는 테이블
CREATE TABLE campsite_section_types (
	campsite_no int NOT NULL,
    section_no int NOT NULL,
    section_type_no int NOT NULL,
    PRIMARY KEY (campsite_no, section_no, section_type_no),
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE,
    FOREIGN KEY (section_no) REFERENCES sections_table(section_no) ON DELETE CASCADE,
    FOREIGN KEY (section_type_no) REFERENCES section_types_table(section_type_no) ON DELETE CASCADE
);






DESCRIBE rooms_table;

ALTER TABLE campsites_table
MODIFY COLUMN campsite_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;