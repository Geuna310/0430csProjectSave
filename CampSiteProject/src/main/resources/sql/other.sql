Use camp_database;


-- 예약 정보 테이블
CREATE TABLE reservations (
    reservation_no bigINT AUTO_INCREMENT PRIMARY KEY,    			-- 예약 고유번호
    reservation_check_in_date DATE NOT NULL,                       	-- 체크인 날짜
    reservation_check_out_date DATE NOT NULL,                      	-- 체크아웃 날짜
    reservation_total_price INT NOT NULL,                         	-- 총 예약 금액
    reservation_status VARCHAR(50) DEFAULT '예약완료',               	-- 예약 상태 (예약완료, 완료, 취소 등)
    reservation_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     	-- 예약 생성일
    member_no INT NOT NULL,                             			-- 예약자 고유번호 (회원)
    room_no INT NOT NULL,                             				-- 예약한 객실 고유번호
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE,   -- 사용자(예약자) 외래키
    FOREIGN KEY (room_no) REFERENCES rooms_table(room_no)       	-- 객실 외래키
);


INSERT INTO reservations (
    reservation_check_in_date, 
    reservation_check_out_date, 
    reservation_total_price, 
    reservation_status, 
    reservation_created_at, 
    member_no, 
    room_no
) VALUES 
('2025-05-01', '2025-05-03', 200000, '예약완료', '2025-04-15 10:23:45', 5, 22);


-- 결제 정보 테이블
CREATE TABLE payments (
    payment_no bigINT AUTO_INCREMENT PRIMARY KEY,       -- 결제 고유번호
    reservation_no bigINT NOT NULL,                     	 -- 연결된 예약 고유번호
    member_no INT NOT NULL,                          	 -- 결제한 회원 고유번호
    payment_amount INT NOT NULL,                         -- 결제 금액
    payment_method VARCHAR(50),                          -- 결제 수단 (카드, 카카오페이 등)
    payment_status VARCHAR(50) DEFAULT '결제완료',       	 -- 결제 상태 (결제완료, 결제실패, 환불요청, 환불완료)
    payment_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,       -- 결제 일시
    payment_receipt_url VARCHAR(255),                    -- 결제 영수증 or 인증 URL (결제 API를 사용하면 결제하고 난 후의 인증 URL을 줌)
    
    FOREIGN KEY (reservation_no) REFERENCES reservations(reservation_no) ON DELETE CASCADE,
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE
);


-- 리뷰 정보 테이블
CREATE TABLE reviews_table (
    review_no bigINT AUTO_INCREMENT PRIMARY KEY,         		  -- 리뷰 고유번호
    review_rating TINYINT CHECK (review_rating BETWEEN 1 AND 5),  -- 평점 (1~5)
    review_text TEXT,                                      		  -- 리뷰 내용
    review_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  		  -- 작성일
	member_no INT NOT NULL,                             	  	  -- 작성자 고유번호
    campsite_no INT NOT NULL,                         		  	  -- 리뷰 대상 캠핑장 고유번호
    review_favorite MEDIUMINT NOT NULL default 0,						  -- 리뷰 좋아요
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE,     	-- 사용자 외래키
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE 	-- 캠핑장 외래키 (캠핑장 정보 삭제 시 자동 삭제)
);
-- 리뷰 이미지 테이블
CREATE TABLE review_images_table (
	review_images_no bigINT AUTO_INCREMENT PRIMARY KEY, 		  -- 리뷰 이미지 고유번호
    review_images_url VARCHAR(255) NOT NULL,  					  -- 이미지 url
    review_images_sort_order INT DEFAULT 0, 					  -- 이미지 정렬 순서
    review_no bigint NOT NULL,									  -- 리뷰 고유번호
    foreign key (review_no) references reviews_table(review_no) ON DELETE CASCADE -- 리뷰 외래키
);


CREATE TABLE review_comments_table (
    member_no INT NOT NULL,                           				-- 작성자 고유번호
    campsite_no INT NOT NULL,                         				-- 리뷰 대상 캠핑장 고유번호
    review_no bigINT NOT NULL,							  			-- 리뷰 고유번호
    
    review_comment_no INT AUTO_INCREMENT PRIMARY KEY,   			-- 대댓글 고유번호
    review_comment_text TEXT,                               			-- 대댓글 내용
    review_comment_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  		-- 대댓글 작성일
    
    parent_comment_no INT DEFAULT NULL,								-- 상위 댓글 고유번호 (NULL = 최상위 댓글)
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE,     						-- 사용자 외래키
    FOREIGN KEY (review_no) REFERENCES reviews_table(review_no) ON DELETE CASCADE,     	-- 리뷰 외래키
    FOREIGN KEY (campsite_no) REFERENCES campsites_table(campsite_no) ON DELETE CASCADE, 	-- 캠핑장 외래키
    FOREIGN KEY (parent_comment_no) REFERENCES review_comments_table(review_comment_no)
);


CREATE TABLE inquiries_table (
    inquiry_id bigINT AUTO_INCREMENT PRIMARY KEY,			-- 문의글 고유번호
    member_no INT NOT NULL,             					-- 회원 고유번호(외래키)
    inquiry_title VARCHAR(200) NOT NULL,					-- 문의글 제목
    inquiry_content TEXT NOT NULL,							-- 문의글 작성 내용
    inquiry_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 	-- 문의글 작성 날짜
    is_answered BOOLEAN DEFAULT FALSE,                      -- 답변 완료
    FOREIGN KEY (member_no) REFERENCES member_table(member_no) ON DELETE CASCADE
);

CREATE TABLE answers_table (
    answer_id bigINT AUTO_INCREMENT PRIMARY KEY,				-- 답변글 고유번호
    inquiry_id bigINT NOT NULL,             				-- 문의글 고유번호(외래키)
    admin_no INT NOT NULL,               					-- 관리자 계정 외래키
    answer_content TEXT NOT NULL,							-- 답변글 작성 내용
    answer_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 답변글 작성 날짜
    UNIQUE (inquiry_id),   									-- 문의글당 답변 하나만 가능
    FOREIGN KEY (inquiry_id) REFERENCES inquiries_table(inquiry_id) ON DELETE CASCADE, -- 문의글 삭제시 답변글도 자동 삭제
    FOREIGN KEY (admin_no) REFERENCES admin_table(admin_no)
);