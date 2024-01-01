import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

 form: FormGroup = new FormGroup({})

 isSubmit: boolean = false;
 isError: boolean = false;
 errorMessage = '';
 userSubscription: Subscription = new Subscription();
 constructor( private toastr: ToastrService , private userService: UserService, private formBuilder: FormBuilder,private router: Router) {}
 ngOnInit(): void {
  this.userSubscription = this.userService.user.subscribe((user) => {
    if (user) {
      // this.router.navigate(['./home']);
      // console.log('User data after successful login:', user);
    }
  });

  this.form = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });
}

onSubmit() {
  this.isSubmit = true;

  if (this.form.invalid) {
    return;
  }

  console.log(this.form.value);

  this.userService.loginUser(this.form.value).subscribe(
    data => {
      this.userService.loginData(data['user'], data['jwtAuthToken']);

      this.toastr.success('Login successful!', 'Success');
      this.router.navigate(['/events']);
    },
    error => {
      console.log(error);

      if (error.statusText === "OK") {
        this.errorMessage = error.error.message;
        this.isError = true;
      }

      this.toastr.error('Login failed. Please check your credentials.', 'Error');
    }
  );
}

ngOnDestroy() {
  this.userSubscription.unsubscribe();
}
}
