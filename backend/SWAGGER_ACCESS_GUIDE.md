# Swagger Documentation Access Guide

## Prerequisites
Ensure all microservices are running:
- User Service on port 8082
- Course Service on port 8081
- API Gateway on port 8080

---

## Swagger UI URLs (Direct Access)

### 1. User Service Swagger
**URL**: http://localhost:8082/swagger-ui.html

**Alternative URL**: http://localhost:8082/swagger-ui/index.html

**API Docs JSON**: http://localhost:8082/api-docs

**Endpoints Available**:
- POST /api/users - Create user
- GET /api/users - Get all users
- GET /api/users/{id} - Get user by ID
- PUT /api/users/{id} - Update user
- DELETE /api/users/{id} - Delete user
- GET /api/users/{id}/courses - Get user's courses

---

### 2. Course Service Swagger
**URL**: http://localhost:8081/swagger-ui.html

**Alternative URL**: http://localhost:8081/swagger-ui/index.html

**API Docs JSON**: http://localhost:8081/api-docs

**Endpoints Available**:

**Course Management**:
- POST /api/courses - Create course
- GET /api/courses - Get all courses
- GET /api/courses/{id} - Get course by ID
- PUT /api/courses/{id} - Update course
- DELETE /api/courses/{id} - Delete course
- GET /api/courses/instructor/{instructorId} - Get courses by instructor

**Enrollment Management**:
- POST /api/enrollments - Enroll user in course
- GET /api/enrollments/user/{userId} - Get user enrollments
- GET /api/enrollments/course/{courseId} - Get course enrollments
- DELETE /api/enrollments/{id} - Unenroll user

---

## How to Use Swagger UI

### Step 1: Open Swagger UI
1. Start all microservices
2. Open browser and navigate to:
   - http://localhost:8082/swagger-ui.html (User Service)
   - http://localhost:8081/swagger-ui.html (Course Service)

### Step 2: Explore Endpoints
- All REST endpoints are grouped by controller tags
- Click on any endpoint to expand details
- View request/response schemas

### Step 3: Test Endpoints
1. Click "Try it out" button
2. Fill in required parameters/body
3. Click "Execute"
4. View response below

---

## Testing Workflow in Swagger

### Example 1: Create User (User Service)
1. Go to http://localhost:8082/swagger-ui.html
2. Find "User Management" section
3. Click on "POST /api/users"
4. Click "Try it out"
5. Enter request body:
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "INSTRUCTOR"
}
```
6. Click "Execute"
7. Note the returned user ID

### Example 2: Create Course (Course Service)
1. Go to http://localhost:8081/swagger-ui.html
2. Find "Course Management" section
3. Click on "POST /api/courses"
4. Click "Try it out"
5. Enter request body (use instructorId from previous step):
```json
{
  "title": "Spring Boot Course",
  "description": "Learn Spring Boot",
  "instructorId": 1,
  "category": "Programming",
  "price": 49.99,
  "duration": 120
}
```
6. Click "Execute"

### Example 3: Enroll Student (Course Service)
1. In http://localhost:8081/swagger-ui.html
2. Find "Enrollment Management" section
3. Click on "POST /api/enrollments"
4. Click "Try it out"
5. Enter request body:
```json
{
  "userId": 2,
  "courseId": 1
}
```
6. Click "Execute"

---

## API Gateway Note

The API Gateway (port 8080) routes requests but doesn't have its own Swagger UI since it's just a routing layer. 

To see all endpoints in one place, you need to:
- Access User Service Swagger: http://localhost:8082/swagger-ui.html
- Access Course Service Swagger: http://localhost:8081/swagger-ui.html

When testing via Gateway, use port 8080:
- User APIs: http://localhost:8080/api/users
- Course APIs: http://localhost:8080/api/courses
- Enrollment APIs: http://localhost:8080/api/enrollments

---

## Swagger Features

### 1. Interactive Documentation
- View all endpoints with descriptions
- See request/response models
- Understand required vs optional fields

### 2. Try It Out
- Execute API calls directly from browser
- No need for Postman initially
- Instant response preview

### 3. Schema Definitions
- View data models (User, Course, Enrollment)
- See field types and constraints
- Understand relationships

### 4. Response Codes
- 200: Success
- 201: Created
- 204: No Content (Delete)
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

---

## Troubleshooting

### Swagger UI Not Loading
1. Check if service is running: `netstat -ano | findstr :8082`
2. Verify URL: http://localhost:8082/swagger-ui.html
3. Try alternative: http://localhost:8082/swagger-ui/index.html
4. Check browser console for errors

### 404 Error on Swagger
- Ensure springdoc dependency is in pom.xml
- Verify application.properties has correct swagger paths
- Restart the service

### Cannot Execute Requests
- Check if MySQL is running
- Verify database connections
- Ensure required data exists (e.g., user before course)

---

## Quick Access Summary

| Service | Swagger UI | Port |
|---------|-----------|------|
| User Service | http://localhost:8082/swagger-ui.html | 8082 |
| Course Service | http://localhost:8081/swagger-ui.html | 8081 |
| API Gateway | N/A (routing only) | 8080 |

**Recommendation**: Use Swagger UI for exploring and understanding APIs, then use Postman or API Gateway for integration testing.
