import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Overview {
  totalSongs: number;
  totalPlays: number;
  totalFavorites: number;
}

export interface SongAnalytics {
  id: number;
  title: string;
  playCount: number;
  favoriteCount: number;
}

export interface TrendResponse {
  label: string;
  count: number;
}

export interface TopListener {
  listenerName: string;
  listenCount: number;
}

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  private baseUrl = `${environment.apiUrl}/artist/analytics`;

  constructor(private http: HttpClient) {}

  getOverview(artistId: number): Observable<Overview> {
    return this.http.get<Overview>(`${this.baseUrl}/${artistId}/overview`);
  }

  getSongs(artistId: number): Observable<SongAnalytics[]> {
    return this.http.get<SongAnalytics[]>(`${this.baseUrl}/${artistId}/songs`);
  }

  getTopSongs(artistId: number): Observable<SongAnalytics[]> {
    return this.http.get<SongAnalytics[]>(`${this.baseUrl}/${artistId}/top`);
  }

  getTrends(
    artistId: number,
    type: 'daily' | 'weekly' | 'monthly' = 'monthly'
  ): Observable<TrendResponse[]> {
    return this.http.get<TrendResponse[]>(
      `${this.baseUrl}/${artistId}/trends?type=${type}`
    );
  }

  getTopListeners(artistId: number): Observable<TopListener[]> {
    return this.http.get<TopListener[]>(
      `${this.baseUrl}/${artistId}/top-listeners`
    );
  }
}
