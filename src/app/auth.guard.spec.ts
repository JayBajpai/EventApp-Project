import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { UserService } from './user.service';


describe('AuthGuard', () => {
  let authGuard: AuthGuard;
  let userService: UserService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [AuthGuard, UserService]
    });

    authGuard = TestBed.inject(AuthGuard);
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
  });

  it('should allow navigation when user is logged in', fakeAsync(() => {
    spyOn(userService, 'checkLogin').and.returnValue(true);

    const canActivate = authGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    tick(); // simulate async operation

    expect(canActivate).toBe(true);
  }));

  it('should redirect to login page and return false when user is not logged in', fakeAsync(() => {
    spyOn(userService, 'checkLogin').and.returnValue(false);
    spyOn(router, 'navigate');

    const canActivate = authGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    tick(); // simulate async operation

    expect(canActivate).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['./login']);
  }));
});
