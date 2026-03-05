import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  private baseUrl = `${environment.apiUrl}/artists`;

  constructor(private http: HttpClient) {}
// register
  registerArtist(data: any) {
  return this.http.post(`${this.baseUrl}`, data);
}

  getArtistProfile(artistId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${artistId}`);
  }

  updateArtistProfile(artistId: number, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${artistId}`, data);
  }

  uploadProfileImage(artistId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(
      `${this.baseUrl}/${artistId}/profile-image`,
      formData
    );
  }

  uploadBannerImage(artistId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(
      `${this.baseUrl}/${artistId}/banner-image`,
      formData
    );
  }
}
