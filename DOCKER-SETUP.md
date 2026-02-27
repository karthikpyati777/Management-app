# Management App - Docker Compose Setup

Complete microservices application with Spring Boot backend and Angular frontend.

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose installed
- Ports available: 3306, 4200, 8080, 8081, 8082

### One Command Setup

```bash
git clone https://github.com/karthikpyati777/Management-app.git
cd Management-app
docker compose up --build
```

Wait 3-5 minutes for all services to build and start.

## 🌐 Access Application

- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:8080
- **User Service**: http://localhost:8082
- **Course Service**: http://localhost:8081
- **MySQL**: localhost:3306

## 📋 Services

| Service | Port | Description |
|---------|------|-------------|
| Frontend | 4200 | Angular UI |
| API Gateway | 8080 | Spring Cloud Gateway |
| User Service | 8082 | User management |
| Course Service | 8081 | Course management |
| MySQL | 3306 | Database |

## 🔧 Docker Commands

### Start All Services
```bash
docker compose up -d
```

### Stop All Services
```bash
docker compose down
```

### View Logs
```bash
docker compose logs -f
```

### Rebuild Services
```bash
docker compose up --build
```

### Check Status
```bash
docker compose ps
```

### Restart Specific Service
```bash
docker compose restart frontend
docker compose restart api-gateway
```

## 🏗️ Project Structure

```
Management-app/
├── backend/
│   ├── user-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   ├── course-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   └── api-gateway/
│       ├── src/
│       ├── pom.xml
│       └── Dockerfile
├── frontend/
│   ├── src/
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
└── docker-compose.yml
```

## 🔐 Default Credentials

- Username: `admin`
- Password: `admin123`

## 🐳 Dockerfile Locations

Each service has its own Dockerfile:
- `backend/user-service/Dockerfile`
- `backend/course-service/Dockerfile`
- `backend/api-gateway/Dockerfile`
- `frontend/Dockerfile`

## 🌍 AWS/Cloud Deployment

### Open These Ports in Security Group:
- 4200 (Frontend)
- 8080 (API Gateway)
- 8081 (Course Service)
- 8082 (User Service)
- 3306 (MySQL - optional)

### Access on Cloud:
```
http://YOUR_SERVER_IP:4200
```

## 🔍 Troubleshooting

### Services Not Starting
```bash
docker compose down -v
docker compose up --build
```

### Check Logs
```bash
docker compose logs mysql
docker compose logs user-service
docker compose logs course-service
docker compose logs api-gateway
docker compose logs frontend
```

### Port Already in Use
```bash
# Stop existing containers
docker compose down

# Check what's using the port
netstat -tulpn | grep 4200
```

### Database Connection Issues
```bash
# Wait for MySQL to be healthy
docker compose ps

# Restart dependent services
docker compose restart user-service course-service
```

## 📊 Health Checks

Check if services are running:
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8081/actuator/health
```

## 🛠️ Development

### Local Development (Without Docker)

**Backend:**
```bash
cd backend/user-service
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm start
```

## 📝 Environment Variables

Modify in `docker-compose.yml`:

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/course_platform
  SPRING_DATASOURCE_USERNAME: root
  SPRING_DATASOURCE_PASSWORD: rootpassword
```

## 🎯 Features

- ✅ Microservices Architecture
- ✅ Spring Boot 3.x
- ✅ Angular 17
- ✅ MySQL 8.0
- ✅ Docker Compose
- ✅ Spring Cloud Gateway
- ✅ RESTful APIs
- ✅ Responsive UI

## 📚 API Endpoints

### User Service (8082)
- GET `/api/users` - List users
- POST `/api/users` - Create user
- GET `/api/users/{id}` - Get user
- PUT `/api/users/{id}` - Update user
- DELETE `/api/users/{id}` - Delete user

### Course Service (8081)
- GET `/api/courses` - List courses
- POST `/api/courses` - Create course
- GET `/api/courses/{id}` - Get course
- PUT `/api/courses/{id}` - Update course
- DELETE `/api/courses/{id}` - Delete course

### API Gateway (8080)
Routes all `/api/**` requests to appropriate services.

## 🚀 Production Deployment

1. Clone repository
2. Update environment variables
3. Run `docker compose up -d`
4. Configure reverse proxy (Nginx/Apache)
5. Set up SSL certificates
6. Configure firewall rules

## 📞 Support

For issues, check:
- Docker logs: `docker compose logs`
- Service status: `docker compose ps`
- GitHub Issues: https://github.com/karthikpyati777/Management-app/issues

## 📄 License

MIT License

---

**Ready to run with one command**: `docker compose up --build` 🎉
