import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.scss']
})
export class AdminUsersComponent implements OnInit {
  users: any[] = [];
  showForm = false;
  editMode = false;
  currentUser: any = { username: '', email: '', password: '', role: 'STUDENT' };

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => console.error('Error loading users:', err)
    });
  }

  openForm(user?: any) {
    if (user) {
      this.editMode = true;
      this.currentUser = { ...user };
    } else {
      this.editMode = false;
      this.currentUser = { username: '', email: '', password: '', role: 'STUDENT' };
    }
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
    this.currentUser = { username: '', email: '', password: '', role: 'STUDENT' };
  }

  saveUser() {
    if (this.editMode) {
      this.userService.updateUser(this.currentUser.id, this.currentUser).subscribe({
        next: () => {
          this.loadUsers();
          this.closeForm();
          alert('User updated successfully!');
        },
        error: (err) => {
          console.error('Error updating user:', err);
          alert('Failed to update user');
        }
      });
    } else {
      this.userService.register(this.currentUser).subscribe({
        next: () => {
          this.loadUsers();
          this.closeForm();
          alert('User created successfully!');
        },
        error: (err) => {
          console.error('Error creating user:', err);
          alert('Failed to create user');
        }
      });
    }
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loadUsers();
          alert('User deleted successfully!');
        },
        error: (err) => {
          console.error('Error deleting user:', err);
          alert('Failed to delete user');
        }
      });
    }
  }
}
