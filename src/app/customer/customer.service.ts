import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ICustomer } from '../customer/customer';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private _url = 'api/customers/';

  private customer : ICustomer;
  private messageSource = new BehaviorSubject<ICustomer>(this.customer);
  currentMessage = this.messageSource.asObservable();

  constructor(private http: HttpClient) { }

    getCustomer(userName : string) : Observable<ICustomer> {
        return this.http.get<ICustomer>(this._url + userName);
    }

    addCustomer(customer : ICustomer) : Observable<ICustomer>{
       return this.http.post<ICustomer>(this._url, customer)
      .pipe(map((response: ICustomer) => response as ICustomer));        
    }

    updateCustomer(userName : string, customer : ICustomer) : Observable<ICustomer> {
      return this.http.put<ICustomer>(this._url + userName, customer);        
    }

    changeMessage(message : ICustomer){
      this.messageSource.next(message);
    }



}
