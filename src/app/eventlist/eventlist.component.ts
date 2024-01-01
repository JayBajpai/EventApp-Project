
import { Component, OnInit, NgZone } from '@angular/core';
import { EventService } from '../event.service';
import { RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-eventlist',
  templateUrl: './eventlist.component.html',
  styleUrls: ['./eventlist.component.css']
})
export class EventlistComponent implements OnInit {
  events: Event[] = [];
  originalEvents: Event[] = [];
  eventId: number | null = null;

  constructor(
    private eventService: EventService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.eventService.getAllEvents().subscribe(
      (data) => {
        console.log('Setting events property:', data);
        this.events = data;

        this.originalEvents = [...data];
      },
      (error) => {
        console.error('Error fetching events', error);
        this.toastr.error('Error fetching events. Please try again later.', 'Error');
      }
    );
  }

  onSearch(): void {
    if (this.eventId !== null) {
      this.eventService.getEventById(this.eventId).subscribe(
        (data) => {
          this.events = [data];
        },
        (error) => {
          console.error('Error fetching event by ID', error);
          this.events = [];
          this.toastr.error('Error fetching event by ID. Please try again later.', 'Error');
        }
      );
    } else {
      this.reset();
    }
  }

  reset(): void {
    this.events = [...this.originalEvents];
    this.eventId = null;
  }
}

interface Event {
  type: string;
  id: number;
  title: string;
  datetime_utc: string;
  venue: Venue;
}

interface Venue {
  name: string;
  address: string;
  city: string;
  capacity: string;
  country: string;
}
