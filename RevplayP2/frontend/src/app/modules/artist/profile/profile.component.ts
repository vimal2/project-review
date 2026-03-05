import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ArtistService } from 'src/app/core/services/artist.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profileForm!: FormGroup;
  profileData: any = null;
  artistId!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private artistService: ArtistService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    this.artistId = Number(localStorage.getItem("artistId"));

    if (!this.artistId) {
      this.router.navigate(['/artist/register']);
      return;
    }

    this.profileForm = this.fb.group({
      artistName: [''],
      bio: [''],
      genre: [''],
      instagramLink: [''],
      twitterLink: [''],
      youtubeLink: [''],
      websiteLink: ['']
    });

    this.loadProfile();
  }

  loadProfile() {
    this.artistService.getArtistProfile(this.artistId)
      .subscribe({
        next: (res: any) => {
          this.profileData = res?.data;

          if (this.profileData) {
            this.profileForm.patchValue(this.profileData);
          }
        },
        error: (err: HttpErrorResponse) => {
          if (err.status === 404) {
            localStorage.removeItem('artistId');
            this.router.navigate(['/artist/register']);
          } else {
            console.error(err);
          }
        }
      });
  }

  enableEdit() {
    this.isEditMode = true;
  }

  cancelEdit() {
    this.isEditMode = false;
    this.profileForm.patchValue(this.profileData);
  }

  updateProfile() {
    this.artistService.updateArtistProfile(
      this.artistId,
      this.profileForm.value
    ).subscribe(() => {
      alert("Profile updated successfully");
      this.isEditMode = false;
      this.loadProfile();
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/home/login']);
  }
}
