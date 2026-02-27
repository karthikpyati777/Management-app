import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../../services/course.service';

@Component({
  selector: 'app-admin-enrollments',
  templateUrl: './admin-enrollments.component.html',
  styleUrls: ['./admin-enrollments.component.scss']
})
export class AdminEnrollmentsComponent implements OnInit {
  enrollments: any[] = [];

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.loadAllEnrollments();
  }

  loadAllEnrollments() {
    // Load all courses and their enrollments
    this.courseService.getAllCourses().subscribe({
      next: (courses) => {
        courses.forEach((course: any) => {
          this.courseService.getCourseEnrollments(course.id).subscribe({
            next: (enrollments) => {
              enrollments.forEach((enrollment: any) => {
                this.enrollments.push({
                  ...enrollment,
                  courseTitle: course.title
                });
              });
            }
          });
        });
      },
      error: (err) => console.error('Error loading enrollments:', err)
    });
  }

  unenroll(id: number) {
    if (confirm('Are you sure you want to remove this enrollment?')) {
      this.courseService.deleteEnrollment(id).subscribe({
        next: () => {
          this.enrollments = [];
          this.loadAllEnrollments();
          alert('Enrollment removed successfully!');
        },
        error: (err) => {
          console.error('Error removing enrollment:', err);
          alert('Failed to remove enrollment');
        }
      });
    }
  }
}
