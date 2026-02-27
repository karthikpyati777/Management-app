import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  courses: any[] = [];

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.courseService.getAllCourses().subscribe({
      next: (data) => {
        this.courses = data;
      }
    });
  }

  enroll(courseId: number) {
    this.courseService.enrollCourse(courseId).subscribe({
      next: () => {
        alert('Enrolled successfully!');
      },
      error: (err) => {
        console.error('Enrollment error:', err);
        alert('Enrollment failed. Please login first.');
      }
    });
  }
}
