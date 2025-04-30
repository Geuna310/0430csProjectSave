CREATE DATABASE camp_database;
Use camp_database;
SHOW COLUMNS FROM suppliers_table;


CREATE TABLE suppliers_table ( -- 협력사
    supplier_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,	-- 협력사 고유 번호
    supplier_name VARCHAR(255) NOT NULL,					-- 협력사 이름
    supplier_contact_person VARCHAR(20),					-- 협력사 관리자 이름
    supplier_contact_phone VARCHAR(13),						-- 협력사 관리자 번호
    supplier_phone VARCHAR(13) NOT NULL,					-- 협력사 전화번호
    supplier_email VARCHAR(30) NOT NULL,					-- 협력사 이메일
    supplier_address VARCHAR(255),							-- 협력사 주소
    supplier_type VARCHAR(50),								-- 협력사 납품 타입(예: 식자재, 장비, 소모품 등)
    supplier_status VARCHAR(20) DEFAULT '활성',				-- 협력사 거래상태
    supplier_bank_account VARCHAR(50),						-- 협력사 계좌
    supplier_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 생성된 날짜
    supplier_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정된 날짜
);




CREATE TABLE product_table( -- 제품
   product_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
   product_name varchar(255) NOT NULL,
   product_price int NOT NULL,
   product_image varchar(200),
   product_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   product_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   supplier_id int, 
   FOREIGN KEY (supplier_id) REFERENCES suppliers_table(supplier_id) ON DELETE CASCADE
);


