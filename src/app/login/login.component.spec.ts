import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { UserService } from '../user.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userServiceSpy: jasmine.SpyObj<UserService>;
  let toastrServiceSpy: jasmine.SpyObj<ToastrService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(waitForAsync(() => {
    const userServiceSpyObj = jasmine.createSpyObj('UserService', ['loginUser', 'loginData', 'user', 'loggedIn$', 'checkLogin', 'logout']);
    const toastrServiceSpyObj = jasmine.createSpyObj('ToastrService', ['success', 'error']);
    const routerSpyObj = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: UserService, useValue: userServiceSpyObj },
        { provide: ToastrService, useValue: toastrServiceSpyObj },
        { provide: Router, useValue: routerSpyObj },
        FormBuilder,
      ],
      imports: [ReactiveFormsModule, FormsModule],
    }).compileComponents();

    userServiceSpy = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    toastrServiceSpy = TestBed.inject(ToastrService) as jasmine.SpyObj<ToastrService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form', () => {
    expect(component.form.get('username')).toBeTruthy();
    expect(component.form.get('password')).toBeTruthy();
  });

  it('should handle login success', () => {
    userServiceSpy.loginUser.and.returnValue(of({ user: { username: 'testUser' }, jwtAuthToken: 'testToken' }));

    component.onSubmit();

    expect(userServiceSpy.loginUser).toHaveBeenCalledWith(component.form.value);
    expect(userServiceSpy.loginData).toHaveBeenCalledWith({ username: 'testUser' }, 'testToken');
    expect(toastrServiceSpy.success).toHaveBeenCalledWith('Login successful!', 'Success');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/events']);
  });

  it('should handle login failure', () => {
    userServiceSpy.loginUser.and.returnValue(of(null));
    userServiceSpy.user.next(null);

    const error = { statusText: 'OK', error: { message: 'Invalid credentials' } };

    component.onSubmit();

    expect(userServiceSpy.loginUser).toHaveBeenCalledWith(component.form.value);
    expect(toastrServiceSpy.error).toHaveBeenCalledWith('Login failed. Please check your credentials.', 'Error');
});
  it('should unsubscribe on ngOnDestroy', () => {
    spyOn(component.userSubscription, 'unsubscribe');

    component.ngOnDestroy();

    expect(component.userSubscription.unsubscribe).toHaveBeenCalled();
  });
});
