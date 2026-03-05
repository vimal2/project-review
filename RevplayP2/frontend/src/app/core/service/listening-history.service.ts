import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AuthSessionService } from './auth-session.service';
import { environment } from 'src/environments/environment';

export interface ListeningHistoryItem {
  historyId: number;
  userId: number;
  songId: number;
  songTitle: string;
  songUrl: string;
  playedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ListeningHistoryService {
  private readonly baseUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private authSessionService: AuthSessionService
  ) {}

  recordPlay(songId: number): Observable<{ success: boolean; message: string }> {
    const userId = this.authSessionService.getCurrentUserId();
    if (userId === null) {
      return throwError(() => new Error('User not logged in'));
    }

    return this.http.post<{ success: boolean; message: string }>(`${this.baseUrl}/player/play`, {
      userId,
      songId
    });
  }

  getRecentHistory(limit = 50): Observable<ListeningHistoryItem[]> {
    const userId = this.authSessionService.getCurrentUserId();
    if (userId === null) {
      return throwError(() => new Error('User not logged in'));
    }

    const params = new HttpParams()
      .set('userId', userId)
      .set('limit', Math.min(Math.max(limit, 1), 50));
    return this.http.get<ListeningHistoryItem[]>(`${this.baseUrl}/history/recent`, { params });
  }

  clearHistory(): Observable<{ success: boolean; message: string }> {
    const userId = this.authSessionService.getCurrentUserId();
    if (userId === null) {
      return throwError(() => new Error('User not logged in'));
    }

    const params = new HttpParams().set('userId', userId);
    return this.http.delete<{ success: boolean; message: string }>(`${this.baseUrl}/history`, { params });
  }
}
