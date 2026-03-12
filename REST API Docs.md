# REST API 명세서

전체 API 명세(요청 및 응답 포맷, 에러 처리 규격 등)는 서버 실행 후 Swagger UI를 통해 확인하실 수 있습니다.
- Swagger 주소: http://localhost:8080/swagger-ui/index.html

단, Spring Security의 Form Login을 사용하는 인증 API는 Swagger 문서에 노출되지 않으므로 아래에 별도로 정리합니다.

---

## 1. 인증 API (Authentication)

세션 쿠키(`JSESSIONID`)를 발급받아야 콘텐츠 수정 및 삭제 권한을 획득할 수 있습니다.
미리 등록되어 있는 테스트 계정 정보는 다음과 같습니다.

- 테스트 계정 (USER, ADMIN 권한 모두 보유)
  - username: `admin`
  - password: `Password123!`

### 1.1 로그인
- URL: `/api/v1/auth/login`
- Method: `POST`
- Content-Type: `application/x-www-form-urlencoded`
- Request Parameters:
  - `username` (String): 아이디
  - `password` (String): 비밀번호
- Response:
  - `200 OK`: 로그인 성공 (세션 쿠키 발급)
  - `401 Unauthorized`: 로그인 실패

### 1.2 로그아웃
- URL: `/logout`
- Method: `POST` 
- Response:
  - `200 OK`: 로그아웃 성공 및 세션 만료
