DROP DATABASE IF EXISTS camp_database;

create database camp_database;
Use camp_database;

INSERT INTO facilities_table (facility_name) VALUES
('전기'), ('와이파이'), ('바비큐'), ('샤워장'), ('개수대'), ('온수'),('마트');

INSERT INTO section_types_table (section_type_name) VALUES
('카라반'), ('글램핑'), ('캠프닉');

INSERT INTO category_table (category_name) VALUES
('물맑은'),('단풍'), ('산'), ('바다'), ('벚꽃'), ('낚시'),('감성');

INSERT INTO member_table (member_no, member_id, member_password, member_name, member_nickname, member_phone, member_email, member_birth, member_gender, member_image_url, is_business)
VALUES
(1,'user01', 'password123', '홍길동', '길동이', '010-1234-5678', 'user01@example.com', '1990-05-15', '남', 'https://example.com/image1.jpg', 1),
(2,'user02', 'securepass456', '김철수', '철수짱', '010-2345-6789', 'user02@example.com', '1988-08-22', '남', 'https://example.com/image2.jpg', 1),
(3,'user03', 'mypassword789', '박영희', '영희님', '010-3456-7890', 'user03@example.com', '1992-12-10', '여', 'https://example.com/image3.jpg', 1);

INSERT INTO campsites_table (campsite_no, campsite_name, campsite_location, campsite_description, campsite_business_number,campsite_checkIn, campsite_checkOut, member_no)
VALUES 
(1, '산속 캠핑장', '강원도 홍천군', '자연 속에서 힐링할 수 있는 캠핑장입니다.', '123-45-67890','14:00:00', '11:00:00', 1),
(2, '바닷가 캠핑장', '부산광역시 해운대구', '해변과 가까운 캠핑장으로, 바다를 보며 캠핑할 수 있습니다.', '987-65-43210','14:00:00', '11:00:00', 2),
(3, '숲속 캠핑장', '경기도 가평군', '푸른 숲속에서 바베큐와 함께 즐길 수 있는 캠핑장입니다.', '112-22-33445','14:00:00', '11:00:00', 3),
(4, '산악 캠핑장', '경기도 가평군 청평면', '도시와 매연과 함께 힐링할 수 있는 캠핑장입니다.', '122-22-33445','14:00:00', '11:00:00', 1),
(5, '뷰튀풀 캠핑장', '강원도 강릉시 사천면', '산을 보며 서핑을 즐길 수 있는 장소입니다.', '132-22-33445','14:00:00', '11:00:00', 2);


INSERT INTO sections_table (section_no, section_name, section_description, section_map, campsite_no)
VALUES 
(1, '숲속 구역', '자연과 조화를 이루는 숲속 구역입니다.', 'http://example.com/section_map1.jpg', 1),
(2, '바닷가 구역', '바다를 바라보며 캠핑을 즐길 수 있는 구역입니다.', 'http://example.com/section_map2.jpg', 2),
(3, '산악 구역', '산속에서 캠핑과 트레킹을 동시에 즐길 수 있는 구역입니다.', 'http://example.com/section_map3.jpg', 3),
(4, '호수 구역', '호수와 가까워 물놀이와 캠핑을 동시에 즐길 수 있는 구역입니다.', 'http://example.com/section_map4.jpg', 4),
(5, '대나무 숲 구역', '대나무 숲 속에서 편안한 시간을 보낼 수 있는 구역입니다.', 'http://example.com/section_map5.jpg', 5);

INSERT INTO rooms_table (room_name, room_price, room_peak_price, room_capacity, section_no)
VALUES 
('숲속 객실 1', 50000, 70000, 4, 1),
('바닷가 객실 1', 60000, 85000, 5, 2),
('산악 객실 1', 45000, 65000, 3, 3),
('호수 객실 1', 55000, 75000, 4, 4),
('대나무 숲 객실 1', 48000, 68000, 2, 5);

INSERT INTO room_images_table (room_image_url, room_image_sort_order, room_no)
VALUES 
('http://example.com/room_image1.jpg', 0, 1),
('http://example.com/room_image2.jpg', 1, 1),
('http://example.com/room_image3.jpg', 0, 2),
('http://example.com/room_image4.jpg', 1, 2),
('http://example.com/room_image5.jpg', 0, 3);





