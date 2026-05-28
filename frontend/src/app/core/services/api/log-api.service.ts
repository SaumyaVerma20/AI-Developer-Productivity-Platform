import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { BaseApiService } from './base-api.service';

@Injectable({
  providedIn: 'root'
})
export class LogApiService extends BaseApiService {

  uploadLogs(file: File): Observable<any> {

    console.log(file);

    return of({
      message: 'Logs uploaded successfully'
    });

    /*
    REAL API LATER:

    const formData = new FormData();

    formData.append('file', file);

    return this.post('/logs/upload', formData);
    */
  }

}