CREATE TABLE Warehouses_table ( -- 창고
    warehouse_id int PRIMARY KEY AUTO_INCREMENT,
    warehouse_name VARCHAR(100) NOT NULL,
    warehouses_location VARCHAR(255) NOT NULL,
	warehouses_phone varchar(13) NOT NULL,
    warehouses_email varchar(50) NOT NULL,
    warehouses_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    warehouses_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Storage_Locations_table ( -- 창고 저장 위치
    Storage_location_id int PRIMARY KEY AUTO_INCREMENT,
    warehouse_id int,  -- 어느 창고에 속하는지 (FK)
    Storage_location_code VARCHAR(20) UNIQUE,  -- 사람이 보기 쉬운 코드 (예: A0325)
    Storage_section VARCHAR(10),
    Storage_rack VARCHAR(10),
    Storage_floor VARCHAR(10),
    Storage_slot VARCHAR(10),
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses_table(warehouse_id) ON DELETE CASCADE
);

CREATE TABLE Inventory_table ( -- 저장된 물품 정보
    inventory_id int PRIMARY KEY AUTO_INCREMENT,											-- 저장된 물품 ID
    inventory_quantity int NOT NULL,  														-- 적재된 수량
	inventory_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,								-- 생성된 날짜(자동 기입)
	inventory_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	-- 수정된 날짜(자동 기입)
    product_id int,  																		-- 적재된 물건의 ID (상품 테이블과 연결)
    Storage_location_id int,  																-- 적재된 창고 위치 (Storage_Locations 테이블과 연결)
    FOREIGN KEY (product_id) REFERENCES Product_table(product_id) ON DELETE CASCADE,
    FOREIGN KEY (Storage_location_id) REFERENCES Storage_Locations_table(Storage_location_id) ON DELETE CASCADE
);


CREATE TABLE Stock_Movements_table ( -- 재고 입출력 내역
    stock_movement_id bigint PRIMARY KEY AUTO_INCREMENT,
    product_id int,
    Storage_location_id int,
    stock_movements_quantity int,
    stock_movement_type ENUM('IN', 'OUT'),  -- 입고(IN) / 출고(OUT)
    stock_movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Product_table(product_id) ON DELETE CASCADE,
    FOREIGN KEY (Storage_location_id) REFERENCES Storage_Locations_table(Storage_location_id) ON DELETE CASCADE
);

select * from suppliers_table;
select * from product_table;
select * from warehouses_table;
select * from Storage_Locations_table;
select * from Inventory_table;
select * from Stock_Movements_table;

ALTER TABLE Warehouses_table  
ADD COLUMN Warehouses_phone varchar(13) NOT NULL,
ADD COLUMN Warehouses_email varchar(30) NOT NULL;


ALTER TABLE suppliers_table  
DROP COLUMN Warehouses_email,
DROP COLUMN Warehouses_phone;



INSERT INTO suppliers_table 
(supplier_id, supplier_name, supplier_contact_person, supplier_contact_phone, supplier_phone, supplier_email, supplier_address, supplier_type, supplier_status, supplier_bank_account) 
VALUES 
(1, '캠파이어 코퍼레이션', '홍정훈', '010-1234-5678', '02-3456-7890', 'sales@campfirecorp.com', '서울특별시 강남구 캠핑로 101', '장비', '활성', '국민은행 123-45-67890'),
(2, '마운틴에코', '김지은', '010-9876-5432', '031-1234-5678', 'eco@mountaineco.com', '경기도 성남시 분당구 에코산로 77', '식자재', '활성', '신한은행 234-56-78901'),
(3, '노던캠프서플라이', '최민석', '010-2222-3333', '051-9876-5432', 'info@northerncamp.com', '부산광역시 해운대구 캠핑시장길 9', '소모품', '비활성', '하나은행 345-67-89012');

INSERT INTO suppliers_table 
(supplier_name, supplier_contact_person, supplier_contact_phone, supplier_phone, supplier_email, supplier_address, supplier_type, supplier_status, supplier_bank_account) 
VALUES 
('캠파이어 코퍼레이션', '홍정훈', '010-1234-5678', '02-3456-7890', 'sales@campfirecorp.com', '서울특별시 강남구 캠핑로 101', '장비', '활성', '국민은행 123-45-67890'),
('마운틴에코', '김지은', '010-9876-5432', '031-1234-5678', 'eco@mountaineco.com', '경기도 성남시 분당구 에코산로 77', '식자재', '활성', '신한은행 234-56-78901'),
('노던캠프서플라이', '최민석', '010-2222-3333', '051-9876-5432', 'info@northerncamp.com', '부산광역시 해운대구 캠핑시장길 9', '소모품', '비활성', '하나은행 345-67-89012');






INSERT INTO product_table 
(product_name, product_price, product_image, supplier_id)
VALUES 
('4인용 텐트', 180000, 'tent_4p.jpg', 1),
('캠핑용 테이블 세트', 95000, 'camp_table_set.jpg', 1),
('휴대용 가스버너', 35000, 'gas_burner.jpg', 3),
('고기용 캠핑 숯 5kg', 12000, 'charcoal_5kg.jpg', 2),
('아이스박스 40L', 55000, 'icebox_40l.jpg', 1),
('일회용 수저세트 (100개입)', 8000, 'spoon_set.jpg', 3),
('냉동 삼겹살 1kg', 13000, 'pork_1kg.jpg', 2);

INSERT INTO Warehouses_table (warehouse_name, Warehouses_location, Warehouses_phone, Warehouses_email)
VALUES ('평택Hub센터', '평택시', '010-1234-5678', 'pyeongtaek@naver.com'),
	   ('청주Hub센터', '청주시', '010-2345-6789', 'cheongju@naver.com'),
       ('서울 캠핑 물류창고', '서울특별시 송파구 창고길 11', '02-1111-2222', 'seoul-warehouse@campingstock.com'),
	   ('부산 해운대 창고', '부산광역시 해운대구 물류로 88', '051-2222-3333', 'busan@campingstock.com');



        
INSERT INTO storage_locations_table 
(warehouse_id, Storage_location_code, Storage_section, Storage_rack, Storage_floor, Storage_slot)
VALUES 
(1, 'A0325', 'A', '03', '2', '5'),
(1, 'B0410', 'B', '04', '1', '0'),
(2, 'C0201', 'C', '02', '0', '1');

INSERT INTO storage_locations_table 
(warehouse_id, Storage_location_code, Storage_section, Storage_rack, Storage_floor, Storage_slot)
VALUES 
(1, 'A0326', 'A', '03', '2', '6'),  
(1, 'B0411', 'B', '04', '1', '1'),  
(2, 'C0202', 'C', '02', '0', '2');  

INSERT INTO inventory_table 
(inventory_quantity, product_id, Storage_location_id)
VALUES 
(50, 1, 4),  -- 4인용 텐트
(30, 2, 4),  -- 캠핑 테이블 세트
(100, 3, 6), -- 가스버너
(200, 4, 5), -- 숯
(25, 5, 4),  -- 아이스박스
(300, 6, 6), -- 수저세트
(80, 7, 5);  -- 삼겹살

INSERT INTO stock_movements_table  
(product_id, Storage_location_id, stock_movements_quantity, stock_movement_type) 
VALUES  
(1, 4, 50, 'IN'),  -- 텐트 입고  
(2, 4, 30, 'IN'),  -- 테이블 입고  
(3, 6, 100, 'IN'), -- 가스버너 입고  
(4, 5, 200, 'IN'), -- 숯 입고  
(5, 4, 25, 'IN'),  -- 아이스박스 입고  
(6, 6, 300, 'IN'), -- 수저세트 입고  
(7, 5, 80, 'IN');  -- 삼겹살 입고  







DESCRIBE suppliers_table;

DESCRIBE warehouses_table;

delete from suppliers_table where supplier_id = 1;