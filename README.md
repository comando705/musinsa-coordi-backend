# 무신사 코디 백엔드 서비스

## 1. 구현 범위

### 1.1 주요 기능
- Spring Boot 3.2.3 기반의 RESTful API 서버
- JPA를 활용한 데이터베이스 연동
- OpenAPI(Swagger)를 통한 API 문서화
- 데이터 유효성 검증 (Validation)
- Thymeleaf 템플릿 엔진 지원

### 1.2 기술 스택
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot DevTools
- OpenAPI 3.0 (Swagger)

### 1.3 프로젝트 구조
```
com.musinsacoordi.backend
├── domain/           # 도메인 계층
│   ├── brand/        # 브랜드 도메인
│   │   ├── dto/      
│   │   │   ├── BrandRequestDto.java    # 브랜드 요청 DTO
│   │   │   └── BrandResponseDto.java   # 브랜드 응답 DTO
│   │   ├── error/    # 도메인별 에러 처리
│   │   ├── validation/# 유효성 검증
│   │   ├── Brand.java          # 브랜드 엔티티
│   │   ├── BrandController.java # 브랜드 API 컨트롤러
│   │   ├── BrandService.java    # 브랜드 비즈니스 로직
│   │   └── BrandRepository.java # 브랜드 데이터 접근
│   ├── product/      # 상품 도메인
│   └── category/     # 카테고리 도메인
├── web/              # 웹 페이지 컨트롤러
│   └── WebController.java
├── common/           # 공통 모듈
├── config/           # 설정 클래스
└── MusinsaCoordiBackendApplication.java
```

각 패키지 및 클래스 설명:

1. **domain/**: 도메인 중심 패키지
   - 각 도메인별로 독립적인 패키지 구성
   - 도메인 패키지 구조 예시 (brand 도메인):
     - `dto/`: 요청/응답 데이터 객체
     - `error/`: 도메인별 예외 처리
     - `validation/`: 입력값 검증
     - `Entity.java`: 도메인 엔티티
     - `Controller.java`: REST API 엔드포인트
     - `Service.java`: 비즈니스 로직
     - `Repository.java`: 데이터 접근 계층

2. **web/**: 웹 페이지 라우팅
   - `WebController.java`: 웹 페이지 요청 처리

3. **common/**: 공통 기능
   - 공통 유틸리티
   - 공통 예외 처리
   - 공통 응답 형식

4. **config/**: 애플리케이션 설정
   - Spring Boot 설정 클래스

5. **MusinsaCoordiBackendApplication.java**: 스프링 부트 메인 클래스

## 2. 빌드 및 실행 방법

### 2.1 개발 환경 요구사항
- JDK 17 이상
- Gradle 8.x

### 2.2 프로젝트 빌드
```bash
# 프로젝트 빌드
./gradlew build

# 테스트 스킵하고 빌드하기
./gradlew build -x test
```

### 2.3 애플리케이션 실행
```bash
# 실행
./gradlew bootRun
```

### 2.4 테스트 실행
```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스만 실행
./gradlew test --tests "testClassName"
```

## 3. 추가 정보

### 3.1 API 문서 / web url
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI 명세: `http://localhost:8080/v3/api-docs`
- web : `http://localhost:8080/brand`

### 3.2 데이터베이스
- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `comando705`
- Password: `123qwe!@#`

### 3.3 개발 가이드
- 코드 스타일: Google Java Style Guide 준수
- 브랜치 전략: Git Flow
- 커밋 메시지: Conventional Commits

### 3.4 참고 사항
- 개발 환경에서는 Spring Boot DevTools가 활성화되어 있어 코드 변경 시 자동으로 재시작됩니다.
- API 문서는 Swagger UI를 통해 실시간으로 확인 및 테스트가 가능합니다.
- 데이터베이스는 기본적으로 인메모리 H2를 사용하므로, 애플리케이션 재시작 시 데이터가 초기화됩니다.

### 3.5 상세 패키지 구조
```
com.musinsacoordi.backend
├── domain/           # 도메인 계층
│   ├── brand/        # 브랜드 도메인
│   │   ├── dto/      
│   │   │   ├── BrandRequestDto.java    # 브랜드 요청 DTO
│   │   │   └── BrandResponseDto.java   # 브랜드 응답 DTO
│   │   ├── error/    # 도메인별 에러 처리
│   │   ├── validation/# 유효성 검증
│   │   ├── Brand.java          # 브랜드 엔티티
│   │   ├── BrandController.java # 브랜드 API 컨트롤러
│   │   ├── BrandService.java    # 브랜드 비즈니스 로직
│   │   └── BrandRepository.java # 브랜드 데이터 접근
│   ├── product/      # 상품 도메인
│   └── category/     # 카테고리 도메인
├── web/              # 웹 페이지 컨트롤러
│   └── WebController.java
├── common/           # 공통 모듈
├── config/           # 설정 클래스
└── MusinsaCoordiBackendApplication.java
```

각 패키지 및 클래스 설명:

1. **domain/**: 도메인 중심 패키지
   - 각 도메인별로 독립적인 패키지 구성
   - 도메인 패키지 구조 예시 (brand 도메인):
     - `dto/`: 요청/응답 데이터 객체
     - `error/`: 도메인별 예외 처리
     - `validation/`: 입력값 검증
     - `Entity.java`: 도메인 엔티티
     - `Controller.java`: REST API 엔드포인트
     - `Service.java`: 비즈니스 로직
     - `Repository.java`: 데이터 접근 계층

2. **web/**: 웹 페이지 라우팅
   - `WebController.java`: 웹 페이지 요청 처리

3. **common/**: 공통 기능
   - 공통 유틸리티
   - 공통 예외 처리
   - 공통 응답 형식

4. **config/**: 애플리케이션 설정
   - Spring Boot 설정 클래스

5. **MusinsaCoordiBackendApplication.java**: 스프링 부트 메인 클래스

### 3.6 공통 파일 구성
1. **BaseEntity**: 모든 엔티티가 상속받는 기본 클래스
   - 생성일시, 수정일시, 생성자, 수정자 등 공통 감사 필드 포함
   - `@EntityListeners(AuditingEntityListener.class)` 적용

2. **공통 응답 형식**
   ```java
   public class ApiResponse<T> {
       private String code;      // 응답 코드
       private String message;   // 응답 메시지
       private T data;          // 응답 데이터
       private LocalDateTime timestamp;  // 응답 시간
   }
   ```

### 3.7 에러 처리 가이드
1. **에러 코드 정의**
   ```java
   public enum ErrorCode {
       // Common
       INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
       INTERNAL_SERVER_ERROR(500, "C002", "Server Error"),
       
       // Business
       ENTITY_NOT_FOUND(404, "B001", "Entity Not Found"),
       DUPLICATE_ENTITY(409, "B002", "Entity Already Exists");
       
       private final int status;
       private final String code;
       private final String message;
   }
   ```

2. **예외 처리 계층**
   - `BusinessException`: 비즈니스 로직 예외의 최상위 클래스
   - `EntityNotFoundException`: 엔티티 조회 실패 시 발생
   - `InvalidValueException`: 유효하지 않은 입력값 처리

3. **전역 예외 처리**
   - `@RestControllerAdvice`를 사용한 일관된 에러 응답 처리
   - 모든 예외는 `ErrorResponse` 형식으로 변환되어 반환
   - 비즈니스 예외와 시스템 예외를 구분하여 처리