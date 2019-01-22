import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { FreeDateService } from './free-date.service';
import { IFreeDate } from './freeDate';

describe('FreeDateService', () => {
  let service: FreeDateService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [ FreeDateService ]
    })

    service = TestBed.get(FreeDateService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() =>{
    httpMock.verify(); 
  })

  it('should get freeDate', () => {
    const testingFreeDate : IFreeDate = {
      weekNumber: 1,
      freeDates : true,
      daysFree : [],
      mondayHours : [],
      tuesdayHours : [],
      wednesdayHours : [],
      thursdayHours : [],
      fridayHours : [],
      saturdayHours : [],
      sundayHours : []
    }

    service.getFreeDate(testingFreeDate.weekNumber).subscribe(freeDate =>{
      expect(freeDate.freeDates).toBe(true);
      expect(freeDate.weekNumber).toBe(1);
      expect(freeDate.daysFree.length).toBe(7);
      expect(freeDate.mondayHours.length).toBe(9);
      expect(freeDate.tuesdayHours.length).toBe(9);
      expect(freeDate.wednesdayHours.length).toBe(9);
      expect(freeDate.thursdayHours.length).toBe(9);
      expect(freeDate.fridayHours.length).toBe(9);
      expect(freeDate.saturdayHours.length).toBe(9);
      expect(freeDate.sundayHours.length).toBe(9);
    })

    const request = httpMock.expectOne(`api/freedates/1`);

    expect(request.request.method).toBe('GET');

  });
})
