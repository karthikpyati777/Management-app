import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  user = { username: '', email: '', password: '' };
  error = '';

  constructor(private userService: UserService, private router: Router) {}

  register() {
    this.userService.register(this.user).subscribe({
      next: () => {
        alert('Registration successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Registration error:', err);
        this.error = err.error?.message || err.message || 'Registration failed. Please check if backend is running.';
      }
    });
  }
}
