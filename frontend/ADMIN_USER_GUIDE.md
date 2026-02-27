# Complete Course Platform - Admin & User Guide

## 🎯 Features Implemented

### Admin Panel (Full CRUD Operations)
- **User Management**: Create, Read, Update, Delete users
- **Course Management**: Create, Read, Update, Delete courses
- **Enrollment Management**: View all enrollments, Remove enrollments

### User Features
- **Registration**: Create new account
- **Login**: Authenticate with email/password
- **Browse Courses**: View all available courses
- **Enroll in Courses**: Register for courses
- **My Courses**: View enrolled courses

## 🚀 How to Run

### 1. Start Backend Services
Make sure these are running:
- API Gateway: `http://localhost:8080`
- User Service: `http://localhost:8082`
- Course Service: `http://localhost:8081`

### 2. Start Frontend
```bash
cd c:\course-platform\frontend
npm start
```

### 3. Access Application
Open browser: `http://localhost:4200`

## 👥 User Roles & Access

### Regular User Flow
1. **Register**: Go to `/register`
   - Username: `student1`
   - Email: `student1@test.com`
   - Password: `password123`

2. **Login**: Go to `/login`
   - Email: `student1@test.com`
   - Password: `password123`

3. **Browse Courses**: Click "Courses" in navigation

4. **Enroll**: Click "Enroll Now" on any course

5. **View My Courses**: Click "My Courses" to see enrollments

### Admin Flow
1. **Access Admin Panel**: Go to `/admin` (after login)

2. **Manage Users**:
   - View all registered users
   - Add new users with roles (STUDENT/INSTRUCTOR/ADMIN)
   - Edit user details
   - Delete users

3. **Manage Courses**:
   - View all courses
   - Create new courses (need instructor ID)
   - Edit course details
   - Delete courses

4. **Manage Enrollments**:
   - View all user enrollments
   - See which user enrolled in which course
   - Remove enrollments

## 📊 Admin Panel Features

### Users Tab
- **Columns**: ID, Username, Email, Role, Created At, Actions
- **Actions**: Edit, Delete
- **Add User**: Click "+ Add User" button
- **Roles**: STUDENT, INSTRUCTOR, ADMIN

### Courses Tab
- **Columns**: ID, Title, Description, Instructor ID, Duration, Price, Actions
- **Actions**: Edit, Delete
- **Add Course**: Click "+ Add Course" button
- **Fields**: Title, Description, Instructor ID, Duration, Price

### Enrollments Tab
- **Columns**: Enrollment ID, User ID, Course ID, Course Title, Enrolled At, Actions
- **Actions**: Remove enrollment
- **View**: See all user-course relationships

## 🎨 Beautiful Design Features

### Navigation Bar
- Gradient purple theme
- Responsive design
- Shows username when logged in
- Dynamic menu based on auth status

### Course Cards
- Modern card design with shadows
- Hover effects (lift animation)
- Large emoji icons
- Gradient buttons
- Price display

### Admin Panel
- Sidebar navigation with gradient background
- Tab-based interface
- Modal forms for create/edit
- Data tables with hover effects
- Color-coded badges for roles
- Smooth transitions

### Forms
- Clean input fields with borders
- Validation
- Error messages
- Success alerts
- Cancel/Save buttons

## 🔌 API Endpoints Used

### User Service (via API Gateway)
- `GET /api/users` - Get all users
- `POST /api/users/register` - Register user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Course Service (via API Gateway)
- `GET /api/courses` - Get all courses
- `POST /api/courses` - Create course
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

### Enrollment Service (via API Gateway)
- `POST /api/enrollments` - Enroll user
- `GET /api/enrollments/user/{userId}` - Get user enrollments
- `GET /api/enrollments/course/{courseId}` - Get course enrollments
- `DELETE /api/enrollments/{id}` - Remove enrollment

## 📝 Test Scenarios

### Scenario 1: Admin Creates Course
1. Login as admin
2. Go to Admin Panel → Courses tab
3. Click "+ Add Course"
4. Fill in: Title, Description, Instructor ID (use 1), Duration, Price
5. Click "Save"
6. Course appears in table

### Scenario 2: User Enrolls in Course
1. Register/Login as student
2. Click "Courses" in navigation
3. Browse available courses
4. Click "Enroll Now" on a course
5. See success message
6. Go to "My Courses" to verify enrollment

### Scenario 3: Admin Views Enrollments
1. Login as admin
2. Go to Admin Panel → Enrollments tab
3. See all user enrollments with course titles
4. Can remove enrollments if needed

## 🎯 Key Components Created

### Admin Components
- `AdminDashboardComponent` - Main admin layout with sidebar
- `AdminUsersComponent` - User CRUD operations
- `AdminCoursesComponent` - Course CRUD operations
- `AdminEnrollmentsComponent` - View/manage enrollments

### User Components (Enhanced)
- `CoursesComponent` - Browse and enroll
- `MyCoursesComponent` - View enrollments
- `LoginComponent` - Authentication
- `RegisterComponent` - User registration

### Services (Enhanced)
- `UserService` - Added update, delete methods
- `CourseService` - Added CRUD and enrollment methods

## 🎨 Color Scheme
- Primary Gradient: `#667eea` → `#764ba2` (Purple)
- Success: `#3498db` (Blue)
- Danger: `#e74c3c` (Red)
- Background: `#f5f7fa` (Light Gray)
- Text: `#2d3748` (Dark Gray)

## ✅ Completed Features Checklist
- ✅ User Registration & Login
- ✅ Browse Courses with beautiful cards
- ✅ Enroll in Courses
- ✅ View My Enrolled Courses
- ✅ Admin Panel with sidebar navigation
- ✅ Admin: Full User CRUD
- ✅ Admin: Full Course CRUD
- ✅ Admin: View & Manage Enrollments
- ✅ Beautiful responsive UI
- ✅ Modal forms for create/edit
- ✅ Error handling & alerts
- ✅ Backend integration with all endpoints
- ✅ Gradient theme throughout
- ✅ Hover effects & animations

## 🚀 Next Steps (Optional Enhancements)
- Add authentication guards for admin routes
- Implement search/filter in admin tables
- Add pagination for large datasets
- Show course details in enrollments
- Add user profile page
- Implement role-based access control
- Add course ratings and reviews
