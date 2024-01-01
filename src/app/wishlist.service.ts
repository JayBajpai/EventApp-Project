
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Wishlist } from './wishlist';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  private baseUrl = 'http://localhost:8083/api/wishlist';
  private wishlistUpdatedSubject = new Subject<void>();

  constructor(private http: HttpClient) {}

  addToWishlist(eventId: number): Observable<Wishlist> {
    const url = `${this.baseUrl}/add?eventId=${eventId}`;
    return this.http.post<Wishlist>(url, eventId);
  }

  removeFromWishlist(eventId: number): Observable<void> {
    const url = `${this.baseUrl}/remove/${eventId}`;
    return this.http.delete<void>(url, { responseType: 'text' as 'json' });
  }

  getAllWishlistItems(): Observable<Wishlist[]> {
    const url = `${this.baseUrl}/all`;
    return this.http.get<Wishlist[]>(url);
  }

  wishlistUpdated(): void {
    this.wishlistUpdatedSubject.next();
  }

  getWishlistUpdatedObservable(): Observable<void> {
    return this.wishlistUpdatedSubject.asObservable();
  }
}
