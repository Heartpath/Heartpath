# 0. Version

## Android

| Product | Version |
| :-----: | :-----: |
| Android |  8.1.0  |
| kotiln  | 1.8.10  |
|  hilt   |  2.45   |

## Backend

|      Product      |  Version  |
| :---------------: | :-------: |
| Intellij Ultimate | 2023.2.1  |
|       Java        | 11.0.20.1 |
|    Spring Boot    |  2.7.17   |
|       Redis       |   5.0.7   |
|       MySQL       |  8.0.33   |
|      MongoDB      |   5.0.0   |
|      Jenkins      |  2.414.3  |

## 외부 API

|    Product    | Version |
| :-----------: | :-----: |
| Firebase FCM  | 32.5.0  |
|  Kakao Login  | 2.14.0  |
| Google ARCore |    -    |

# 1. Config

# 2. Architecture

![architecture](./images/Architecture.png)

|     Server     | Description |
| :------------: | :---------: |
| Member-Service | 사용자 관리 |
| Letter-Service |  편지 관리  |
| Store-Service  |  상점 관리  |

### `마음길` 서비스의 서버는 위 그림과 같이 MSA 구조로, 총 세 개의 서버가 운영되고 있습니다.

# 1. Build

> 각 서버는 `AWS EC2`를 이용해 배포했습니다. <br>
> 각 서버의 인스턴스 유형은 `t2.medium`입니다.

### 각 서버의 Build 버전 및 사양은 다음과 같습니다.

|  Build  |          Version           |
| :-----: | :------------------------: |
|   OS    | Linux 6.2.0-1015-aws amd64 |
| OpenJDK |         11.0.20.1          |
| Gradle  |            8.3             |
| Groovy  |           3.0.17           |
| Tomcat  |           9.0.82           |
|   Ant   |          1.10.13           |

### Spring Boot 빌드 방법

```shell
#!/bin/bash

# 프로젝트 경로를 변수로 지정
PROJECT_PATH="/your/actual/project/path"

# 프로젝트 경로로 이동
cd $PROJECT_PATH

# Gradle 기반 스크립트에 실행 권한을 부여
chmod +x gradlew

# 프로젝트 빌드
./gradlew clean build

# 빌드된 디렉토리로 이동
cd ./build/libs

# 실행
# --jasypt.encryptor.password=복호화키
# 표준 출력을 output.log 파일로 리다이렉션하고,
# 표준 오류를 error.log 파일로 리다이렉션
nohup java -jar '빌드된 Jar Name' \
--jasypt.encryptor.password=zootopia \
1 > $PROJECT_PATH/output.log 2 > $PROJECT_PATH/error.log %
```

# 2. Deploy

### AWS Deploy Stacks

> 사용한 **AWS 기술 스택**은 다음과 같습니다.

|           AWS Stack            | Description |
| :----------------------------: | :---------: |
|          AWS **EC2**           |             |
|          AWS **RDS**           |             |
|           AWS **S3**           |             |
|       AWS **DocumentDB**       |             |
|        AWS **Route53**         |             |
|   AWS **Cerificate Manager**   |             |
| AWS **Elastic Load Balancing** |             |
|       AWS **CloudWatch**       |             |

### 배포 환경

![deploy_architecture](./images/Deploy_Architecture.png)

Request URL: `https://heartpath/`

|   EndPoint   |     Server     |
| :----------: | :------------: |
|  /user/\*\*  |  User-Service  |
| /letter/\*\* | Letter-Service |
| /store/\*\*  | Store-Service  |
|  otherwise   |   404 Error    |

# 3.
