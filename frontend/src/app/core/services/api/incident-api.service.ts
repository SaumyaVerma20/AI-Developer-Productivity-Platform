import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { BaseApiService } from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class IncidentApiService extends BaseApiService {

  getIncidents(): Observable<any[]> {

    return of([
      {
        id: 'INC-1001',
        service: 'payment-service',
        severity: 'Critical',
        status: 'Open',
        createdAt: '2026-05-27 10:30 AM'
      },
      {
        id: 'INC-1002',
        service: 'auth-service',
        severity: 'High',
        status: 'Investigating',
        createdAt: '2026-05-27 09:15 AM'
      },
      {
        id: 'INC-1003',
        service: 'notification-service',
        severity: 'Medium',
        status: 'Resolved',
        createdAt: '2026-05-27 08:45 AM'
      },
      {
        id: 'INC-1004',
        service: 'order-service',
        severity: 'Low',
        status: 'Monitoring',
        createdAt: '2026-05-27 07:20 AM'
      }
    ]);

    /*
    REAL API LATER:

    return this.get('/incidents');
    */
  }

}