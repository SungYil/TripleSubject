# triple-subject

### 프로젝트 개요
> Place에 대한 리뷰를 관리하고 리뷰 작성에 따른 유저 포인트를 관리하는 서버 개발.

------
### 사용 언어 및 라이브러리
JAVA 11

SPRING BOOT 2.7.0

Mapstruct 1.4.1

lombok 0.2.0

JPA 2.7.0

MySQL 8.0.29

gradle 7.4.1

-------
### API 문서

Method|HTTP request|Description
---|---|---|
GET   | /points            | 전체유저 point 조회
GET   | /users/{userId}/points  | userId와 일치하는 point 조회
POST  | /events            | action : ADD 리뷰 추가
POST  | /events   | action : MOD 리뷰 수정
POST  | /events | action : DELETE 리뷰 삭제
--------------------------------

##### GET   /points    전체유저 point 조회

###### Responses

Code 200 조회 성공

Example Value
```
   {
    "userUuid" : "string",
    "point" : 0
   }
```

------------------

##### GET   /points/{userUuid}    userUuid와 일치하는 point조회

###### Responses

Code 200 조회 성공

Example Value
```
   {
    "userUuid" : "string",
    "point" : 0
   }
```

Code 404 조회 실패

Example Value
```
   {
    "message" : "일치하는 User가 없습니다"
   }
```

Code 400 조회 실패
Example Value
```
   {
    "message" : "userId 형식이 맞지 않습니다."
   }
```
-----------------
##### POST  /events  action : ADD 리뷰 추가

###### Responses

Code 200 등록 성공

```
{
 "reviewId" : "string",
 "content" : "string",
 "attachedPhotoIds" : ["string"],
 "userId" : "string",
 "placeId" : "string"
}
```

Code 400 등록 실패
```
   {
    "message" : "userId 형식이 유효하지 않습니다"
     or "placeId 형식이 유효하지 않습니다." 
    or "attachedPhotoIds 형식이 유효하지 않습니다" 
   }
```

Code 409 등록 실패
```
   {
    "message" : "해당 장소에 대한 리뷰를 이미 작성했습니다."
   }
```
-----------------
##### POST  /events  action : MOD 리뷰 수정

###### Responses

Code 200 수정 성공

```
{
 "reviewId" : "string",
 "content" : "string",
 "attachedPhotoIds" : ["string"],
 "userId" : "string",
 "placeId" : "string"
}
```

Code 400 수정 실패
```
   {
    "message" : "userId 형식이 유효하지 않습니다" 
    or "placeId 형식이 유효하지 않습니다." 
    or "attachedPhotoIds 형식이 유효하지 않습니다"
   }
```

Code 404 수정 실패
```
   {
    "message" : "일치하는 userId가 없습니다." 
    or "일치하는 reviewId가 없습니다." 
    or "일치하는 placeId가 없습니다."
   }
```

-----------------

##### POST  /events  action : DELETE 리뷰 삭제

###### Responses

Code 200 삭제 성공

Code 400 삭제 실패
```
   {
    "message" : "userId 형식이 유효하지 않습니다" 
    or "placeId 형식이 유효하지 않습니다." 
    or "attachedPhotoIds 형식이 유효하지 않습니다"
    or "리뷰작성자가 아닙니다"
   }
```

Code 404 삭제 실패
```
   {
    "message" : "일치하는 userId가 없습니다." 
    or "일치하는 reviewId가 없습니다." 
    or "일치하는 placeId가 없습니다."
   }
```
