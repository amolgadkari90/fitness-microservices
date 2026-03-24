@echo off

echo ==========================================
echo Stopping all services...
echo ==========================================

REM Kill all Java processes (Spring Boot + Kafka)
taskkill /F /IM java.exe

REM Optional: Kill Kafka console windows (if needed)
taskkill /F /IM cmd.exe /FI "WINDOWTITLE eq kafka-server-start*"

echo ==========================================
echo All services stopped
echo ==========================================