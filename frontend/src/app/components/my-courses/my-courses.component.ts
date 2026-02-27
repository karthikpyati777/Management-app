import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-my-courses',
  templateUrl: './my-courses.component.html',
  styleUrls: ['./my-courses.component.scss']
})
export class MyCoursesComponent implements OnInit {
  enrollments: any[] = [];

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.courseService.getEnrollments().subscribe({
      next: (data) => {
        this.enrollments = data;
      },
      error: (err) => {
        console.error('Error loading enrollments:', err);
      }
    });
  }
}
