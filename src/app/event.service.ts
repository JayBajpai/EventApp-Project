import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class EventService {
 // private baseUrl = 'http://localhost:8082/api/v1.0/events';

     private baseUrl = "http://eventlist-env.eba-sx2jnavd.eu-west-3.elasticbeanstalk.com/api/v1.0/events";
  getBaseUrl(): string {
    return this.baseUrl;
  }

  constructor(private http: HttpClient) {}

  getAllEvents(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/allEvents`).pipe(
      tap(data => console.log('Event Service Response:', data))
    );
  }

  getEventById(eventId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/events/${eventId}`);
  }
}
