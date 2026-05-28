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
        severity: 'Critical'
      },
      {
        id: 'INC-1002',
        service: 'auth-service',
        severity: 'Medium'
      }
    ]);

    /*
    REAL API LATER:

    return this.get('/incidents');
    */
  }

}