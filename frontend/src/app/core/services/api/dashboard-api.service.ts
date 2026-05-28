import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { BaseApiService } from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardApiService extends BaseApiService {

  getDashboardSummary(): Observable<any> {

    // MOCK RESPONSE FOR NOW

    return of({
      criticalIncidents: 12,
      aiAnalyses: 145,
      retryFailures: 8,
      servicesMonitored: 24
    });

    /*
    REAL API LATER:

    return this.get('/dashboard/summary');
    */
  }

}