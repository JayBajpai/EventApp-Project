import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddWishlistComponent } from './add-wishlist.component';
import { WishlistService } from '../wishlist.service';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Wishlist } from '../wishlist';

describe('AddWishlistComponent', () => {
  let component: AddWishlistComponent;
  let fixture: ComponentFixture<AddWishlistComponent>;
  let wishlistServiceSpy: jasmine.SpyObj<WishlistService>;
  let toastrServiceSpy: jasmine.SpyObj<ToastrService>;

  beforeEach(() => {
    const wishlistServiceSpyObj = jasmine.createSpyObj('WishlistService', [
      'getWishlistUpdatedObservable',
      'addToWishlist',
      'removeFromWishlist',
      'getAllWishlistItems',
      'wishlistUpdated',
    ]);

    const toastrServiceSpyObj = jasmine.createSpyObj('ToastrService', ['success', 'error']);

    TestBed.configureTestingModule({
      declarations: [AddWishlistComponent],
      providers: [
        { provide: WishlistService, useValue: wishlistServiceSpyObj },
        { provide: ToastrService, useValue: toastrServiceSpyObj },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AddWishlistComponent);
    component = fixture.componentInstance;
    wishlistServiceSpy = TestBed.inject(WishlistService) as jasmine.SpyObj<WishlistService>;
    toastrServiceSpy = TestBed.inject(ToastrService) as jasmine.SpyObj<ToastrService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadWishlistItems on ngOnInit', () => {
    const wishlistItems: Wishlist[] = [
      { eventId: 1, type: 'type', title: 'Item 1', datetime_utc: '2022-01-01T12:00:00Z' },
    ];
    wishlistServiceSpy.getWishlistUpdatedObservable.and.returnValue(of());
    wishlistServiceSpy.getAllWishlistItems.and.returnValue(of(wishlistItems));

    component.ngOnInit();

    expect(wishlistServiceSpy.getWishlistUpdatedObservable).toHaveBeenCalled();
    expect(wishlistServiceSpy.getAllWishlistItems).toHaveBeenCalled();
    expect(component.wishlistItems).toEqual(wishlistItems);
  });

  it('should call addToWishlist and update wishlistItems on success', () => {
    const eventId = 1;
    wishlistServiceSpy.addToWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();

    component.eventIdInput = eventId;
    component.addToWishlist();

    expect(wishlistServiceSpy.addToWishlist).toHaveBeenCalledWith(eventId);
    expect(toastrServiceSpy.success).toHaveBeenCalledWith(
      'Added to wishlist successfully.',
      'Success'
    );
    expect(wishlistServiceSpy.wishlistUpdated).toHaveBeenCalled();
    expect(component.eventIdInput).toBeNull();
  });

  it('should handle addToWishlist error', () => {
    const eventId = 1;
    const errorMessage = 'Error adding to wishlist';
    wishlistServiceSpy.addToWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();
    wishlistServiceSpy.addToWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();

    component.eventIdInput = eventId;
    component.addToWishlist();

    expect(wishlistServiceSpy.addToWishlist).toHaveBeenCalledWith(eventId);
    expect(toastrServiceSpy.error).toHaveBeenCalledWith(
      'Error adding to wishlist. Please try again later.',
      'Error'
    );
    expect(wishlistServiceSpy.wishlistUpdated).not.toHaveBeenCalled();
  });

  it('should call removeFromWishlist and update wishlistItems on success', () => {
    const eventId = 1;
    wishlistServiceSpy.removeFromWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();

    component.removeFromWishlist(eventId);

    expect(wishlistServiceSpy.removeFromWishlist).toHaveBeenCalledWith(eventId);
    expect(toastrServiceSpy.success).toHaveBeenCalledWith(
      'Removed from wishlist successfully.',
      'Success'
    );
    expect(wishlistServiceSpy.wishlistUpdated).toHaveBeenCalled();
  });

  it('should handle removeFromWishlist error', () => {
    const eventId = 1;
    const errorMessage = 'Error removing from wishlist';
    wishlistServiceSpy.removeFromWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();
    wishlistServiceSpy.removeFromWishlist.and.returnValue(of());
    wishlistServiceSpy.wishlistUpdated.and.callThrough();

    component.removeFromWishlist(eventId);

    expect(wishlistServiceSpy.removeFromWishlist).toHaveBeenCalledWith(eventId);
    expect(toastrServiceSpy.error).toHaveBeenCalledWith(
      'Error removing from wishlist. Please try again later.',
      'Error'
    );
    expect(wishlistServiceSpy.wishlistUpdated).not.toHaveBeenCalled();
  });

  it('should call loadWishlistItems on wishlistUpdated', () => {
    wishlistServiceSpy.getAllWishlistItems.and.returnValue(of());

    component.loadWishlistItems();

    expect(wishlistServiceSpy.getAllWishlistItems).toHaveBeenCalled();
  });
});
