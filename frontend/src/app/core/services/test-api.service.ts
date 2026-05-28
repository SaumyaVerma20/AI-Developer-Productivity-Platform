import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TestApiService {

  constructor(private http: HttpClient) {}

  testRequest() {
    return this.http.get('https://jsonplaceholder.typicode.com/posts');
  }

}