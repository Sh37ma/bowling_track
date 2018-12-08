import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from "rxjs/operators";
import { IFreeDate } from './freeDate';

@Injectable({
  providedIn: 'root'
})
export class FreeDateService {

  private _url = 'api/freedates/';

  private freeDate : IFreeDate;
  private messageSource = new BehaviorSubject<IFreeDate>(this.freeDate);
  currentMessage = this.messageSource.asObservable();

  constructor(private http: HttpClient) { }

    getFreeDate(weekNumber : number) : Observable<IFreeDate> {
        return this.http.get<IFreeDate>(this._url + weekNumber);
    }

    updateFreeDate(weekNumber : number, dayOfWeek : String, freeDate : IFreeDate) : Observable<IFreeDate> {
      return this.http.put<IFreeDate>(this._url + weekNumber +"/"+ dayOfWeek, freeDate);
  }
}
