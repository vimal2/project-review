import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SongService } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-upload-song',
  templateUrl: './upload-song.component.html',
  styleUrls: ['./upload-song.component.css']
})
export class UploadSongComponent {

  uploadForm!: FormGroup;
  selectedFile!: File;
  artistId = Number(localStorage.getItem("artistId")); // later from login

  constructor(
    private fb: FormBuilder,
    private songService: SongService
  ) {
    this.initForm();
  }

  initForm() {
    this.uploadForm = this.fb.group({
      title: ['', Validators.required],
      genre: [''],
      duration: ['', Validators.required],
      visibility: ['PUBLIC', Validators.required]
    });
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadSong() {

    if (this.uploadForm.invalid || !this.selectedFile) {
      alert('Please fill all required fields');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('title', this.uploadForm.value.title);
    formData.append('genre', this.uploadForm.value.genre);
    formData.append('duration', this.uploadForm.value.duration);
    formData.append('visibility', this.uploadForm.value.visibility);

    this.songService.uploadSong(this.artistId, formData)
      .subscribe({
        next: () => {
          alert('Song uploaded successfully!');
          this.uploadForm.reset();
        },
        error: (err) => {
          console.error(err);
          alert('Upload failed');
        }
      });
  }
}
