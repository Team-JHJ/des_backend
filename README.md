# 분산에너지 모니터링 서비스 DES

## 프로젝트 소개
이 프로젝트는 분산에너지의 구성 요소인 DER, HomeLoad, Inverter, SmarterMeter, VPP와 같은 자원들을 통합적으로 관리하고 효율적으로 데이터를 제공하기 위한 시스템입니다. 관리자는 해당 시스템을 이용하여 더 쉽게 자원 및 기기의 상태를 파악, 관리하여 효과적인 자원관리 계획을 수립할 수 있습니다.

## 주요 기능
### 데이터 관리
- 다양한 분산에너지 데이터 구성 관리
- 각 자원별 상태를 관리자가 쉽게 볼 수 있도록 제공하여 효율적인 상태관리를 도움
- 기기의 결함 유무를 표시하여 효율적인 결함 관리를 도움

### API 제공
- RESTful API를 통해 데이터를 카테고리별 조회 가능
- 정적 및 동적 데이터 모두 처리하는 기능 제공
- 주요 엔드 포인트
  - **GET**: /api/{category}/resources
  - **PUT**: /api/{category}
  - **POST**: /api/{category}
  - **DELETE**: /api/{category}

### 계층적 응답 구조
```
{
  "category": [
    {
      "name": "smartMeter",
      "description": "실시간으로 에너지 소비량을 측정하고 전송하는 계량기"
    }
  ],
  "columns": [
    {
      "id": 1,
      "isFault": false,
      "name": "smartmeter1",
      "details": [
        {
          "name": "installationDate",
          "description": "설치 날짜",
          "value": "2022-05-01",
          "typeof": "String"
        },
        {
          "name": "realtimeMonitoring",
          "description": "실시간 모니터링 여부",
          "value": true,
          "typeof": "Boolean"
        }
      ]
    }
  ]
}
```

## 기술 스택
- **프레임워크**: Spring Boot, React
- **데이터베이스**: MySQL
- **컨테이너화**: Docker
- **배포 환경**: AWS EC2

### 시스템 아키텍쳐
<img width="737" alt="des아키텍쳐" src="https://github.com/user-attachments/assets/2649e6f5-2fc8-4ae8-96e9-80e583a1f039" />

## 시연 영상
https://github.com/user-attachments/assets/cd1c27ef-0695-4077-90ed-5f1842e0c508
