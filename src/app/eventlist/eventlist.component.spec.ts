import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EventlistComponent } from './eventlist.component';
import { EventService } from '../event.service';
import { ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';

describe('EventlistComponent', () => {
  let component: EventlistComponent;
  let fixture: ComponentFixture<EventlistComponent>;
  let eventServiceSpy: jasmine.SpyObj<EventService>;
  let toastrServiceSpy: jasmine.SpyObj<ToastrService>;

  beforeEach(() => {
    const eventServiceSpyObj = jasmine.createSpyObj('EventService', [
      'getAllEvents',
      'getEventById',
    ]);

    const toastrServiceSpyObj = jasmine.createSpyObj('ToastrService', ['error']);

    TestBed.configureTestingModule({
      declarations: [EventlistComponent],
      providers: [
        { provide: EventService, useValue: eventServiceSpyObj },
        { provide: ToastrService, useValue: toastrServiceSpyObj },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EventlistComponent);
    component = fixture.componentInstance;
    eventServiceSpy = TestBed.inject(EventService) as jasmine.SpyObj<EventService>;
    toastrServiceSpy = TestBed.inject(ToastrService) as jasmine.SpyObj<ToastrService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch all events on ngOnInit', () => {
    const mockEvents = [
      { type: 'type1', id: 1, title: 'Event 1', datetime_utc: '2022-01-01T12:00:00Z', venue: { name: 'Venue 1', address: 'Address 1', city: 'City 1', capacity: '100', country: 'Country 1' } },
      { type: 'type2', id: 2, title: 'Event 2', datetime_utc: '2022-01-02T12:00:00Z', venue: { name: 'Venue 2', address: 'Address 2', city: 'City 2', capacity: '150', country: 'Country 2' } },
    ];
    eventServiceSpy.getAllEvents.and.returnValue(of(mockEvents));

    component.ngOnInit();

    expect(eventServiceSpy.getAllEvents).toHaveBeenCalled();
    expect(component.events).toEqual(mockEvents);
    expect(component.originalEvents).toEqual(mockEvents);
  });

  it('should fetch event by ID on onSearch', () => {
    const eventId = 1;
    const mockEvent = { type: 'type1', id: 1, title: 'Event 1', datetime_utc: '2022-01-01T12:00:00Z', venue: { name: 'Venue 1', address: 'Address 1', city: 'City 1', capacity: '100', country: 'Country 1' } };
    eventServiceSpy.getEventById.and.returnValue(of(mockEvent));

    component.eventId = eventId;
    component.onSearch();

    expect(eventServiceSpy.getEventById).toHaveBeenCalledWith(eventId);
    expect(component.events).toEqual([mockEvent]);
  });

  it('should handle error on onSearch', () => {
    const eventId = 1;
    const errorMessage = 'Error fetching event by ID';
    eventServiceSpy.getEventById.and.returnValue(of(null));

    spyOn(console, 'error');

    component.eventId = eventId;
    component.onSearch();

    expect(eventServiceSpy.getEventById).toHaveBeenCalledWith(eventId);
    expect(component.events).toEqual([]);
    expect(console.error).toHaveBeenCalledWith('Error fetching event by ID', jasmine.anything());
    expect(toastrServiceSpy.error).toHaveBeenCalledWith(
      'Error fetching event by ID. Please try again later.',
      'Error'
    );
  });

  it('should reset events on reset', () => {
    const mockEvents = [
      { type: 'type1', id: 1, title: 'Event 1', datetime_utc: '2022-01-01T12:00:00Z', venue: { name: 'Venue 1', address: 'Address 1', city: 'City 1', capacity: '100', country: 'Country 1' } },
      { type: 'type2', id: 2, title: 'Event 2', datetime_utc: '2022-01-02T12:00:00Z', venue: { name: 'Venue 2', address: 'Address 2', city: 'City 2', capacity: '150', country: 'Country 2' } },
    ];
    component.events = mockEvents;
    component.originalEvents = mockEvents;
    component.eventId = 1;

    component.reset();

    expect(component.events).toEqual(mockEvents);
    expect(component.eventId).toBeNull();
  });
});
