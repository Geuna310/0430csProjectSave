-- dummydate 의 내용을 일부 수정해서 사용

show databases;

Use camp_database;
show tables;

select * from member_table;

select * from campsites_table;
select * from sections_table;
select * from rooms_table;

select * from facilities_table;
select * from section_types_table;
select * from category_table;

select * from campsite_facilities;
select * from campsite_categories;
select * from  campsite_section_types;

INSERT INTO facilities_table (facility_name) VALUES
('전기'), ('와이파이'), ('바비큐'), ('샤워장'), ('개수대'), ('온수'),('마트');

INSERT INTO section_types_table (section_type_name) VALUES
('카라반'), ('글램핑'), ('캠프닉'), ('펜션');

INSERT INTO category_table (category_name) VALUES
('물맑은'),('단풍'), ('산'), ('바다'), ('벚꽃'), ('낚시'),('감성');



INSERT INTO campsites_table (campsite_no, campsite_name, campsite_location, campsite_description, campsite_business_number,campsite_checkIn, campsite_checkOut, member_no)
VALUES 
(1, '산속 캠핑장', '강원도 홍천군', '자연 속에서 힐링할 수 있는 캠핑장입니다.', '123-45-67890','14:00:00', '11:00:00', 1),
(2, '바닷가 캠핑장', '부산광역시 해운대구', '해변과 가까운 캠핑장으로, 바다를 보며 캠핑할 수 있습니다.', '987-65-43210','14:00:00', '11:00:00', 2),
(3, '숲속 캠핑장', '경기도 가평군', '푸른 숲속에서 바베큐와 함께 즐길 수 있는 캠핑장입니다.', '112-22-33445','14:00:00', '11:00:00', 3),
(4, '산악 캠핑장', '경기도 가평군 청평면', '도시와 매연과 함께 힐링할 수 있는 캠핑장입니다.', '122-22-33445','14:00:00', '11:00:00', 1),
(5, '뷰튀풀 캠핑장', '강원도 강릉시 사천면', '산을 보며 서핑을 즐길 수 있는 장소입니다.', '132-22-33445','14:00:00', '11:00:00', 2),
(6, '벚꽃 펜션', '강원도 양양군', '벚꽃이 만개한 펜션입니다.', '123-43-4321','15:00:00', '11:00:00', 3);


INSERT INTO sections_table (section_no, section_name, section_description, section_map, campsite_no)
VALUES 
(1, '숲속 구역', '자연과 조화를 이루는 숲속 구역입니다.', 'http://example.com/section_map1.jpg', 1),
(2, '바닷가 구역', '바다를 바라보며 캠핑을 즐길 수 있는 구역입니다.', 'http://example.com/section_map2.jpg', 2),
(3, '산악 구역', '산속에서 캠핑과 트레킹을 동시에 즐길 수 있는 구역입니다.', 'http://example.com/section_map3.jpg', 3),
(4, '호수 구역', '호수와 가까워 물놀이와 캠핑을 동시에 즐길 수 있는 구역입니다.', 'http://example.com/section_map4.jpg', 4),
(5, '대나무 숲 구역', '대나무 숲 속에서 편안한 시간을 보낼 수 있는 구역입니다.', 'http://example.com/section_map5.jpg', 5),
(6, '벚나무 구역', '봄이 되면 벚꽃이 만개하는 구역입니다.', 'http://example.com/section_map6.jpg', 6);

INSERT INTO rooms_table (room_name, room_price, room_peak_price, room_capacity, room_quantity, section_no, campsite_no )
VALUES 
# 수용인원 2의 배수 통일 / 남은 방 개수 3개로 통일 / 구역&캠핑장 번호는 방 번호와 일치
('숲속 객실 1', 50000, 70000, 2, 3, 1, 1),
('바닷가 객실 1', 60000, 85000, 4, 3, 2, 2),
('산악 객실 1', 45000, 65000, 6, 3, 3, 3),
('호수 객실 1', 55000, 75000, 8, 3, 4, 4),
('대나무 숲 객실 1', 48000, 68000, 10, 3, 5, 5),
('벚꽃 객실 1', 30000, 20000, 12, 3, 6, 6);

INSERT INTO campsite_facilities (campsite_no, facility_no)
VALUES 
-- 1 - 전기, 와이파이
-- 2 - 바비큐, 샤워장 
-- 3 - 바비큐, 개수대, 온수
-- 4 - 전기, 와이파이, 온수, 마트
-- 5 - 샤워장, 개수대
-- 6 - 온수, 마트 
(1, 1), (1, 2), 
(2, 3), (2, 4),
(3, 3), (3, 5), (3, 6),
(4, 1), (4, 2), (4, 6), (4, 7),
(5, 4), (5, 5),
(6, 6), (6, 7);

INSERT INTO campsite_categories (campsite_no, category_no)
VALUES 
-- 1 - 산
-- 2 - 바다, 낚시
-- 3 - 산, 단풍, 벚꽃
-- 4 - 산
-- 5 - 산, 바다, 물맑은, 감성 
-- 6 - 벚꽃 
(1, 3),
(2, 4), (2, 6),
(3, 3), (3, 2), (3, 5), 
(4, 3),
(5, 3), (5, 4), (5, 1), (5, 7),
(6, 5);


INSERT INTO campsite_section_types (campsite_no, section_no, section_type_no)
VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3), (4, 4, 1), (5, 5, 2), (6, 6, 4);


####################################################################################


