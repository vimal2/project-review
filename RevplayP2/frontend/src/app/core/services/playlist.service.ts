import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

export interface Playlist {
  id: number;
  name: string;
  description: string;
  songIds: number[];
}

@Injectable({ providedIn: 'root' })
export class PlaylistService {

  private baseUrl = `${environment.apiUrl}/playlists`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  create(data: { name: string; description: string }, username: string = this.getUsername()): Observable<Playlist> {
    return this.http.post<Playlist>(`${this.baseUrl}?username=${encodeURIComponent(username)}`, data);
  }

  getAll(username: string = this.getUsername()): Observable<Playlist[]> {
    return this.http.get<Playlist[]>(`${this.baseUrl}?username=${encodeURIComponent(username)}`);
  }

  update(playlistId: number, data: { name: string; description: string }, username: string = this.getUsername()): Observable<Playlist> {
    return this.http.put<Playlist>(`${this.baseUrl}/${playlistId}?username=${encodeURIComponent(username)}`, data);
  }

  delete(playlistId: number, username: string = this.getUsername()) {
    return this.http.delete(`${this.baseUrl}/${playlistId}?username=${encodeURIComponent(username)}`);
  }

  addSong(playlistId: number, songId: number, username: string = this.getUsername()): Observable<Playlist> {
    return this.http.post<Playlist>(`${this.baseUrl}/${playlistId}/songs/${songId}?username=${encodeURIComponent(username)}`, {});
  }

  removeSong(playlistId: number, songId: number, username: string = this.getUsername()): Observable<Playlist> {
    return this.http.delete<Playlist>(`${this.baseUrl}/${playlistId}/songs/${songId}?username=${encodeURIComponent(username)}`);
  }

  private getUsername(): string {
    return this.authService.getCurrentUsername();
  }
}
