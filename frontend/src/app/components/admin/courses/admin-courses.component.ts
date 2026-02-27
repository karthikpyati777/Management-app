import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../../services/course.service';

@Component({
  selector: 'app-admin-courses',
  templateUrl: './admin-courses.component.html',
  styleUrls: ['./admin-courses.component.scss']
})
export class AdminCoursesComponent implements OnInit {
  courses: any[] = [];
  showForm = false;
  editMode = false;
  currentCourse: any = { title: '', description: '', instructorId: null, duration: null, price: 0 };

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.loadCourses();
  }

  loadCourses() {
    this.courseService.getAllCourses().subscribe({
      next: (data) => {
        this.courses = data;
      },
      error: (err) => console.error('Error loading courses:', err)
    });
  }

  openForm(course?: any) {
    if (course) {
      this.editMode = true;
      this.currentCourse = { ...course };
    } else {
      this.editMode = false;
      this.currentCourse = { title: '', description: '', instructorId: null, duration: null, price: 0 };
    }
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
    this.currentCourse = { title: '', description: '', instructorId: null, duration: null, price: 0 };
  }

  saveCourse() {
    if (this.editMode) {
      this.courseService.updateCourse(this.currentCourse.id, this.currentCourse).subscribe({
        next: () => {
          this.loadCourses();
          this.closeForm();
          alert('Course updated successfully!');
        },
        error: (err) => {
          console.error('Error updating course:', err);
          alert('Failed to update course');
        }
      });
    } else {
      this.courseService.createCourse(this.currentCourse).subscribe({
        next: () => {
          this.loadCourses();
          this.closeForm();
          alert('Course created successfully!');
        },
        error: (err) => {
          console.error('Error creating course:', err);
          alert('Failed to create course');
        }
      });
    }
  }

  deleteCourse(id: number) {
    if (confirm('Are you sure you want to delete this course?')) {
      this.courseService.deleteCourse(id).subscribe({
        next: () => {
          this.loadCourses();
          alert('Course deleted successfully!');
        },
        error: (err) => {
          console.error('Error deleting course:', err);
          alert('Failed to delete course');
        }
      });
    }
  }
}
