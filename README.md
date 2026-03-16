# 🖥️ Pcafe Kiosk (PC방 매점 키오스크 시스템)

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-527FFF?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)

<br/>

## 🔗 Links
- [📄 프로젝트 기획서 및 워크스페이스 (Notion)](https://www.notion.so/springwinter/1-PCafe-Kiosk-30df9adf8bf880f286d4e8e5239a467c)
- [🎬 서비스 시연 영상 (YouTube)](#) <br/>

## 📖 Abstract
**Pcafe Kiosk**는 PC방에서 사용하는 매점 키오스크 시스템을 Java 콘솔 환경(CLI)에서 구현한 프로그램입니다. 이 시스템은 사용자(손님) 서비스와 관리자 서비스를 분리하여 매점 운영에 필요한 전반적인 기능을 제공합니다.
사용자는 직관적인 콘솔 화면을 통해 카테고리별 메뉴를 확인하고, 원하는 상품을 선택하여 주문 및 결제까지 원스톱으로 진행할 수 있습니다. 

관리자는 매점 운영의 핵심인 상품 정보를 체계적으로 관리(신규 등록, 정보 수정, 삭제)할 수 있으며, 매출 확인 기능을 통해 효율적으로 PC방 매점 데이터를 관리할 수 있습니다. 인건비를 절약하면서 복잡한 GUI 요소 없이도 빠르고 가벼운 콘솔 환경에서 필수적인 주문/결제 및 매점 관리 시스템의 흐름을 명확하게 파악할 수 있습니다.

<br/>

## ✨ Pcafe 사용자 및 관리자 기능 소개 

- **👤 사용자(손님) 모드**
  - 회원가입 및 로그인 (세션 관리)
  - 카테고리별 상품 조회 및 검색
  - 장바구니 담기 및 주문/결제 진행
  - 마이페이지 (사용자 정보 관리 -정보수정, 탈퇴)
- **⚙️ 관리자 모드**
  - 상품 카테고리 관리 - 등록, 수정, 삭제 
  - 상품 등록, 전체 조회, 상품 수정, 상품 삭제
  - 사용자 조회 (ID / 이름 / 전체검색) 
  - 전체 매출 확인 및 관리
  

<br/>

## 👨‍💻 Team Members & Roles

| 이름 | 역할 | 담당 업무 및 기여 |
| :---: | :---: | :--- |
| **이정건** | 팀장 | • 주문하기 기능 구현<br>• 관리자 매출 관리 및 마이페이지 기능 구현<br>• 프로젝트 전반의 트러블슈팅 및 주요 전반적인 에러 해결<br> •코드리팩토링 및 장바구니 담기, 장바구니 F/U |
| **김다예** | 팀원 | • 세션(Session) 관리 및 로그인 기능<br>• 사용자 정보 수정 기능<br>• 관리자 카테고리 관리 및 사용자 조회 기능  <br>•마이페이지 - 회원 탈퇴 기능구현|
| **김준서** | 팀원 | • DB ERD 설계<br>• AWS 인프라(RDS 등) 구축 및 연동<br>• Main View 화면 구현 및 발표 자료(PPT) 제작 |
| **오혜진** | 팀원 | • 데이터베이스 SQL 쿼리 작성<br>• 사용자 카테고리 조회 기능<br>• 관리자 상품 CRUD(등록/조회/수정/삭제) 구현<br>• 시스템 공통 예외 처리(Exception Handling) 로직 설계 |

<br/>

## 🛠️ Tech Stack
- **Language**: Java (JDK 17)
- **IDE**: Eclipse
- **Database**: MySQL
- **Infrastructure**: AWS RDS
- **Version Control**: Git, GitHub
- **Environment**: Console (CLI)

<br/>

## 👀 Information Architecture (IA)
![Pcafe Kiosk IA](public/ReadMe/IA.png)

<br/>

## 🗄️ Entity Relationship Diagram (ERD)
![Pcafe Kiosk ERD](https://github.com/Allyeeah/Pcafe-Kiosk/blob/Feature/39/resources/erd.PNG?raw=true)

