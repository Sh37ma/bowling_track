import { Component, OnInit } from '@angular/core';
import { ICustomer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../reservation/reservation.service';
import { IReservation } from '../reservation/reservation';


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  customer : ICustomer;
  reservation : IReservation[];
  oneReservation : IReservation;
  showButton : boolean;

  constructor( private customerService : CustomerService, private reservationService : ReservationService, private router: Router, private toastrService: ToastrService) {

  }

  show(){
      for(let reservationNumber of this.customer.reservations){
        this.reservationService.getReservation(reservationNumber).subscribe((responce: IReservation) => {
          this.reservation.push(responce);
          //get reservations details from collection reservations and create readable date = Month week day
          // for all reservations
        });
        //this.reservation.push(this.oneReservation);
      }
     
     this.showButton = !this.showButton;
    }

    book(){
     // this.customerService.addReservation(this.customer.userName).subscribe((res: ICustomer) => {
       //TODO
       //add to array  - update customer collection
        //add new reservation to collection reservations 
        //update free Dates
     // });
    }


    delete(){
      //this.customerService.deleteReservation(this.customer.userName).subscribe((res: ICustomer) => {
        //TODO
        //numberOfReservation - value
        //delete from array - update customer collection
        //delete from collection reservations 
        //update free Dates
        
     // });
    }

    logOut(){
      
          //delete customer from message to unable hacking
          this.router.navigate(['home'])
          this.toastrService.info('Poprawnie', 'Wylogowano');
          this.customerService.changeMessage(null);
      
    }

  ngOnInit() {
    this.customerService.currentMessage.subscribe(message => this.customer = message);
    //only for development time
    this.reservation =  new Array(0);
    this.showButton = true;
    this.customer = {
      userName: "test",
      password: "test",
      reservations: [3, 1]
    }
    // this.reservation.push({  number : 1,
    //   firstName : "Ziom",
    //     lastName : "Mega",
    //   date : "Poniedzialek",
    //   telephone : 609609609,  });  
      // console.log(this.reservation[0])
  
  }
} 
