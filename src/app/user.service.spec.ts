import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { Observable } from 'rxjs';

describe('UserService', () => {
  let userService: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    });

    userService = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(userService).toBeTruthy();
  });

  it('should register a user', () => {
    const mockUser = { username: 'testUser', password: 'testPassword' };

    userService.registerUser(mockUser).subscribe(response => {
      expect(response).toBeDefined();
      expect(response.username).toBe(mockUser.username);
    });

    const req = httpTestingController.expectOne(`${(userService as any).apiUrl}/register`);
    expect(req.request.method).toBe('POST');
    req.flush({ username: mockUser.username });
  });

  it('should login a user', () => {
    const mockUser = { username: 'testUser', password: 'testPassword' };
    const mockResponse = { token: 'mockToken' };

    userService.loginUser(mockUser).subscribe(response => {
      expect(response).toBeDefined();
      expect(response.user.username).toBe(mockUser.username);
      expect(response.token).toBe(mockResponse.token);
      expect(localStorage.getItem('username')).toBe(JSON.stringify(mockUser.username));
      expect(localStorage.getItem('tokenData')).toBe(JSON.stringify(mockResponse.token));
      const loggedIn = (userService as any).loggedIn$ as Observable<boolean>;
      loggedIn.subscribe((value: boolean) => {
        expect(value).toBe(true);
      });
    });

    const req = httpTestingController.expectOne(`${(userService as any).loginUrl}/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should check if a user is logged in', () => {
    localStorage.setItem('tokenData', 'mockToken');
    expect(userService.checkLogin()).toBe(true);

    localStorage.removeItem('tokenData');
    expect(userService.checkLogin()).toBe(false);
  });

  it('should logout a user', () => {
    localStorage.setItem('username', 'testUser');
    localStorage.setItem('tokenData', 'mockToken');

    userService.logout();

    expect(localStorage.getItem('username')).toBeNull();
    expect(localStorage.getItem('tokenData')).toBeNull();
    const loggedIn = (userService as any).loggedIn$ as Observable<boolean>;
    loggedIn.subscribe((value: boolean) => {
      expect(value).toBe(false);
    });
  });
});
