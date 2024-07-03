# artvu_be
 아트뷰 백엔드 저장소

### https://artvu.co.kr/ (모바일 권장)

**공연 중 혹은 공연 예정인 연극들을 소개하고 나에게 맞는 연극을 추천해주는 공연 큐레이팅 플랫폼.** 

- 공연 정보 출처 : 공연예술 통합전산망 ([https://www.kopis.or.kr](https://www.kopis.or.kr/por/main/main.do))
- 화면 : Next.js / 소스관리 : Gitlab / CICD : Jenkins

### 사용 기술
**Language** : java        
**Framework** : Spring Boot (API 서버), Spring Batch (배치 서버), Spring Webflux (WebClient), Spring Security (Security)        
**DB** : MariaDB, JPA, QueryDSL          
**Authorization** : OAuth2.0 + JWT         

### 배치 소스
https://github.com/maiorem/artvu-batch

### ERD
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FplWpN%2FbtsIhR0OHZ3%2Fws9VVrkYJ2sXy6t6sJ9jF0%2Fimg.png" />

### 공연 정보 서비스 제공 흐름
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FY6jLg%2FbtsIg7wyUqF%2F5RbnSvaMGxAoKzTomH6iBK%2Fimg.png" />

## 개발 진행 중에 발생한 문제 상황과 해결 과정

### 메인페이지 진입 시 데이터 출력 속도

**문제상황**

- 초기 설계단계에서 일부 관리자 입력값을 제외한 KOPIS 정보는 KOPIS 오픈API를 그대로 활용하여 로그인 유저가 활용하는 정보만 저장하도록 기획되었으나 해당 API를 실시간으로 활용하고 메인페이지의 큐레이팅 된 테마를 감당하기에는 속도가 지나치게 느림.
- 이후 메인 페이지 진입 인원이 늘어날 수록 프론트 서버에 부담이 커질 것으로 판단됨.

**해결**

- Spring Batch 도입을 제안하고 이를 실현하기 위해 Spring Batch 5버전을 학습하고 적용하여 매일 자정에 KOPIS로부터 데이터를 받아 DB에 저장하고 날짜가 지난 공연에 대해 공연종료 처리를 하도록 함.
- 메인 페이지 테마의 복잡성을 JPA만으로 구현한 속도 또한 만족스럽지 않아 QueryDSL을 도입하고 쿼리를 튜닝하여 239의 쿼리 수와 속도를 46으로 단축함.

**[모니터링 툴 : Scouter]**          

- **/api/performs/theme 기준 1회 요청당 239 쿼리 count 발생. Time 111~145**        

![아트뷰_테마_쿼리수정 전.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcJhNnb%2FbtsIiwu1zGB%2Fg0vQGyaCJc08mCB2KWONGk%2Fimg.png)

![아트뷰_테마_쿼리수정 전_ElapsedTime.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FrxqNk%2FbtsIjdPmCB3%2F7QoUO8dQIrz4ZoGK5oPJfk%2Fimg.png)

**QueryDSL 도입 후 + 테이블 Join으로 쿼리 개선**     

- **/api/performs/theme 기준 1회 요청 109 쿼리 count 로 개선 / Time 46으로 개선**

![아트뷰_테마_쿼리수정 후.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbCmdLV%2FbtsIg2oGCUn%2Fq6mqhEe3rnQTy3BZGKYKj1%2Fimg.png)

![아트뷰_테마_쿼리수정 후_ElapsedTime.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4MrYx%2FbtsIg0RU2Db%2FTGunAZD20WkR0Qyi9hcLt1%2Fimg.png)

**개선점**

- Redis를 도입하여 메인 테마에 변경이 있을 때 캐시 데이터로 저장하면 페이지 진입 성능이 더욱 향상될 것으로 기대되어 논의 중에 있음.

### 신규 도입한 툴과 기존 모듈간의 충돌

**문제상황**

- 로컬에서 개발 테스트 시 문제 없었던 WebClient가 서버에 올라가면 오류를 일으켜 외부 API를 활용하지 못하는 일이 발생. 원인을 보니 모니터링 툴로 서버에 설치한 scouter와 버전 충돌을 일으키고 있었음. scouter 오픈 소스를 체크하니 해당 문제에 대한 이슈가 올라와 있었으나 아직 패치되지 않은 것으로 확인됨.
- 카카오 로그인 인증 과정에서 쿠키로 리프레시 토큰을 브라우저에 제공하는 방식으로 프론트엔드와 합의 했으나 개발 서버와 운영 서버의 도메인이 달라 쿠키 도메인을 개발과 운영 따로 적용해야 하는 문제가 발생함. 공통 서브도메인으로 개발 운영 동시적용하고 싶었으나 Cookie 클래스는 “.도메인” 방식 적용이 불가한 상황으로 확인됨.

**해결**

- 우선은 변경해도 전체 서비스 흐름에 큰 문제가 없는 모듈을 변경함. 외부 API 호출에 WebClient 대신 RestTemplete을 사용하도록 하고 Cookie 대신 “.도메인” 방식을 지원하는 ResponseCookie를 사용함.
- 다만 이번 문제는 새로운 툴을 도입하는 과정에서 기존 모듈과의 충돌을 잘 알아보지 않았다는 지점을 반성하고 이후에는 기술 사이의 충돌과 버전 문제에 대해 잘 알아보는 습관을 가지도록 유의했다.



### API 제공 목록

[공연정보 (리스트/상세)](https://zany-duke-0f6.notion.site/1d7b0d9f09b743caad61364a19ed31d8?v=291e93579b46474383ffe26b2c39d328&pvs=4)

[마이페이지](https://zany-duke-0f6.notion.site/25b9b6a2ff6c41fc9ce836205a49b51f?v=0c4cee0210b540148f9fce5563ca3304&pvs=4)
