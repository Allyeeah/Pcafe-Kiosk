-- 1. 카테고리 테이블
CREATE TABLE category (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (category_id)
);

-- 2. 회원(관리자,사용자) 테이블  
CREATE TABLE member (
    user_id VARCHAR(20) PRIMARY KEY,
    user_pw CHAR(4) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    is_admin CHAR(1) NOT NULL DEFAULT 'N',   -- 'Y' 관리자, 'N' 일반회원
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. 상품 테이블 
CREATE TABLE item (
    item_id INT NOT NULL AUTO_INCREMENT,
    item_code VARCHAR(15) NOT NULL UNIQUE,
    item_name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- 4. 주문 테이블 0312
CREATE TABLE orders (
order_id INT PRIMARY KEY AUTO_INCREMENT, -- 주문코드
order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 주문일자
user_id VARCHAR(30) NOT NULL, -- 주문한 사람
status VARCHAR(20) DEFAULT '주문 완료', -- 주문 상태
total_amount INT NOT NULL, -- 총구매금액
FOREIGN KEY (user_id) REFERENCES member(user_id)
); 

-- 5. 주문 상세 테이블 0311
CREATE TABLE order_detail (
    order_detail_id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,              -- 주문번호 (FK)
    item_id INT NOT NULL,               -- 상품번호 (FK)
    unit_price INT NOT NULL,            -- 단가 
    qty INT NOT NULL,                   -- 주문수량 
    PRIMARY KEY (order_detail_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id)
);

-- 6. 장바구니 테이블
CREATE TABLE cart (
    cart_id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(20) NOT NULL,
    item_id INT NOT NULL,
    qty INT NOT NULL,
    PRIMARY KEY (cart_id),
    FOREIGN KEY (user_id) REFERENCES member(user_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id)
);