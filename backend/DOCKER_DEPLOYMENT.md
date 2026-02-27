# Docker Deployment Guide - Course Platform Microservices

## Prerequisites
- Docker Desktop installed and running
- Docker Compose installed (included with Docker Desktop)

---

## Quick Start

### 1. Build and Run All Services
```bash
cd c:\course-platform\backend
docker-compose up --build
```

### 2. Run in Detached Mode (Background)
```bash
docker-compose up -d --build
```

### 3. Stop All Services
```bash
docker-compose down
```

### 4. Stop and Remove Volumes (Clean Start)
```bash
docker-compose down -v
```

---

## Individual Service Commands

### Build Individual Service
```bash
# User Service
docker build -t user-service:latest ./user-service

# Course Service
docker build -t course-service:latest ./course-service

# API Gateway
docker build -t api-gateway:latest ./api-gateway
```

### Run Individual Service
```bash
# User Service
docker run -p 8082:8082 user-service:latest

# Course Service
docker run -p 8081:8081 course-service:latest

# API Gateway
docker run -p 8080:8080 api-gateway:latest
```

---

## Docker Compose Commands

### View Running Containers
```bash
docker-compose ps
```

### View Logs
```bash
# All services
docker-compose logs

# Specific service
docker-compose logs user-service
docker-compose logs course-service
docker-compose logs api-gateway
docker-compose logs mysql

# Follow logs (real-time)
docker-compose logs -f
```

### Restart Services
```bash
# All services
docker-compose restart

# Specific service
docker-compose restart user-service
```

### Stop Services (without removing)
```bash
docker-compose stop
```

### Start Stopped Services
```bash
docker-compose start
```

---

## Service URLs (After Docker Deployment)

| Service | URL | Port |
|---------|-----|------|
| API Gateway | http://localhost:8080 | 8080 |
| User Service | http://localhost:8082 | 8082 |
| Course Service | http://localhost:8081 | 8081 |
| MySQL | localhost:3306 | 3306 |

### Swagger UI
- User Service: http://localhost:8082/swagger-ui.html
- Course Service: http://localhost:8081/swagger-ui.html

---

## Testing After Deployment

### 1. Check Service Health
```bash
# Check if all containers are running
docker-compose ps

# Expected output:
# NAME                STATUS              PORTS
# api-gateway         Up                  0.0.0.0:8080->8080/tcp
# course-service      Up                  0.0.0.0:8081->8081/tcp
# user-service        Up                  0.0.0.0:8082->8082/tcp
# mysql               Up (healthy)        0.0.0.0:3306->3306/tcp
```

### 2. Test Registration
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "role": "STUDENT"
  }'
```

### 3. Test Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

---

## Multi-Stage Docker Build Explanation

### Stage 1: Build (Maven)
```dockerfile
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
```
- Uses Maven image to build the application
- Copies source code and builds JAR file
- Skips tests for faster builds

### Stage 2: Runtime (JRE)
```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
- Uses lightweight JRE image (smaller size)
- Copies only the JAR file from build stage
- Exposes service port
- Runs the application

### Benefits:
- ✅ Smaller final image size (only JRE, not full JDK + Maven)
- ✅ Faster deployment
- ✅ More secure (fewer tools in production image)
- ✅ Consistent builds

---

## Docker Compose Architecture

```
┌─────────────────────────────────────────┐
│         API Gateway (8080)              │
│    Routes requests to services          │
└──────────────┬──────────────────────────┘
               │
       ┌───────┴────────┐
       │                │
┌──────▼──────┐  ┌─────▼────────┐
│ User Service│  │Course Service│
│   (8082)    │  │    (8081)    │
└──────┬──────┘  └──────┬───────┘
       │                │
       └────────┬───────┘
                │
         ┌──────▼──────┐
         │    MySQL    │
         │   (3306)    │
         └─────────────┘
```

---

## Troubleshooting

### Issue 1: Port Already in Use
```bash
# Stop local services first
taskkill /F /IM java.exe

# Or change ports in docker-compose.yml
ports:
  - "8090:8080"  # Use 8090 instead of 8080
```

### Issue 2: MySQL Connection Failed
```bash
# Check MySQL container logs
docker-compose logs mysql

# Restart MySQL
docker-compose restart mysql
```

### Issue 3: Service Not Starting
```bash
# View service logs
docker-compose logs user-service

# Rebuild specific service
docker-compose up -d --build user-service
```

### Issue 4: Clean Everything and Restart
```bash
# Stop and remove everything
docker-compose down -v

# Remove all images
docker-compose down --rmi all

# Rebuild from scratch
docker-compose up --build
```

---

## Production Considerations

### 1. Environment Variables
Create `.env` file:
```env
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_DATABASE=coursedb
SPRING_PROFILES_ACTIVE=prod
```

### 2. Health Checks
Already configured in docker-compose.yml for MySQL.

### 3. Resource Limits
Add to docker-compose.yml:
```yaml
services:
  user-service:
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
```

### 4. Logging
```yaml
services:
  user-service:
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
```

---

## Image Sizes (Approximate)

| Service | Build Stage | Runtime Stage | Savings |
|---------|-------------|---------------|---------|
| User Service | ~500MB | ~200MB | 60% |
| Course Service | ~500MB | ~200MB | 60% |
| API Gateway | ~450MB | ~180MB | 60% |

---

## Quick Commands Reference

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Rebuild and restart
docker-compose up -d --build

# Check status
docker-compose ps

# Access MySQL
docker exec -it course-platform-mysql mysql -uroot -p1ki17cs026

# Remove everything
docker-compose down -v --rmi all
```

---

## Next Steps

1. Run `docker-compose up --build`
2. Wait for all services to start (~2-3 minutes)
3. Test endpoints at http://localhost:8080
4. View Swagger UI at http://localhost:8082/swagger-ui.html
5. Monitor logs with `docker-compose logs -f`
