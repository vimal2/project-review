import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FavoriteService {

  private baseUrl = `${environment.apiUrl}/favorites`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  add(songId: number, username: string = this.getUsername()) {
    return this.http.post(`${this.baseUrl}/${songId}?username=${encodeURIComponent(username)}`, {});
  }

  remove(songId: number, username: string = this.getUsername()) {
    return this.http.delete(`${this.baseUrl}/${songId}?username=${encodeURIComponent(username)}`);
  }

  get(username: string = this.getUsername()) {
    return this.http.get<number[]>(`${this.baseUrl}?username=${encodeURIComponent(username)}`);
  }

  private getUsername(): string {
    return this.authService.getCurrentUsername();
  }
}
