import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface LibrarySong {
  id: number;
  title: string;
  artist: string;
  album?: string | null;
  genre?: string | null;
  releaseDate?: string | null;
}

export interface LibrarySongDetail {
  id: number;
  title: string;
  artist: string;
  albumName: string | null;
  genre: string | null;
  duration: number | null;
  createdAt: string | null;
}

interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

interface SongResponse {
  id: number;
  title: string;
  artistName: string;
  albumName?: string | null;
  genre?: string | null;
  duration?: number | null;
  createdAt?: string | null;
}

@Injectable({ providedIn: 'root' })
export class SongLibraryService {
  private baseUrl = `${environment.apiUrl}/songs`;

  constructor(private http: HttpClient) {}

  getPublicSongs(): Observable<LibrarySong[]> {
    return this.http
      .get<ApiResponse<SongResponse[]>>(`${this.baseUrl}/public`)
      .pipe(
        map((response) =>
          (response?.data ?? []).map((song) => ({
            id: song.id,
            title: song.title,
            artist: song.artistName,
            album: song.albumName ?? null,
            genre: song.genre ?? null,
            releaseDate: song.createdAt ?? null,
          })),
        ),
      );
  }

  getPublicSongById(songId: number): Observable<LibrarySongDetail> {
    return this.http
      .get<ApiResponse<SongResponse>>(`${this.baseUrl}/public/${songId}`)
      .pipe(
        map((response) => {
          const song = response?.data;
          return {
            id: song?.id ?? songId,
            title: song?.title ?? '',
            artist: song?.artistName ?? '',
            albumName: song?.albumName ?? null,
            genre: song?.genre ?? null,
            duration: song?.duration ?? null,
            createdAt: song?.createdAt ?? null,
          };
        }),
      );
  }
}
