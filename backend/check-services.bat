@echo off
echo Checking Microservices Status...
echo.

echo MySQL (port 3306):
netstat -ano | findstr :3306
if %errorlevel% neq 0 echo NOT RUNNING
echo.

echo Course Service (port 8081):
netstat -ano | findstr :8081
if %errorlevel% neq 0 echo NOT RUNNING
echo.

echo User Service (port 8082):
netstat -ano | findstr :8082
if %errorlevel% neq 0 echo NOT RUNNING
echo.

echo API Gateway (port 8080):
netstat -ano | findstr :8080
if %errorlevel% neq 0 echo NOT RUNNING
echo.

pause
