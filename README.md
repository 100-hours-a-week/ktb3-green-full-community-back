# 🆚 양자택일 커뮤니티 서비스 Hmmm..

> **갈팡질팡 고민되는 양자택일, Hmmm.. 에서 함께 고민하자!**

- _오늘 저녁으로 치킨? 피자?_ **사소한 질문**부터, <br>
- _100% 확률로 10억 받기 vs 50% 확률로 20억 받기_ 흥미진진한 **밸런스 게임**, <br>
- _제가 개발자를 꿈꾸고 있는데요... 어떤 방향성이 좋을까요?_ **진지한 고민**까지! <br>

둘 중 하나를 골라야 하는 어떤 질문이라도 전부 대환영! <br>
서로의 생각을 자유롭게 나눌 수 있는 양자택일 커뮤니티 서비스입니다 👋

<img width="300" height="300" alt="image" src="https://github.com/user-attachments/assets/1b338015-e590-4271-8154-244a73570810" />

▲ 프로젝트 메인 로고

<br>

## 🔨 기술 스택
**Frontend** <br>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">

**Backend** <br>
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white">
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">


**Database / Infra**  <br>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

**Tools** <br>
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">

<br>

## 🙋🏻‍♀️ 참여 인원
FE/BE 모두 한 명이 담당하여 진행한 개인 프로젝트입니다.
| 유지연 |
|:---:|
| <img src="https://github.com/user-attachments/assets/705cb654-b61d-4853-8075-f70710a38e72" width="180" /> |
| <a href="https://github.com/jiyeonyooo" target="_blank">@jiyeonyooo</a> |
<br>


## 🔥 ERD 설계
<img width="1650" height="732" alt="image" src="https://github.com/user-attachments/assets/63321a5e-4b7f-49ff-b572-4b5ff0fcfaab" />


<br>

<br>

| **테이블** | **관계** | **연관관계 매핑** | **FK** | **연관관계 주인** |
| --- | --- | --- | --- | --- |
| users ↔ posts | 1:N | 단방향 @ManyToOne | posts.user_id | posts |
| users ↔ comments | 1:N | 단방향 @ManyToOne | comments.user_id | comments |
| users ↔ picks | 1:N | 단방향 @ManyToOne | picks.user_id | picks |
| users ↔ profile_image | 1:1 | 단방향 `@OneToOne` | `users.image_id` | users |
| posts ↔ post_meta | 1:1 | 단방향 @OneToOne | post_meta.post_id | post_meta |
| posts ↔ comments | 1:N | 단방향 @ManyToOne | comments.post_id | comments |
| posts ↔ picks | 1:N | 단방향 @ManyToOne | picks.post_id | picks |

<br>

## 📺 기능 소개
### 1. 회원가입 / 로그인

### 2. 게시글 기능

### 3. 댓글 기능

### 4. 사용자 정보 변경 기능
