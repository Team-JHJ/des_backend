# 1. Base Image: OpenJDK 17
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 필수 패키지 설치 (lsof, fuser 포함)
RUN apt-get update && \
    apt-get install -y lsof psmisc && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. 전체 프로젝트 복사
COPY . /app

# 5. 포트 노출
EXPOSE 8080

# 6. Gradle 빌드 수행 (컨테이너 내부에서 빌드)
RUN ./gradlew clean build -x test

# 7. JAR 실행
CMD ["java", "-jar", "/app/build/libs/des_backend-1.0-SNAPSHOT.jar"]
