
import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { WishlistService } from '../wishlist.service';
import { Wishlist } from '../wishlist';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-wishlist',
  templateUrl: './add-wishlist.component.html',
  styleUrls: ['./add-wishlist.component.css']
})
export class AddWishlistComponent implements OnInit {
  eventIdInput: number | null = null;
  wishlistItems: Wishlist[] = [];

  constructor(
    private wishlistService: WishlistService,
    private ngZone: NgZone,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.loadWishlistItems();
    this.wishlistService.getWishlistUpdatedObservable().subscribe(() => {
      this.loadWishlistItems();
    });
  }

  addToWishlist(): void {
    if (this.eventIdInput) {
      this.wishlistService.addToWishlist(this.eventIdInput).subscribe(() => {
        this.toastr.success('Added to wishlist successfully.', 'Success');
        this.wishlistService.wishlistUpdated();
        this.eventIdInput = null;
      }, error => {
        console.error('Error adding to wishlist', error);
        this.toastr.error('Error adding to wishlist. Please try again later.', 'Error');
      });
    }
  }

  removeFromWishlist(eventId: number): void {
    this.wishlistService.removeFromWishlist(eventId).subscribe(() => {
      this.toastr.success('Removed from wishlist successfully.', 'Success');
      this.wishlistService.wishlistUpdated();
    }, error => {
      console.error('Error removing from wishlist', error);
      this.toastr.error('Error removing from wishlist. Please try again later.', 'Error');
    });
  }

  loadWishlistItems(): void {
    this.wishlistService.getAllWishlistItems().subscribe((wishlistItems) => {
      this.wishlistItems = wishlistItems;
      this.ngZone.run(() => {});
    }, error => {
      console.error('Error loading wishlist items', error);
      this.toastr.error('Error loading wishlist items. Please try again later.', 'Error');
    });
  }
}
