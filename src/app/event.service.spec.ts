import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EventService } from './event.service';

describe('EventService', () => {
  let eventService: EventService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EventService],
    });

    eventService = TestBed.inject(EventService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(eventService).toBeTruthy();
  });

  it('should get all events', () => {
    const mockEvents = [
      { type: 'type1', id: 1, title: 'Event 1', datetime_utc: '2022-01-01', venue: { name: 'Venue 1', address: 'Address 1', city: 'City 1', capacity: '100', country: 'Country 1' } },
      { type: 'type2', id: 2, title: 'Event 2', datetime_utc: '2022-01-02', venue: { name: 'Venue 2', address: 'Address 2', city: 'City 2', capacity: '150', country: 'Country 2' } },
    ];

    eventService.getAllEvents().subscribe((events) => {
      expect(events).toEqual(mockEvents);
    });

    const req = httpTestingController.expectOne(`${eventService.getBaseUrl()}/allEvents`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEvents);
  });

  it('should get event by ID', () => {
    const eventId = 1;
    const mockEvent = { type: 'type1', id: 1, title: 'Event 1', datetime_utc: '2022-01-01', venue: { name: 'Venue 1', address: 'Address 1', city: 'City 1', capacity: '100', country: 'Country 1' } };

    eventService.getEventById(eventId).subscribe((event) => {
      expect(event).toEqual(mockEvent);
    });

    const req = httpTestingController.expectOne(`${eventService.getBaseUrl()}/events/${eventId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEvent);
  });
});
