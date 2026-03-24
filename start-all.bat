@echo off

echo ==========================================
echo Starting Kafka (KRaft Mode)...
echo ==========================================

REM ------------------------------------------
REM Navigate to Kafka installation directory
REM ------------------------------------------
cd /d C:\kafka

REM Start Kafka server in a new window (KRaft mode)
start cmd /k bin\windows\kafka-server-start.bat config\kraft\server.properties

echo Waiting for Kafka to initialize (30 seconds)...
timeout /t 30

REM ------------------------------------------
REM Load Environment Variables (from .env file)
REM ------------------------------------------
cd /d H:\Java\0-STS-432\fitness-microservices

echo Loading environment variables from .env...

REM IMPORTANT:
REM This approach correctly reads full lines and avoids breaking values
REM containing '=' or '&' (like DB_URL)

for /f "usebackq delims=" %%i in (`type .env ^| findstr /v "^#"`) do (
    set "%%i"
)

REM ------------------------------------------
REM Debug check (uncomment if needed)
REM ------------------------------------------
REM echo DB_URL=%DB_URL%
REM echo DB_USERNAME=%DB_USERNAME%

echo ==========================================
echo Starting Configuration Server...
echo ==========================================
cd configuration-server

REM Config Server must start first (provides centralized config)
start cmd /k mvn spring-boot:run

echo Waiting for Config Server...
timeout /t 20

echo ==========================================
echo Starting Eureka Server...
echo ==========================================
cd ../eureka-server

REM Eureka depends on Config Server
start cmd /k mvn spring-boot:run

echo Waiting for Eureka Server...
timeout /t 20

echo ==========================================
echo Starting User Service...
echo ==========================================
cd ../user-service

REM Depends on Config Server + Eureka + DB env variables
start cmd /k mvn spring-boot:run

echo ==========================================
echo Starting Activity Service...
echo ==========================================
cd ../activity-service

REM Depends on Config Server + Eureka + Mongo env variables
start cmd /k mvn spring-boot:run

echo ==========================================
echo Starting AI Service...
echo ==========================================
cd ../ai-service

REM Depends on Config Server + Eureka + Mongo + Gemini env variables
start cmd /k mvn spring-boot:run

echo ==========================================
echo All services started successfully
echo ==========================================

REM ------------------------------------------
REM Notes:
REM ------------------------------------------
REM 1. Ensure .env file exists in:
REM    H:\Java\0-STS-432\fitness-microservices
REM
REM 2. Required variables:
REM    DB_URL, DB_USERNAME, DB_PASSWORD
REM    MONGO_USERNAME, MONGO_PASSWORD, MONGO_CLUSTER, MONGO_DATABASE
REM    EUREKA_SERVER_URL
REM    GEMINI_URL, GEMINI_KEY
REM
REM 3. Do NOT commit .env to Git (add to .gitignore)
REM
REM 4. Each service runs in a separate window for easier debugging
REM ------------------------------------------