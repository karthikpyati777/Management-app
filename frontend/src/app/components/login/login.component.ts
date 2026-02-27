import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = { email: '', password: '' };
  error = '';

  constructor(private userService: UserService, private router: Router) {}

  login() {
    this.error = '';
    this.userService.getAllUsers().subscribe({
      next: (users: any[]) => {
        console.log('All users:', users);
        console.log('Trying to login with:', this.credentials);
        
        const user = users.find(u => 
          u.email.toLowerCase() === this.credentials.email.toLowerCase() && 
          u.password === this.credentials.password
        );
        
        console.log('Found user:', user);
        
        if (user) {
          localStorage.setItem('userId', user.id.toString());
          localStorage.setItem('username', user.username);
          localStorage.setItem('userRole', user.role || 'STUDENT');
          alert('Login successful!');
          this.router.navigate(['/courses']);
        } else {
          this.error = 'Invalid email or password. Please check your credentials.';
        }
      },
      error: (err) => {
        console.error('Login error:', err);
        this.error = 'Login failed. Please check if backend is running.';
      }
    });
  }
}
