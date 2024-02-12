import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { RegisterComponent } from './register.component';
import { UserService } from '../user.service';


describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let userService: any;  // Replace 'any' with the actual type of UserService
  let toastrService: any; // Replace 'any' with the actual type of ToastrService

  beforeEach(async () => {
    userService = jasmine.createSpyObj('UserService', ['registerUser']);
    toastrService = jasmine.createSpyObj('ToastrService', ['success', 'error']);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, RouterTestingModule, ToastrModule.forRoot()],
      declarations: [RegisterComponent],
      providers: [
        { provide: UserService, useValue: userService },
        { provide: ToastrService, useValue: toastrService }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form', () => {
    expect(component.form).toBeDefined();
    // Add more assertions as needed to check form controls and validators
  });

  it('should reset the form on onReset()', () => {
    component.form.setValue({
      firstname: 'John',
      lastname: 'Doe',
      username: 'john.doe',
      country: 'USA',
      password: 'password123',
      emailId: 'john.doe@example.com',
      phone: '1234567890',
    });
    component.onReset();
    expect(component.submitted).toBeFalsy();
    expect(component.errorMessage).toEqual('');
    expect(component.form.value).toEqual({
      firstname: '',
      lastname: '',
      username: '',
      country: '',
      password: '',
      emailId: '',
      phone: '',
    });
  });

});
