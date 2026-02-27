# Admin User Functionalities - Course Platform

## Overview
This document describes all functionalities available for ADMIN users in the Course Platform.

---

## 1. USER MANAGEMENT (Admin Only)

### 1.1 View All Users
**Endpoint**: `GET http://localhost:8080/api/users`  
**Description**: Get list of all registered users (students, instructors, admins)  
**Response**:
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "role": "STUDENT",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "id": 2,
    "username": "prof_smith",
    "email": "smith@example.com",
    "role": "INSTRUCTOR",
    "createdAt": "2024-01-15T11:00:00"
  }
]
```

### 1.2 View Specific User
**Endpoint**: `GET http://localhost:8080/api/users/{id}`  
**Example**: `GET http://localhost:8080/api/users/1`  
**Description**: Get details of a specific user

### 1.3 Create New User
**Endpoint**: `POST http://localhost:8080/api/users`  
**Description**: Admin can create users with any role  
**Body**:
```json
{
  "username": "new_instructor",
  "email": "instructor@example.com",
  "password": "secure123",
  "role": "INSTRUCTOR"
}
```

### 1.4 Update User Information
**Endpoint**: `PUT http://localhost:8080/api/users/{id}`  
**Example**: `PUT http://localhost:8080/api/users/1`  
**Description**: Update user details (username, email, role)  
**Body**:
```json
{
  "username": "updated_username",
  "email": "updated@example.com",
  "role": "INSTRUCTOR"
}
```

### 1.5 Delete User
**Endpoint**: `DELETE http://localhost:8080/api/users/{id}`  
**Example**: `DELETE http://localhost:8080/api/users/5`  
**Description**: Permanently remove a user from the system

### 1.6 View User's Courses
**Endpoint**: `GET http://localhost:8080/api/users/{id}/courses`  
**Example**: `GET http://localhost:8080/api/users/2/courses`  
**Description**: View all courses taught by an instructor

---

## 2. COURSE MANAGEMENT (Admin + Instructor)

### 2.1 View All Courses
**Endpoint**: `GET http://localhost:8080/api/courses`  
**Description**: Get list of all courses in the platform  
**Response**:
```json
[
  {
    "id": 1,
    "title": "Spring Boot Microservices",
    "description": "Learn microservices architecture",
    "instructorId": 2,
    "category": "Programming",
    "price": 49.99,
    "duration": 120,
    "createdAt": "2024-01-15T12:00:00"
  }
]
```

### 2.2 View Specific Course
**Endpoint**: `GET http://localhost:8080/api/courses/{id}`  
**Example**: `GET http://localhost:8080/api/courses/1`  
**Description**: Get detailed information about a course

### 2.3 Create New Course
**Endpoint**: `POST http://localhost:8080/api/courses`  
**Description**: Create a new course (requires valid instructorId)  
**Body**:
```json
{
  "title": "Advanced Java Programming",
  "description": "Deep dive into Java concepts",
  "instructorId": 2,
  "category": "Programming",
  "price": 79.99,
  "duration": 180
}
```

### 2.4 Update Course
**Endpoint**: `PUT http://localhost:8080/api/courses/{id}`  
**Example**: `PUT http://localhost:8080/api/courses/1`  
**Description**: Update course details  
**Body**:
```json
{
  "title": "Updated Course Title",
  "description": "Updated description",
  "instructorId": 2,
  "category": "Programming",
  "price": 89.99,
  "duration": 200
}
```

### 2.5 Delete Course
**Endpoint**: `DELETE http://localhost:8080/api/courses/{id}`  
**Example**: `DELETE http://localhost:8080/api/courses/3`  
**Description**: Remove a course from the platform

### 2.6 View Courses by Instructor
**Endpoint**: `GET http://localhost:8080/api/courses/instructor/{instructorId}`  
**Example**: `GET http://localhost:8080/api/courses/instructor/2`  
**Description**: Get all courses taught by a specific instructor

---

## 3. ENROLLMENT MANAGEMENT (Admin)

### 3.1 Enroll Student in Course
**Endpoint**: `POST http://localhost:8080/api/enrollments`  
**Description**: Manually enroll a student in a course  
**Body**:
```json
{
  "userId": 1,
  "courseId": 1
}
```
**Response**:
```json
{
  "id": 1,
  "userId": 1,
  "courseId": 1,
  "enrolledAt": "2024-01-15T14:00:00",
  "status": "ACTIVE"
}
```

### 3.2 View Student's Enrollments
**Endpoint**: `GET http://localhost:8080/api/enrollments/user/{userId}`  
**Example**: `GET http://localhost:8080/api/enrollments/user/1`  
**Description**: View all courses a student is enrolled in

### 3.3 View Course Enrollments
**Endpoint**: `GET http://localhost:8080/api/enrollments/course/{courseId}`  
**Example**: `GET http://localhost:8080/api/enrollments/course/1`  
**Description**: View all students enrolled in a specific course

### 3.4 Unenroll Student
**Endpoint**: `DELETE http://localhost:8080/api/enrollments/{id}`  
**Example**: `DELETE http://localhost:8080/api/enrollments/1`  
**Description**: Remove a student's enrollment from a course

---

## 4. ADMIN WORKFLOW EXAMPLES

