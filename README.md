# schedule

# LV1

## 일정 생성 및 조회

### Method

`Post`

### Endpoint

/api/schedules

### Description

사용자의 일정을 생성할 떄 사용하는 API

### 생성 시 포함되어야 할 데이터

- 일정 제목 : title : String : NOT NULL & 100자 이내
- 일정 내용 : contents : String : NOT NULL & 500자 이내
- 작성자명 : userName : String : NOT NULL & 20자 이내
- 비밀번호 : password : String : NOT NULL & 20자 이내

### 자동으로 등록되는 데이터

-
    - 일정 고유 식별자 : schedules_id : Long
- 작성일 : postDate : String : 자동으로 등록
- 수정일 : updateDate : String : 자동으로 등록

=> 로그인 없이 일정별 작성자 이름과 비밀번호로 수정과 삭제가 가능한 구조

### Request

Pathvariable : none  
Request body

````json
{
  "title": "운동",
  "contents": "러닝 1시간",
  "userName": "Bob",
  "password": "rice"
}
````

### Response

Status Code : 200 OK  
Response body : JSON

````json
{
  "schedule_id": 1,
  "userName": "Bob",
  "title": "운동",
  "contents": "러닝 1시간",
  "updateDate": "2025-05-13T16:25"
}
````

유효성 검사 실패   
Status Code : 400 Bad Request

서버 오류나 예외 발생시  
Status Code : 500 Internal Server Error

### Method

`GET`

### Endpoint

/api/schedules/{schedules_id}

### Description

선택한 일정 단 건 정보 불러오기

### Request

Pathvariable : Long : schedules_id : 조회할 일정의 식별자
Request body : none

### Response

Status Code : 200 OK
Response body : JSON

````json
{
  "schedule_id": 1,
  "userName": "Bob",
  "title": "운동",
  "contents": "러닝 1시간",
  "updateDate": "2025-05-13T16:25"
}
````

유효하지 않은 schedules_id 입력  
Status Code : 404 Not Found

서버 오류나 예외 발생시  
Status Code : 500 Internal Server Error

### Method

`GET`

### Endpoint

/api/schedules

### Query Parameter

update : string : 수정 날짜
userName : string : 작성자명

### Description

특정 조건을 기반으로 일정 조회하기   
여러 조건을 걸 때는 &를 기반으로 동작   
아무것도 입력하지 않으면 전체를 조회      
단 여러 건의 데이터가 출력될 떄 반드시 날짜를 기준으로 내림차순으로 정렬

### Request

Pathvariable : none
Request body : none

### Query Parameter 사용 예시

/api/schedules : 전체 일정 조회   
/api/schedules?update=yyyy-mm-dd : 수정된 날짜를 기반으로 조회   
/api/schedules?userName=name : 이름을 기반으로 조회   
/api/schedules?update=yyyy-mm-dd&userName=name : 이름과 수정된 날짜를 기반으로 조회

### Response

Status Code : 200 OK
Response body : JSON

````json
{
  "schedule_id": 12,
  "userName": "Bob",
  "title": "운동",
  "contents": "러닝 1시간",
  "updateDate": "2025-05-13T16:25"
},
{
"schedule_id": 1,
"userName": "Bob",
"title": "운동",
"contents": "러닝 1시간",
"updateDate": "2025-05-11T16:25"
}
````

유효하지 않은 값을 입력  
Status Code : 404 Not Found

서버 오류나 예외 발생시  
Status Code : 500 Internal Server Error

## ERD
[![Schedule.png](docs/Schedule.png)](https://github.com/taehunim/schedule/blob/main/Schedule.png)
