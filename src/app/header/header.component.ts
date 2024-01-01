import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { UserService } from '../user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userName: any;
  isLoggedin: boolean = false;
  loggedInSubscription!: Subscription;
  constructor(private userService: UserService, private router: Router, private toastr: ToastrService ) { }
  ngOnInit(): void {
    this.userService.loggedIn$.subscribe(isLoggedin => {
      this.isLoggedin = isLoggedin;
      if (isLoggedin) {
        this.userName = localStorage.getItem('username');
      } else {
        this.userName = ''; // Clear username when logged out
      }
    });
  }


  logout(): void {
    this.userService.logout();


    this.toastr.success('Logout successful.', 'Success');
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
   // this.loggedInSubscription.unsubscribe();
  }
}
