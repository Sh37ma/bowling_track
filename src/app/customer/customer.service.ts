import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ICustomer } from '../customer/customer';
import { Observable } from 'rxjs';
import { map } from "rxjs/operators";
import { BehaviorSubject } from 'rxjs';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';

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
        return this.http.get<ICustomer>(this._url + userName) 
        .pipe(map((response: ICustomer) => response as ICustomer));        
    }

    addCustomer(userName : string) : Observable<ICustomer> {
      return this.http.get<ICustomer>(this._url + userName) 
      .pipe(map((response: ICustomer) => response as ICustomer));        
    }

    updateCustomer(userName : string) : Observable<ICustomer> {
      return this.http.get<ICustomer>(this._url + userName) 
      .pipe(map((response: ICustomer) => response as ICustomer));        
    }

    changeMessage(message : ICustomer){
      this.messageSource.next(message);
    }



}
