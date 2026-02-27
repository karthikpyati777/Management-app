@echo off
echo Stopping all microservices...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    echo Stopping API Gateway on port 8080 (PID: %%a)
    taskkill /F /PID %%a 2>nul
)

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081') do (
    echo Stopping Course Service on port 8081 (PID: %%a)
    taskkill /F /PID %%a 2>nul
)

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8082') do (
    echo Stopping User Service on port 8082 (PID: %%a)
    taskkill /F /PID %%a 2>nul
)

timeout /t 3 /nobreak >nul
echo All services stopped.
pause
