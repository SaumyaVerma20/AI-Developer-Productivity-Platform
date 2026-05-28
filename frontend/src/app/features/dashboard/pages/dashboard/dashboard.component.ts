import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardApiService } from '../../../../core/services/api/dashboard-api.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  dashboardSummary: any;

  recentIncidents = [
    {
      id: 'INC-1001',
      service: 'payment-service',
      severity: 'Critical',
      status: 'Open'
    },
    {
      id: 'INC-1002',
      service: 'auth-service',
      severity: 'Medium',
      status: 'Investigating'
    },
    {
      id: 'INC-1003',
      service: 'order-service',
      severity: 'High',
      status: 'Resolved'
    }
  ];

  services = [
    {
      name: 'payment-service',
      status: 'Healthy'
    },
    {
      name: 'auth-service',
      status: 'Degraded'
    },
    {
      name: 'notification-service',
      status: 'Critical'
    }
  ];

  constructor(
    private dashboardApiService: DashboardApiService
  ) {}

  ngOnInit(): void {

    this.loadDashboardSummary();

  }

  loadDashboardSummary(): void {

    this.dashboardApiService
      .getDashboardSummary()
      .subscribe({
        next: (response) => {

          console.log(response);

          this.dashboardSummary = response;
        },
        error: (error) => {

          console.error(error);

        }
      });

  }

}