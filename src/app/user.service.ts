
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from 'src/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private loggedIn = new BehaviorSubject<boolean>(false);
  loggedIn$ = this.loggedIn.asObservable();
  user = new BehaviorSubject<User | null>(null);
  private apiUrl = 'http://localhost:8080/api/users';
  // private loginUrl = 'http://localhost:8081/api/v1.0/authentication';
   private loginUrl="http://eventapplogin-env.eba-pjvpfrnb.us-east-2.elasticbeanstalk.com/api/v1.0/authentication"
  constructor(private httpClient: HttpClient) { }

  registerUser(user: any): Observable<any> {
    return this.httpClient.post<any>(`${this.apiUrl}/register`, user);
  }
  private storeUser(username: any,tokenData: string) {
   // console.log("----------------------------------------------",username,tokenData);
    localStorage.setItem("username", JSON.stringify(username));
    localStorage.setItem("tokenData", JSON.stringify(tokenData));
  }

  loginUser(user: any): Observable<any> {
    return this.httpClient.post<any>(`${this.loginUrl}/login`, user)
      .pipe(
        tap(response => {
          if (response && response.token) {
            const userData = new User(user, response.token);
            this.loginData(userData.user, userData.token);
            this.storeUser(userData.user.username, userData.token);
            this.loggedIn.next(true);
          }
        })
      );
  }

  checkLogin(): boolean {
    const tokenData = localStorage.getItem('tokenData');
    return !!tokenData;
  }


  loginData(user: any, token: string) {
    const data = new User(user, token);

  }


  logout() {
    this.removeUser();
    this.loggedIn.next(false);
  }

  private removeUser() {
    localStorage.removeItem("username");
    localStorage.removeItem("tokenData");
  }
}
