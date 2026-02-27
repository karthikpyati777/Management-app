# Course Platform Frontend

Beautiful Angular frontend that communicates with Spring Boot microservices.

## Setup

1. Install dependencies:
```bash
npm install
```

2. Start development server:
```bash
ng serve
```

3. Open browser at `http://localhost:4200`

## Architecture

- **User Service** (8082): Authentication & user management
- **Course Service** (8081): Course & enrollment management
- **API Gateway** (8080): Routes all requests

## Features

- ✅ User Registration & Login
- ✅ Browse Courses
- ✅ Enroll in Courses
- ✅ View My Courses
- ✅ JWT Authentication
- ✅ Beautiful Responsive UI

## API Endpoints

All requests go through API Gateway at `http://localhost:8080/api`

- POST `/users/register` - Register user
- POST `/users/login` - Login user
- GET `/courses` - Get all courses
- POST `/enrollments` - Enroll in course
- GET `/enrollments` - Get my enrollments
