import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

export interface UserProfile {
  username: string;
  email: string;
  displayName: string;
  bio: string;
  profileImage: string;
}

export interface UserProfileUpdate {
  username: string;
  email: string;
  displayName: string;
  bio: string;
  profileImage: string;
}

export interface UserStats {
  totalPlaylists: number;
  totalFavorites: number;
  totalListeningMinutes: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  getProfile(username: string = this.getUsername()): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.baseUrl}/profile?username=${encodeURIComponent(username)}`);
  }

  updateProfile(data: UserProfileUpdate, username: string = this.getUsername()): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.baseUrl}/profile?username=${encodeURIComponent(username)}`, data);
  }

  getStats(username: string = this.getUsername()): Observable<UserStats> {
    return this.http.get<UserStats>(`${this.baseUrl}/stats?username=${encodeURIComponent(username)}`);
  }

  private getUsername(): string {
    return this.authService.getCurrentUsername();
  }
}
