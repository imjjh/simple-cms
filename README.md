# Contents Management System (CMS) REST API

## 1. 프로젝트 실행 방법 (How to Run)
- **요구 환경**: Java 25, Spring Boot 4
- **서버 실행**
  - Mac/Linux: `./gradlew bootRun`
  - Windows: `gradlew.bat bootRun`
- **테스트 실행**
  - Mac/Linux: `./gradlew test`
  - Windows: `gradlew.bat test`
- 서버 기동 시 `h2-schema.sql`, `h2-data.sql`에 의해 H2 인메모리 DB가 초기화되며 더미 데이터가 적재됩니다.

## 2. API 문서 및 인증 방식
- **API 문서**: `REST API Docs.md` 참고
- **인증 방식**: Spring Security Form Login 적용

## 3. 개발 환경 및 도구
### 3.1 IDE & Tech Stack
- IDE: IntelliJ IDEA, Antigravity
- Database: H2 Database

### 3.2 AI 도구 활용 내역 (GPT, Gemini, Claude)
- **Architecture & Review**: 패키지 구조 구상 및 리뷰 피드백
- **Refactoring & Debugging**: 로직 파악 보조 및 디버깅
- **Scaffolding**: 테스트용 더미 데이터 SQL 생성

## 4. 주요 기능 (Features)
### 4.1 콘텐츠 관리 (CRUD)
- 콘텐츠 작성, 수정, 삭제, 단건 조회
- 콘텐츠 목록 조회 (QueryDSL 동적 검색 및 페이징 적용)
- 조회수 카운트 기능

### 4.2 보안 및 권한 제어
- **인증 (Authentication)**: 사용자 로그인 제어
- **인가 (Authorization)**
  - 작성자 본인만 게시글 수정 및 삭제 가능
  - 관리자(ADMIN)는 모든 게시글 제어 가능