INSERT INTO campsite_facilities (campsite_no, facility_no)
VALUES 
(1, 1), (1, 2), 
(2, 2), (2, 3),
(3, 3), (3, 4),
(4, 5), (4, 6),
(5, 6), (5, 7);






INSERT INTO campsite_categories (campsite_no, category_no)
VALUES 
(1, 1), (1, 3), 
(2, 2), (2, 4),
(3, 1), (3, 3), 
(4, 5), (4, 4), 
(5, 7), (5, 6);

INSERT INTO campsite_section_types (campsite_no, section_no, section_type_no)
VALUES (1, 1, 1), (2, 1, 1), (3, 1, 2), (4, 1, 3), (5, 1, 3);


-- 리뷰 더미 데이터
INSERT INTO reviews_table (review_no, review_rating, review_text, member_no, campsite_no, review_favorite)
VALUES
(1, 5, '정말 멋진 캠핑장이었어요! 자연 속에서 힐링했습니다.', 1, 1, 3),
(2, 4, '시설도 괜찮고 화장실도 깨끗했어요.', 2, 2, 5),
(3, 3, '경치 좋지만 벌레가 많았어요.', 3, 3, 1),
(4, 2, '조금 소란스럽고 자리 간격이 좁아요.', 4, 1, 0);

-- 리뷰 이미지 더미 데이터
INSERT INTO review_images_table (review_images_url, review_images_sort_order, review_no)
VALUES
('https://example.com/images/review1_1.jpg', 1, 1),
('https://example.com/images/review2_1.jpg', 1, 2),
('https://example.com/images/review3_1.jpg', 1, 3),
('https://example.com/images/review3_2.jpg', 2, 3);











-- 삭제용
SET foreign_key_checks = 0;

-- 캠핑장과 구역에 구역 타입(글램핑, 카라반)을 연결해주는 테이블 삭제
DROP TABLE IF EXISTS campsite_section_types;

-- 캠핑장과 카테고리를 연결해주는 테이블 삭제
DROP TABLE IF EXISTS campsite_categories;

-- 카테고리 테이블 삭제
DROP TABLE IF EXISTS category_table;

-- 캠핑장과 편의시설을 연결해주는 테이블 삭제
DROP TABLE IF EXISTS campsite_facilities;

-- 편의시설 목록 테이블 삭제
DROP TABLE IF EXISTS facilities_table;

-- 객실 이미지 테이블 삭제
DROP TABLE IF EXISTS room_images_table;

-- 객실 정보 테이블 삭제
DROP TABLE IF EXISTS rooms_table;

-- 캠핑장 구역 테이블 삭제
DROP TABLE IF EXISTS sections_table;

-- 캠핑장 이미지 테이블 삭제
DROP TABLE IF EXISTS campsites_images_table;

-- 캠핑장 정보 테이블 삭제
DROP TABLE IF EXISTS campsites_table;

-- 구역 타입(글램핑, 카라반) 테이블 삭제
DROP TABLE IF EXISTS section_types_table;


DROP TABLE member_table;
DROP TABLE admin_table;

-- 답변글 테이블 삭제
DROP TABLE IF EXISTS answers_table;

-- 문의글 테이블 삭제
DROP TABLE IF EXISTS inquiries_table;

-- 대댓글 테이블 삭제
DROP TABLE IF EXISTS review_comments_table;

-- 리뷰 이미지 테이블 삭제
DROP TABLE IF EXISTS review_images_table;

-- 리뷰 정보 테이블 삭제
DROP TABLE IF EXISTS reviews_table;

-- 결제 정보 테이블 삭제
DROP TABLE IF EXISTS payments;

-- 예약 정보 테이블 삭제
DROP TABLE IF EXISTS reservations;

DROP TABLE Stock_Movements_table;
DROP TABLE Inventory_table;
DROP TABLE Storage_Locations_table;
DROP TABLE Product_table;
DROP TABLE Warehouses_table;
DROP TABLE Suppliers_table;

show tables;

SET foreign_key_checks = 1;