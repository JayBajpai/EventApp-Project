import { UserService } from './user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'store';

  error: string | null = null;
  constructor(private UserService: UserService) { }
  ngOnInit() {

  }
  handleError() {
    this.error = null;
  }
}
