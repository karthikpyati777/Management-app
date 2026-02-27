import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  courses: any[] = [];
  enrollments: any[] = [];
  newCourse = { title: '', description: '', instructorId: 1, price: 0 };
  editingCourse: any = null;

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.loadCourses();
    this.loadEnrollments();
  }

  loadCourses() {
    this.courseService.getAllCourses().subscribe({
      next: (data) => {
        this.courses = data;
      }
    });
  }

  loadEnrollments() {
    this.courseService.getAllEnrollments().subscribe({
      next: (data) => {
        this.enrollments = data;
      }
    });
  }

  createCourse() {
    this.courseService.createCourse(this.newCourse).subscribe({
      next: () => {
        this.loadCourses();
        this.newCourse = { title: '', description: '', instructorId: 1, price: 0 };
      }
    });
  }

  editCourse(course: any) {
    this.editingCourse = { ...course };
  }

  updateCourse() {
    this.courseService.updateCourse(this.editingCourse.id, this.editingCourse).subscribe({
      next: () => {
        this.loadCourses();
        this.editingCourse = null;
      }
    });
  }

  deleteCourse(id: number) {
    if (confirm('Delete this course?')) {
      this.courseService.deleteCourse(id).subscribe({
        next: () => {
          this.loadCourses();
        }
      });
    }
  }
}
