import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  features = [
    { icon: '📚', title: 'Quality Content', description: 'Learn from industry experts' },
    { icon: '🎓', title: 'Certificates', description: 'Earn recognized certificates' },
    { icon: '⚡', title: 'Fast Learning', description: 'Learn at your own pace' }
  ];
}
