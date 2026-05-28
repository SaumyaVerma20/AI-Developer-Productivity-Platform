import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
  path: '',
  redirectTo: 'login',
  pathMatch: 'full'
},
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login/login.component')
        .then(m => m.LoginComponent)
  },
  {
    path: '',
    loadComponent: () =>
      import('./shared/layouts/main-layout/main-layout.component')
        .then(m => m.MainLayoutComponent),
        canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/dashboard/pages/dashboard/dashboard.component')
            .then(m => m.DashboardComponent)
      },
      {
        path: 'incidents',
        loadComponent: () =>
          import('./features/incidents/pages/incidents/incidents.component')
            .then(m => m.IncidentsComponent)
      },
      {
        path: 'logs/upload',
        loadComponent: () =>
          import('./features/logs/pages/upload-logs/upload-logs.component')
            .then(m => m.UploadLogsComponent)
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];