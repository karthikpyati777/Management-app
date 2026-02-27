import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = `${environment.apiUrl}/courses`;

  constructor(private http: HttpClient) {}

  getAllCourses(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getCourse(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  createCourse(course: any): Observable<any> {
    return this.http.post(this.apiUrl, course);
  }

  updateCourse(id: number, course: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, course);
  }

  deleteCourse(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  enrollCourse(courseId: number): Observable<any> {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('Please login first');
    }
    return this.http.post(`${environment.apiUrl}/enrollments`, { 
      userId: Number(userId), 
      courseId 
    });
  }

  getEnrollments(): Observable<any> {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('Please login first');
    }
    return this.http.get(`${environment.apiUrl}/enrollments/user/${userId}`);
  }

  getCourseEnrollments(courseId: number): Observable<any> {
    return this.http.get(`${environment.apiUrl}/enrollments/course/${courseId}`);
  }

  deleteEnrollment(id: number): Observable<any> {
    return this.http.delete(`${environment.apiUrl}/enrollments/${id}`);
  }
}
