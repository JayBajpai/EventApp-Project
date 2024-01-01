import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { UserService } from '../user.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let userService: UserService;
  let router: Router;
  let toastr: ToastrService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [
        FormBuilder,
        {
          provide: UserService,
          useValue: {
            registerUser: jest.fn(),
          },
        },
        {
          provide: Router,
          useValue: {
            navigate: jest.fn(),
          },
        },
        {
          provide: ToastrService,
          useValue: {
            success: jest.fn(),
            error: jest.fn(),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
    toastr = TestBed.inject(ToastrService);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit form when it is valid', () => {
    spyOn(userService, 'registerUser').and.returnValue(of({}));
    spyOn(router, 'navigate');
    spyOn(toastr, 'success');

    component.form.setValue({
      firstname: 'John',
      lastname: 'Doe',
      username: 'johndoe',
      country: 'USA',
      password: 'password',
      emailId: 'johndoe@example.com',
      phone: '1234567890',
    });

    component.onSubmit();

    expect(userService.registerUser).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['./login']);
    expect(toastr.success).toHaveBeenCalledWith('Successfully Registered.', 'Success');
  });

  it('should handle registration error', () => {
    spyOn(userService, 'registerUser').and.returnValue(throwError({ error: { message: 'Registration failed' } }));
    spyOn(toastr, 'error');

    component.form.setValue({
      firstname: 'John',
      lastname: 'Doe',
      username: 'johndoe',
      country: 'USA',
      password: 'password',
      emailId: 'johndoe@example.com',
      phone: '1234567890',
    });

    component.onSubmit();

    expect(userService.registerUser).toHaveBeenCalled();
    expect(toastr.error).toHaveBeenCalledWith('Something went wrong.', 'Error');
  });
});
