@echo off
echo ========================================
echo  Course Platform - Complete Restart
echo ========================================
echo.

REM Stop all services first
echo [1/4] Stopping all running services...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do taskkill /F /PID %%a 2>nul
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081 ^| findstr LISTENING') do taskkill /F /PID %%a 2>nul
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8082 ^| findstr LISTENING') do taskkill /F /PID %%a 2>nul
timeout /t 3 /nobreak >nul
echo Services stopped.
echo.

REM Check MySQL
echo [2/4] Checking MySQL...
netstat -ano | findstr :3306 | findstr LISTENING >nul
if %errorlevel% neq 0 (
    echo ERROR: MySQL is NOT running on port 3306!
    echo Please start MySQL and run this script again.
    pause
    exit /b 1
)
echo MySQL is running.
echo.

REM Start services
echo [3/4] Starting microservices...
echo.

echo Starting User Service (port 8082)...
start "User Service - Port 8082" cmd /k "cd /d %~dp0user-service && java -jar target/user-service-1.0.0.jar"
timeout /t 15 /nobreak

echo Starting Course Service (port 8081)...
start "Course Service - Port 8081" cmd /k "cd /d %~dp0course-service && java -jar target/course-service-1.0.0.jar"
timeout /t 15 /nobreak

echo Starting API Gateway (port 8080)...
start "API Gateway - Port 8080" cmd /k "cd /d %~dp0api-gateway && java -jar target/api-gateway-1.0.0.jar"
timeout /t 10 /nobreak

echo.
echo [4/4] Verifying services...
echo.

netstat -ano | findstr :8082 | findstr LISTENING >nul
if %errorlevel% equ 0 (
    echo [OK] User Service running on port 8082
) else (
    echo [FAIL] User Service NOT running
)

netstat -ano | findstr :8081 | findstr LISTENING >nul
if %errorlevel% equ 0 (
    echo [OK] Course Service running on port 8081
) else (
    echo [FAIL] Course Service NOT running
)

netstat -ano | findstr :8080 | findstr LISTENING >nul
if %errorlevel% equ 0 (
    echo [OK] API Gateway running on port 8080
) else (
    echo [FAIL] API Gateway NOT running
)

echo.
echo ========================================
echo  All Services Started!
echo ========================================
echo.
echo Access Points:
echo   API Gateway:     http://localhost:8080
echo   User Service:    http://localhost:8082
echo   Course Service:  http://localhost:8081
echo.
echo Swagger UI:
echo   User Service:    http://localhost:8082/swagger-ui.html
echo   Course Service:  http://localhost:8081/swagger-ui.html
echo.
echo Test Registration:
echo   POST http://localhost:8080/api/users/register
echo.
pause
