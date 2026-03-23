@echo off

echo Starting Configuration Server...
cd configuration-server
start cmd /k mvn spring-boot:run

timeout /t 10

echo Starting Eureka Server...
cd ../eureka-server
start cmd /k mvn spring-boot:run

timeout /t 10

echo Starting User Service...
cd ../user-service
start cmd /k mvn spring-boot:run

echo Starting Activity Service...
cd ../activity-service
start cmd /k mvn spring-boot:run

echo Starting AI Service...
cd ../ai-service
start cmd /k mvn spring-boot:run

echo All services started