### Example 1: Complete User Setup
```bash
# 1. Create an instructor
POST http://localhost:8080/api/users
{
  "username": "dr_johnson",
  "email": "johnson@university.com",
  "password": "secure123",
  "role": "INSTRUCTOR"
}
# Response: { "id": 10, ... }

# 2. Create a course for that instructor
POST http://localhost:8080/api/courses
{
  "title": "Database Design",
  "description": "Learn database fundamentals",
  "instructorId": 10,
  "category": "Database",
  "price": 59.99,
  "duration": 100
}
# Response: { "id": 5, ... }

# 3. Enroll students
POST http://localhost:8080/api/enrollments
{
  "userId": 1,
  "courseId": 5
}
```

### Example 2: Platform Analytics
```bash
# Get all users
GET http://localhost:8080/api/users

# Get all courses
GET http://localhost:8080/api/courses

# Check specific course enrollments
GET http://localhost:8080/api/enrollments/course/1

# Check specific student's enrollments
GET http://localhost:8080/api/enrollments/user/1
```

### Example 3: User Management
```bash
# View user details
GET http://localhost:8080/api/users/5

# Update user role (promote to instructor)
PUT http://localhost:8080/api/users/5
{
  "username": "existing_username",
  "email": "existing@email.com",
  "role": "INSTRUCTOR"
}

# View instructor's courses
GET http://localhost:8080/api/users/5/courses
```

---

## 5. ADMIN DASHBOARD DATA ENDPOINTS

### 5.1 Platform Statistics
```bash
# Total users
GET http://localhost:8080/api/users
# Count the array length

# Total courses
GET http://localhost:8080/api/courses
# Count the array length

# Total enrollments (per course)
GET http://localhost:8080/api/enrollments/course/{courseId}
```

### 5.2 User Activity
```bash
# View all instructors and their courses
GET http://localhost:8080/api/users
# Filter by role: "INSTRUCTOR"
# For each instructor:
GET http://localhost:8080/api/users/{instructorId}/courses
```

---

## 6. POSTMAN COLLECTION FOR ADMIN

### Collection Structure:
```
Course Platform Admin
├── User Management
│   ├── Get All Users
│   ├── Get User by ID
│   ├── Create User
│   ├── Update User
│   ├── Delete User
│   └── Get User Courses
├── Course Management
│   ├── Get All Courses
│   ├── Get Course by ID
│   ├── Create Course
│   ├── Update Course
│   ├── Delete Course
│   └── Get Courses by Instructor
└── Enrollment Management
    ├── Enroll Student
    ├── Get User Enrollments
    ├── Get Course Enrollments
    └── Unenroll Student
```

---

## 7. ADMIN ROLES & PERMISSIONS

### Current Implementation:
- **ADMIN**: Full access to all endpoints
- **INSTRUCTOR**: Can manage their own courses
- **STUDENT**: Can view courses and manage their enrollments

### Recommended Admin Capabilities:
1. ✅ Create/Update/Delete Users
2. ✅ Create/Update/Delete Courses
3. ✅ Manage Enrollments
4. ✅ View All Platform Data
5. ✅ Assign Roles to Users
6. ✅ View Course Analytics
7. ✅ Monitor User Activity

---

## 8. TESTING ADMIN FUNCTIONS

### Test Scenario 1: User Management
```bash
# 1. Create admin user
POST http://localhost:8080/api/users/register
{
  "username": "admin",
  "email": "admin@platform.com",
  "password": "admin123",
  "role": "ADMIN"
}

# 2. Login as admin
POST http://localhost:8080/api/users/login
{
  "email": "admin@platform.com",
  "password": "admin123"
}

# 3. View all users
GET http://localhost:8080/api/users
```

### Test Scenario 2: Course Management
```bash
# 1. Create instructor
POST http://localhost:8080/api/users
{
  "username": "instructor1",
  "email": "inst1@platform.com",
  "password": "pass123",
  "role": "INSTRUCTOR"
}

# 2. Create course for instructor
POST http://localhost:8080/api/courses
{
  "title": "Test Course",
  "description": "Test Description",
  "instructorId": 1,
  "category": "Test",
  "price": 29.99,
  "duration": 60
}

# 3. View all courses
GET http://localhost:8080/api/courses
```

---

## 9. SWAGGER UI ACCESS

**User Service Swagger**: http://localhost:8082/swagger-ui.html  
**Course Service Swagger**: http://localhost:8081/swagger-ui.html

Use Swagger UI to:
- View all available endpoints
- Test APIs directly from browser
- See request/response schemas
- Understand parameter requirements

---

## 10. QUICK REFERENCE

| Function | Method | Endpoint |
|----------|--------|----------|
| List Users | GET | /api/users |
| Create User | POST | /api/users |
| Update User | PUT | /api/users/{id} |
| Delete User | DELETE | /api/users/{id} |
| List Courses | GET | /api/courses |
| Create Course | POST | /api/courses |
| Update Course | PUT | /api/courses/{id} |
| Delete Course | DELETE | /api/courses/{id} |
| Enroll Student | POST | /api/enrollments |
| View Enrollments | GET | /api/enrollments/user/{userId} |
| Unenroll Student | DELETE | /api/enrollments/{id} |

**Base URL**: http://localhost:8080 (via API Gateway)
