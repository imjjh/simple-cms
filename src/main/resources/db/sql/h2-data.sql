-- 회원 추가 (username: admin, password: Password123!)
INSERT INTO members (username, password, nickname, created_date) VALUES ('admin', '$2a$10$UYHGC8JEjV1KQMxj7XtNXeuosTs5NCiyhsjHqWWMMfFgiJYRqAE26', '관리자', now());

-- 권한 추가
INSERT INTO member_role (member_id, role) VALUES (1, 'USER');
INSERT INTO member_role (member_id, role) VALUES (1, 'ADMIN');


-- 게시글 추가
INSERT INTO contents (title, description, view_count, created_by, created_date) VALUES ('Spring Boot 가이드', '스프링 부트 기초부터 실무까지', 10, 'admin', now());
INSERT INTO contents (title, description, view_count, created_by, created_date) VALUES ('JPA 활용법', '자바 엔티티 매핑 노하우', 5, 'admin', now());
INSERT INTO contents (title, description, view_count, created_by, created_date) VALUES ('Querydsl 검색', '동적 쿼리 작성하는 방법', 0, 'admin', now());
