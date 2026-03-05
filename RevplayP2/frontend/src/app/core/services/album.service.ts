import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {

  private baseUrl = `${environment.apiUrl}/artists`;

  constructor(private http: HttpClient) {}

  // ================= CREATE ALBUM =================
  createAlbum(artistId: number, albumData: any): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${artistId}/albums`,
      albumData
    );
  }

  // ================= GET ALL ALBUMS =================
  getAlbums(artistId: number): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${artistId}/albums`
    );
  }

  // ================= GET SINGLE ALBUM =================
  getAlbumById(artistId: number, albumId: number): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${artistId}/albums/${albumId}`
    );
  }

  // ================= UPDATE ALBUM =================
  updateAlbum(
    artistId: number,
    albumId: number,
    albumData: any
  ): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/${artistId}/albums/${albumId}`,
      albumData
    );
  }

  // ================= DELETE ALBUM =================
deleteAlbum(artistId: number, albumId: number) {
  return this.http.delete(
    `${this.baseUrl}/${artistId}/albums/${albumId}`
  );
}

}
