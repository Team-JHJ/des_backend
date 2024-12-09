# 1. Base Image: OpenJDK 17
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 필수 패키지 설치 (lsof, fuser 포함)
RUN apt-get update && \
    apt-get install -y lsof psmisc && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. Gradle Wrapper 파일 복사 (Gradle 설치)
COPY gradlew /app/gradlew
COPY gradle /app/gradle

# 5. 전체 프로젝트 복사
COPY . /app

# 6. Gradle 빌드 수행 (컨테이너 내부에서 빌드)
RUN chmod +x gradlew && ./gradlew clean build -x test

# 7. 빌드된 JAR 파일만 추출
RUN mkdir -p /app/dist && cp /app/build/libs/*.jar /app/dist/app.jar

# 8. 포트 노출
EXPOSE 8080

# 9. JAR 실행
CMD ["java", "-jar", "/app/dist/app.jar"]
