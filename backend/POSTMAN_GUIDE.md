# Postman Testing Guide for Course Platform Microservices

## Service Ports
- **API Gateway**: http://localhost:8080
- **User Service**: http://localhost:8082 (direct access)
- **Course Service**: http://localhost:8081 (direct access)

## Recommended Approach
Use API Gateway (port 8080) for all requests - it routes to the appropriate microservice.

---

## 1. USER SERVICE APIs

### Base URL (via Gateway): `http://localhost:8080/api/users`

### 1.1 Create User (POST)
**URL**: `http://localhost:8080/api/users`  
**Method**: POST  
**Headers**: `Content-Type: application/json`  
**Body** (raw JSON):
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "INSTRUCTOR"
}
```

**Response**: Returns created user with ID
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "INSTRUCTOR",
  "createdAt": "2024-01-15T10:30:00"
}
```

### 1.2 Get All Users (GET)
**URL**: `http://localhost:8080/api/users`  
**Method**: GET

### 1.3 Get User by ID (GET)
**URL**: `http://localhost:8080/api/users/1`  
**Method**: GET

### 1.4 Update User (PUT)
**URL**: `http://localhost:8080/api/users/1`  
**Method**: PUT  
**Headers**: `Content-Type: application/json`  
**Body**:
```json
{
  "username": "john_doe_updated",
  "email": "john.updated@example.com",
  "password": "newpassword123",
  "role": "INSTRUCTOR"
}
```

### 1.5 Delete User (DELETE)
**URL**: `http://localhost:8080/api/users/1`  
**Method**: DELETE

### 1.6 Get User's Courses (GET)
**URL**: `http://localhost:8080/api/users/1/courses`  
**Method**: GET  
**Description**: Gets all courses taught by instructor with ID 1

---

## 2. COURSE SERVICE APIs

### Base URL (via Gateway): `http://localhost:8080/api/courses`

### 2.1 Create Course (POST)
**URL**: `http://localhost:8080/api/courses`  
**Method**: POST  
**Headers**: `Content-Type: application/json`  
**Body**:
```json
{
  "title": "Spring Boot Microservices",
  "description": "Learn to build microservices with Spring Boot",
  "instructorId": 1,
  "category": "Programming",
  "price": 49.99,
  "duration": 120
}
```

**Note**: instructorId must exist in User Service (create user first)

### 2.2 Get All Courses (GET)
**URL**: `http://localhost:8080/api/courses`  
**Method**: GET

### 2.3 Get Course by ID (GET)
**URL**: `http://localhost:8080/api/courses/1`  
**Method**: GET

### 2.4 Update Course (PUT)
**URL**: `http://localhost:8080/api/courses/1`  
**Method**: PUT  
**Headers**: `Content-Type: application/json`  
**Body**:
```json
{
  "title": "Advanced Spring Boot Microservices",
  "description": "Advanced microservices patterns",
  "instructorId": 1,
  "category": "Programming",
  "price": 79.99,
  "duration": 180
}
```

### 2.5 Delete Course (DELETE)
**URL**: `http://localhost:8080/api/courses/1`  
**Method**: DELETE

### 2.6 Get Courses by Instructor (GET)
**URL**: `http://localhost:8080/api/courses/instructor/1`  
**Method**: GET  
**Description**: Gets all courses taught by instructor with ID 1

---

## 3. ENROLLMENT SERVICE APIs

### Base URL (via Gateway): `http://localhost:8080/api/enrollments`

### 3.1 Enroll User in Course (POST)
**URL**: `http://localhost:8080/api/enrollments`  
**Method**: POST  
**Headers**: `Content-Type: application/json`  
**Body**:
```json
{
  "userId": 2,
  "courseId": 1
}
```

**Note**: Both userId and courseId must exist

### 3.2 Get User Enrollments (GET)
**URL**: `http://localhost:8080/api/enrollments/user/2`  
**Method**: GET  
**Description**: Gets all courses enrolled by user with ID 2

### 3.3 Get Course Enrollments (GET)
**URL**: `http://localhost:8080/api/enrollments/course/1`  
**Method**: GET  
**Description**: Gets all students enrolled in course with ID 1

### 3.4 Unenroll User (DELETE)
**URL**: `http://localhost:8080/api/enrollments/1`  
**Method**: DELETE  
**Description**: Deletes enrollment with ID 1

---

## Complete Testing Workflow

### Step 1: Create Users
1. Create an instructor:
```json
POST http://localhost:8080/api/users
{
  "username": "prof_smith",
  "email": "smith@university.com",
  "password": "teach123",
  "role": "INSTRUCTOR"
}
```
Note the returned ID (e.g., id: 1)

2. Create a student:
```json
POST http://localhost:8080/api/users
{
  "username": "student_alice",
  "email": "alice@student.com",
  "password": "learn123",
  "role": "STUDENT"
}
```
Note the returned ID (e.g., id: 2)

### Step 2: Create Courses
```json
POST http://localhost:8080/api/courses
{
  "title": "Java Programming",
  "description": "Complete Java course from basics to advanced",
  "instructorId": 1,
  "category": "Programming",
  "price": 59.99,
  "duration": 150
}
```
Note the returned course ID (e.g., id: 1)

### Step 3: Enroll Student
```json
POST http://localhost:8080/api/enrollments
{
  "userId": 2,
  "courseId": 1
}
```

### Step 4: Verify Data
- Get all users: `GET http://localhost:8080/api/users`
- Get all courses: `GET http://localhost:8080/api/courses`
- Get student enrollments: `GET http://localhost:8080/api/enrollments/user/2`
- Get instructor courses: `GET http://localhost:8080/api/users/1/courses`

---

## Postman Collection Setup

### Create Collection
1. Open Postman
2. Click "New" → "Collection"
3. Name it "Course Platform APIs"

### Create Folders
- User Service
- Course Service
- Enrollment Service

### Add Requests
For each API endpoint above:
1. Click "Add Request" in the appropriate folder
2. Set the HTTP method (GET, POST, PUT, DELETE)
3. Enter the URL
4. For POST/PUT: Go to "Body" → Select "raw" → Choose "JSON"
5. Paste the JSON body
6. Click "Save"

### Environment Variables (Optional)
1. Create environment: Click gear icon → "Add"
2. Add variable: `base_url` = `http://localhost:8080`
3. Use in requests: `{{base_url}}/api/users`

---

## Common Issues & Solutions

### Issue 1: Connection Refused
- Ensure all services are running (check ports 8080, 8081, 8082)
- Verify MySQL is running on port 3306

### Issue 2: 404 Not Found
- Check the URL path matches the controller mappings
- Ensure API Gateway is routing correctly

### Issue 3: 500 Internal Server Error
- Check if instructorId exists when creating courses
- Verify userId and courseId exist when creating enrollments
- Check MySQL database is accessible

### Issue 4: Foreign Key Constraint
- Create users before creating courses
- Create courses before enrollments
- Ensure IDs are valid

---

## Direct Service Access (Bypass Gateway)

If needed, you can access services directly:

- User Service: `http://localhost:8082/api/users`
- Course Service: `http://localhost:8081/api/courses`
- Enrollment Service: `http://localhost:8081/api/enrollments`

**Note**: Use Gateway (port 8080) for production-like testing.
