import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SongService {

  private baseUrl = `${environment.apiUrl}/artists`;

  constructor(private http: HttpClient) {}

  uploadSong(artistId: number, formData: FormData): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${artistId}/songs/upload`,
      formData
    );
  }

  updateSong(artistId: number, songId: number, data: any) {
    return this.http.put(
      `${this.baseUrl}/${artistId}/songs/${songId}`,
       data
    );
  }


  getSongs(artistId: number): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${artistId}/songs`
    );
  }

  deleteSong(artistId: number, songId: number): Observable<any> {
    return this.http.delete(
      `${this.baseUrl}/${artistId}/songs/${songId}`
    );
  }

  addSongToAlbum(artistId: number, songId: number, albumId: number) {
    return this.http.put(
       `${this.baseUrl}/${artistId}/songs/${songId}/album/${albumId}`,
     {}
  );
}


removeSongFromAlbum(artistId: number, songId: number) {
  return this.http.put(
    `${this.baseUrl}/${artistId}/songs/${songId}/remove-album`,
    {}
  );
}

 updateVisibility(artistId: number, songId: number, visibility: string) {

  return this.http.put(
    `${this.baseUrl}/${artistId}/songs/${songId}/visibility?visibility=${visibility}`,
    {}
  );

}
}
