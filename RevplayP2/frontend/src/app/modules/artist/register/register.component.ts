import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArtistService } from 'src/app/core/services/artist.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-artist-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private artistService: ArtistService,
    private router: Router
  ) {
    this.initForm();
  }

  initForm() {
    this.registerForm = this.fb.group({
      artistName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      genre: ['']
    });
  }

  register() {

    if (this.registerForm.invalid) return;

    this.artistService.registerArtist(this.registerForm.value)
      .subscribe((res: any) => {

        // Save artistId in localStorage
        localStorage.setItem("artistId", res.id);

        alert("Registration successful!");

        this.router.navigate(['/artist']);
      });
  }
}
