-- 카테고리 데이터 삽입 (key 정보들을 카테고리로 등록)
INSERT INTO category (name, create_date) VALUES ('상의', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('아우터', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('바지', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('스니커즈', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('가방', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('모자', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('양말', CURRENT_TIMESTAMP);
INSERT INTO category (name, create_date) VALUES ('액세서리', CURRENT_TIMESTAMP);

-- 브랜드 데이터 삽입 (A부터 I까지)
INSERT INTO brand (name, create_date) VALUES ('A', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('B', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('C', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('D', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('E', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('F', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('G', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('H', CURRENT_TIMESTAMP);
INSERT INTO brand (name, create_date) VALUES ('I', CURRENT_TIMESTAMP);

-- 상품 데이터 삽입 (브랜드와 카테고리 참조, 가격정보 포함)
-- A 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '상의'),
    11200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '바지'),
    4200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '모자'),
    1700,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '아우터'),
    5500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '가방'),
    2000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '양말'),
    1800,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'A'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2300,
    CURRENT_TIMESTAMP
);

-- B 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '상의'),
    10500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '바지'),
    3800,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '가방'),
    2100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '아우터'),
    5900,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '모자'),
    2000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '양말'),
    2000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'B'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2200,
    CURRENT_TIMESTAMP
);

-- C 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '상의'),
    10000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '바지'),
    3300,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '아우터'),
    6200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '가방'),
    2200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '모자'),
    1900,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '양말'),
    2200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'C'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2100,
    CURRENT_TIMESTAMP
);

-- D 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '상의'),
    10100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '바지'),
    3000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '아우터'),
    5100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '가방'),
    2500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '모자'),
    1500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '양말'),
    2400,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'D'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2000,
    CURRENT_TIMESTAMP
);

-- E 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '상의'),
    10700,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '바지'),
    3800,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '아우터'),
    5000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9900,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '가방'),
    2300,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '모자'),
    1800,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '양말'),
    2100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'E'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2100,
    CURRENT_TIMESTAMP
);

-- F 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'F'),
           (SELECT id FROM category WHERE name = '상의'),
           11200,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'F'),
           (SELECT id FROM category WHERE name = '바지'),
           4000,
           CURRENT_TIMESTAMP
       );


INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '아우터'),
    7200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9300,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '가방'),
    2100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '모자'),
    1600,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '양말'),
    2300,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'F'),
    (SELECT id FROM category WHERE name = '액세서리'),
    1900,
    CURRENT_TIMESTAMP
);


-- G 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'G'),
           (SELECT id FROM category WHERE name = '상의'),
           10500,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'G'),
           (SELECT id FROM category WHERE name = '바지'),
           3900,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'G'),
           (SELECT id FROM category WHERE name = '아우터'),
           5800,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'G'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
    (SELECT id FROM brand WHERE name = 'G'),
    (SELECT id FROM category WHERE name = '가방'),
    2200,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'G'),
    (SELECT id FROM category WHERE name = '모자'),
    1700,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'G'),
    (SELECT id FROM category WHERE name = '양말'),
    2100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'G'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2000,
    CURRENT_TIMESTAMP
);

-- H 브랜드 제품

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '상의'),
    10800,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '바지'),
    3100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '아우터'),
    6300,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'H'),
           (SELECT id FROM category WHERE name = '스니커즈'),
           9700,
           CURRENT_TIMESTAMP
       );


INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '가방'),
    2100,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '모자'),
    1600,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '양말'),
    2000,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'H'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2000,
    CURRENT_TIMESTAMP
);

-- I 브랜드 제품
INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'I'),
           (SELECT id FROM category WHERE name = '상의'),
           11400,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'I'),
           (SELECT id FROM category WHERE name = '바지'),
           3200,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
           (SELECT id FROM brand WHERE name = 'I'),
           (SELECT id FROM category WHERE name = '아우터'),
           6700,
           CURRENT_TIMESTAMP
       );

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'I'),
    (SELECT id FROM category WHERE name = '스니커즈'),
    9500,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date)
VALUES (
    (SELECT id FROM brand WHERE name = 'I'),
    (SELECT id FROM category WHERE name = '가방'),
    2400,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'I'),
    (SELECT id FROM category WHERE name = '모자'),
    1700,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'I'),
    (SELECT id FROM category WHERE name = '양말'),
    1700,
    CURRENT_TIMESTAMP
);

INSERT INTO product (brand_id, category_id, price, create_date) 
VALUES (
    (SELECT id FROM brand WHERE name = 'I'),
    (SELECT id FROM category WHERE name = '액세서리'),
    2400,
    CURRENT_TIMESTAMP
);