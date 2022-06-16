# 스마트 근태관리
> 사원의 출/퇴근을 기록하여 사원별 근태를 관리하는 시스템

## 📁 디렉토리 구조

| 디렉토리       | 설명                  |
|------------|---------------------| 
| config     | config 디렉토리           |
| constants  | status code 디렉토리    |
| controller | 컨트롤러 디렉토리           |
| model      | DTO(각 계층이 데이터를 주고 받을 때 사용하는 객체), DAO(데이터베이스에 접속하여 비즈니스 로직 실행에 필요한 쿼리를 호출) 디렉토리      |
|service| 비즈니스 로직을 수행하는 파일의 디렉토리 |
|mybatis| DB에 질의할 쿼리문(mapper.xml)과 mybatis config file 디렉토리 |
