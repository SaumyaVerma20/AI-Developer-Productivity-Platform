import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { MaterialModule } from '../../material/material.module';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
   menuItems = [
    {
      label: 'Dashboard',
      icon: 'dashboard',
      route: '/dashboard'
    },
    {
      label: 'Incidents',
      icon: 'warning',
      route: '/incidents'
    },
    {
      label: 'Upload Logs',
      icon: 'upload_file',
      route: '/logs/upload'
    },
    {
      label: 'Monitoring',
      icon: 'monitor_heart',
      route: '/monitoring'
    },
    {
      label: 'Settings',
      icon: 'settings',
      route: '/settings'
    }
  ];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Logout error, redirecting anyway:', err);
        this.router.navigate(['/login']);
      }
    });
  }
}
