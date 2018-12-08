import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from "rxjs/operators";
import { IReservation } from './reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private _url = 'api/reservations/';

  private reservation : IReservation;
  private messageSource = new BehaviorSubject<IReservation>(this.reservation);
  currentMessage = this.messageSource.asObservable();

  constructor(private http: HttpClient) { }

    getReservation(number : number) : Observable<IReservation> {
        return this.http.get<IReservation>(this._url + number);
    }

    addReservation(reservation : IReservation) : Observable<IReservation> {
      return this.http.post<IReservation>(this._url, reservation);
  }

    deleteReservation(number : number) : Observable<IReservation> {
      return this.http.delete<IReservation>(this._url + number);
    }

    changeMessage(message : IReservation){
      this.messageSource.next(message);
    }
}
