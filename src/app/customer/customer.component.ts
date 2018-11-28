import { Component, OnInit } from '@angular/core';
import { ICustomer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  customer : ICustomer;
  numberOfReservation : number;

  constructor( private service : CustomerService, private router: Router, private toastrService: ToastrService) {

  }

  show(){
      //this.service.showReservation(this.customer.userName).subscribe((res: ICustomer) => {
       //TODO
        //get reservations details from collection reservations and create readable date = Month week day
        //for all reservations
     // });
    }

    book(){
     // this.service.addReservation(this.customer.userName).subscribe((res: ICustomer) => {
       //TODO
       //add to array  - update customer collection
        //add new reservation to collection reservations 
        //update free Dates
     // });
    }


    delete(){
      //this.service.deleteReservation(this.customer.userName).subscribe((res: ICustomer) => {
        //TODO
        //numberOfReservation - value
        //delete from array - update customer collection
        //delete from collection reservations 
        //update free Dates
        
     // });
    }

    logOut(){
      this.service.getCustomer(this.customer.userName).subscribe((res: ICustomer) => {
          //delete customer from message to unable hacking
          this.router.navigate(['home'])
          this.toastrService.success('Poprawnie', 'Wylogowano');
          this.service.changeMessage(null);
      });
    }
     
  ngOnInit() {
    this.service.currentMessage.subscribe(message => this.customer = message);
    //only for development time
    this.customer = {
      userName: "user",
      password: "user",
      reservations: [1, 2]
    }
  }

}
