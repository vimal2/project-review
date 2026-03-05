import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      role: ['USER', Validators.required], // USER or ARTIST
    });
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    this.authService.register(this.registerForm.value).subscribe({
      next: (response) => {
        this.successMessage = response?.message || 'Registration successful';
        this.registerForm.reset({ role: 'USER' });
        this.loading = false;
        this.router.navigate(['/home/login']);
      },
      error: (error) => {
        const backendMessage =
          error?.error?.message ||
          error?.error?.error ||
          error?.message ||
          '';
        this.errorMessage =
          backendMessage || 'Registration failed. Please try again.';
        this.loading = false;
      },
    });
  }
}
