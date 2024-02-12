import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  errorMessage = '';

  constructor(private userService: UserService, private formBuilder: FormBuilder,private router: Router,private toastr: ToastrService,) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      lastname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      country: ['', [Validators.required, Validators.pattern(/^[a-zA-Z ]+$/)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      emailId: ['', [Validators.email, Validators.required, Validators.pattern(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]]
    });
  }


  onReset(): void {
    this.submitted = false;
    this.errorMessage = '';
    this.form.reset();
  }

  onSubmit() {
    this.submitted = true;
    this.errorMessage = '';

    if (this.form.invalid) {
      return;
    }

    const data = {
      'firstName': this.form.value.firstname,
      'lastName': this.form.value.lastname,
      'username': this.form.value.username,
      'country': this.form.value.country,
      'password': this.form.value.password,
      'emailId': this.form.value.emailId,
      'phone': this.form.value.phone,
    };

    this.userService.registerUser(data).subscribe(
      response => {
        console.log(response);
        this.form.reset();
        this.router.navigate(['./login']);
        this.toastr.success('Successfully Registered.', 'Success');
      },
      error => {
        this.errorMessage = error.error.message;
        this.toastr.error('Something went wrong.', 'Error');
      }
    );
  }
}
