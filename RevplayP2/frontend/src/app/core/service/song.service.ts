import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Song {
  id: number;
  title: string;
  url: string;
}

interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

interface PublicSongResponse {
  id: number;
  title: string;
  audioFileUrl?: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private readonly baseUrl = `${environment.apiUrl}/songs`;
  private readonly apiOrigin = environment.apiUrl.replace(/\/api\/?$/, '');

  constructor(private http: HttpClient) {}

  getAllSongs(): Observable<Song[]> {
    return this.http.get<ApiResponse<PublicSongResponse[]>>(`${this.baseUrl}/public`).pipe(
      map((response) =>
        (response?.data ?? []).map((song) => ({
          id: song.id,
          title: song.title,
          url: this.toAbsoluteAudioUrl(song.audioFileUrl),
        }))
      )
    );
  }

  private toAbsoluteAudioUrl(audioFileUrl: string | null | undefined): string {
    const trimmed = (audioFileUrl ?? '').trim();
    if (!trimmed) {
      return '';
    }

    if (/^https?:\/\//i.test(trimmed)) {
      return trimmed;
    }

    const normalizedPath = trimmed.startsWith('/') ? trimmed : `/${trimmed}`;
    return `${this.apiOrigin}${normalizedPath}`;
  }
}
