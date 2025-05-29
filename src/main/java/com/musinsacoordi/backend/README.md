# 프로젝트 수정 요약

## 이전 수정된 내용

1. **엔티티 파일 수정**
   - `Brand.java`: import 누락 수정, @Id 어노테이션 수정, CascadeType 수정
   - `Category.java`: import 누락 수정, 양방향 매핑 활성화
   - `Product.java`: import 누락 수정, @Id 어노테이션 수정

2. **Repository 인터페이스 생성**
   - `BrandRepository`: Brand 엔티티를 위한 JPA 리포지토리
   - `CategoryRepository`: Category 엔티티를 위한 JPA 리포지토리
   - `ProductRepository`: Product 엔티티를 위한 JPA 리포지토리

3. **에러 처리 관련 수정**
   - `ErrorCode.java`: ENTITY_NOT_FOUND 상수 추가, getFormattedMessage 메서드 수정
   - `BaseException.java`: 여러 인자를 처리할 수 있도록 수정
   - `ErrorHandler.java`: BaseException 처리 로직 수정

4. **데이터 서비스 구현**
   - `CommonDataService.java`: Repository 인터페이스 사용, 초기 데이터 삽입 로직 구현, @Transactional 추가

## 에러 발생 원인 분석

이전에 발생한 에러의 원인은 다음과 같습니다:

1. `BaseException` 클래스는 단일 값(`value`)만 저장할 수 있었으나, `ErrorCode.ENTITY_NOT_FOUND`는 두 개의 값(엔티티 이름과 ID)을 필요로 했습니다.
2. `ErrorCode.getFormattedMessage` 메서드는 단일 인자만 처리할 수 있었습니다.
3. `ErrorHandler` 클래스에서 `e.getValue()`를 호출하여 메시지를 생성했으나, 이는 여러 값을 처리할 수 없었습니다.

## 해결 방법

1. `BaseException` 클래스를 수정하여 `Object[] values`를 저장하도록 변경
2. `ErrorCode.getFormattedMessage` 메서드를 수정하여 가변 인자(`Object... values`)를 처리하도록 변경
3. `ErrorHandler` 클래스에서 `e.getMessage()`를 호출하도록 변경하여 이미 포맷된 메시지를 사용

이러한 수정을 통해 여러 값을 포함한 에러 메시지를 올바르게 처리할 수 있게 되었습니다.

## 새로 구현된 API 목록

### 1. 카테고리별 최저가격 브랜드와 가격 조회 및 총액 계산 API
- **엔드포인트**: GET /api/products/query/lowest-price-by-category
- **설명**: 각 카테고리별로 최저가격 브랜드와 가격을 조회하고 총액을 계산합니다.
- **응답**: 각 카테고리의 최저가격 브랜드, 가격 정보와 총액

### 2. 단일 브랜드로 전체 카테고리 상품 구매 시 최저가격 브랜드 조회 API
- **엔드포인트**: GET /api/products/query/lowest-price-brand
- **설명**: 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액을 조회합니다.
- **응답**: 최저가격 브랜드, 해당 브랜드의 모든 상품 정보, 총액

### 3. 특정 카테고리 가격 범위 조회 API
- **엔드포인트**: GET /api/products/query/price-range/category/{categoryId}
- **설명**: 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회합니다.
- **응답**: 카테고리 정보, 최저가격 브랜드 정보, 최고가격 브랜드 정보

### 4. 카테고리별 최저가격 브랜드와 가격 조회 및 총액 계산 API (한글 응답)
- **엔드포인트**: GET /api/products/query/lowest-price-by-category/korean
- **설명**: 각 카테고리별로 최저가격 브랜드와 가격을 조회하고 총액을 계산합니다. 응답은 한글 형식으로 제공됩니다.
- **응답**: 최저가 브랜드, 카테고리별 최저가격 정보(쉼표 포함), 총액(쉼표 포함)
- **응답 예시**:
```json
{
  "최저가": {
    "브랜드": "D",
    "카테고리": [
      {"카테고리": "상의", "가격": "10,100"},
      {"카테고리": "아우터", "가격": "5,100"},
      {"카테고리": "바지", "가격": "3,000"},
      {"카테고리": "스니커즈", "가격": "9,500"},
      {"카테고리": "가방", "가격": "2,500"},
      {"카테고리": "모자", "가격": "1,500"},
      {"카테고리": "양말", "가격": "2,400"},
      {"카테고리": "액세서리", "가격": "2,000"}
    ],
    "총액": "36,100"
  }
}
```

## 구현 계획 (한글)

1. 도메인 모델 분석
   - 브랜드, 카테고리, 상품 엔티티 구조 파악
   - 리포지토리 인터페이스 및 메소드 이해
   - 기존 컨트롤러 및 서비스 확인

2. 새로운 DTO 구현
   - CategoryLowestPriceDto: 카테고리별 최저가격 브랜드 정보
   - LowestPriceSummaryDto: 카테고리별 최저가격 요약 및 총액
   - BrandLowestTotalPriceDto: 브랜드별 총액 정보
   - CategoryPriceRangeDto: 카테고리 가격 범위 정보
   - KoreanLowestPriceDto: 한글 형식의 최저가격 정보

3. 서비스 메소드 구현
   - 카테고리별 최저가격 브랜드 조회 기능
   - 단일 브랜드로 전체 카테고리 구매 시 최저가격 브랜드 조회 기능
   - 특정 카테고리의 최저/최고가격 브랜드 조회 기능
   - 한글 형식의 응답을 위한 변환 기능

4. 컨트롤러 엔드포인트 구현
   - REST API 엔드포인트 구현
   - Swagger 문서화
   - 한글 응답 형식 API 엔드포인트 추가

5. 테스트 구현
   - 서비스 메소드 단위 테스트
   - 엔드포인트 통합 테스트
   - 한글 응답 형식 검증
