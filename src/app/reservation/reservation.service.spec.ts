import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ReservationService } from './reservation.service';
import { IReservation } from './reservation';

describe('ReservationService', () => {

  let service: ReservationService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    
    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [ ReservationService ]
    })

    service = TestBed.get(ReservationService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() =>{
    httpMock.verify(); 
  })

  it('should get reservation', () => {
    const testingReservation : IReservation = {
      date : "",
      firstName : "",
      lastName : "",
      number : 1,
      telephone : 1,
      trackNumber : 1
    }

    service.getReservation(testingReservation.number).subscribe(reservation =>{
      expect(reservation.date).toBe('2 3 13 Styczeń 1');
      expect(reservation.firstName).toBe('oskar');
      expect(reservation.lastName).toBe('sad');
      expect(reservation.number).toBe(1);
      expect(reservation.telephone).toBe(666555444);
      expect(reservation.trackNumber).toBe(1);
    })

    const request = httpMock.expectOne(`api/reservations/1`);
    expect(request.request.method).toBe('GET');

  });

  it('should add reservation', () => {
    const testingReservation : IReservation = {
      date : '2 3 13 Styczeń 1',
      firstName : 'maciej',
      lastName : 'kon',
      number : 2,
      telephone : 666555333,
      trackNumber : 2
    }

    service.addReservation(testingReservation).subscribe(reservation =>{
      expect(reservation.date).toBe('2 3 13 Styczeń 1');
      expect(reservation.firstName).toBe('maciej');
      expect(reservation.lastName).toBe('kon');
      expect(reservation.telephone).toBe(666555333);
      expect(reservation.trackNumber).toBe(2);
    })

    const request = httpMock.expectOne(`api/reservations/`);
    expect(request.request.method).toBe('POST');

  });


  it('should delete reservation', () => {
    const testingReservation : IReservation = {
      date : '2 3 13 Styczeń 1',
      firstName : 'maciej',
      lastName : 'kon',
      number : 2,
      telephone : 666555333,
      trackNumber : 2
    }

    service.deleteReservation(testingReservation.number).subscribe(reservation =>{
      expect(reservation.date).toBe('2 3 13 Styczeń 1');
    })

    const request = httpMock.expectOne(`api/reservations/2`);
    expect(request.request.method).toBe('DELETE');

  });
})
