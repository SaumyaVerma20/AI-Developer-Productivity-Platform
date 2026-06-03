import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly ACCESS_TOKEN_KEY = 'access_token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';
  private readonly apiPrefix = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient
  ) {}

  login(tokenOrCredentials: any): any {
    if (typeof tokenOrCredentials === 'string') {
      localStorage.setItem(this.ACCESS_TOKEN_KEY, tokenOrCredentials);
      localStorage.setItem(this.REFRESH_TOKEN_KEY, 'mock-refresh-token');
      return;
    }
    return this.http.post<any>(`${this.apiPrefix}/login`, tokenOrCredentials).pipe(
      tap(response => {
        if (response && response.accessToken) {
          localStorage.setItem(this.ACCESS_TOKEN_KEY, response.accessToken);
        }
        if (response && response.refreshToken) {
          localStorage.setItem(this.REFRESH_TOKEN_KEY, response.refreshToken);
        }
      })
    );
  }

  register(userData: any): Observable<any> {
    return this.http.post<any>(`${this.apiPrefix}/register`, userData);
  }

  refreshToken(): Observable<any> {
    const refreshToken = this.getRefreshToken();
    if (!refreshToken) {
      this.clearSession();
      return throwError(() => new Error('No refresh token available'));
    }

    return this.http.post<any>(`${this.apiPrefix}/refresh`, { refreshToken }).pipe(
      tap(response => {
        if (response && response.accessToken) {
          localStorage.setItem(this.ACCESS_TOKEN_KEY, response.accessToken);
        }
        if (response && response.refreshToken) {
          localStorage.setItem(this.REFRESH_TOKEN_KEY, response.refreshToken);
        }
      }),
      catchError(error => {
        this.clearSession();
        return throwError(() => error);
      })
    );
  }

  logout(): Observable<any> {
    const refreshToken = this.getRefreshToken() || '';
    return this.http.post<any>(`${this.apiPrefix}/logout`, { refreshToken }).pipe(
      tap({
        next: () => this.clearSession(),
        error: () => this.clearSession()
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem(this.ACCESS_TOKEN_KEY);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN_KEY);
  }

  clearSession(): void {
    localStorage.removeItem(this.ACCESS_TOKEN_KEY);
    localStorage.removeItem(this.REFRESH_TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}