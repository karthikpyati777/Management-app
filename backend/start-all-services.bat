@echo off
echo Starting Course Platform Microservices...
echo.

echo Checking if MySQL is running...
netstat -ano | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ERROR: MySQL is not running on port 3306!
    echo Please start MySQL first.
    pause
    exit /b 1
)
echo MySQL is running.
echo.

echo Starting User Service on port 8082...
start "User Service" cmd /k "cd user-service && java -jar target/user-service-1.0.0.jar"
timeout /t 10 /nobreak >nul

echo Starting Course Service on port 8081...
start "Course Service" cmd /k "cd course-service && java -jar target/course-service-1.0.0.jar"
timeout /t 10 /nobreak >nul

echo Starting API Gateway on port 8080...
start "API Gateway" cmd /k "cd api-gateway && java -jar target/api-gateway-1.0.0.jar"

echo.
echo All services are starting...
echo.
echo User Service:    http://localhost:8082
echo Course Service:  http://localhost:8081
echo API Gateway:     http://localhost:8080
echo.
echo Swagger UI:
echo User Service:    http://localhost:8082/swagger-ui.html
echo Course Service:  http://localhost:8081/swagger-ui.html
echo.
pause
