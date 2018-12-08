import { Component, OnInit } from '@angular/core';
import { ICustomer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../reservation/reservation.service';
import { IReservation } from '../reservation/reservation';
import { IFreeDate } from '../freeDate/freeDate';
import { FreeDateService } from '../freeDate/free-date.service';


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  customer : ICustomer;
  freeData: IFreeDate;
  reservationToshow : IReservation[];
  oneReservation : IReservation;
  showButton : boolean;
  reservationToDelete : number;
  weekNumber : number;
  dayOfWeek : String;

  constructor( private customerService : CustomerService, private freeDateService : FreeDateService, private reservationService : ReservationService, private router: Router, private toastrService: ToastrService) {
    this.freeDateService.getFreeDate(1).subscribe((response: IFreeDate) => {
      this.freeData = response;
        console.log(response)
    });
  }

  show(){
    this.reservationToshow =  new Array(0);
      for(let reservationNumber of this.customer.reservations){
        this.reservationService.getReservation(reservationNumber).subscribe((responce: IReservation) => {
          this.reservationToshow.push(responce);
          //create readable date = Month week day
        });
      }
     this.showButton = !this.showButton;
    }

  book(){
    // this.reservationService.addReservation(this.customer.userName).subscribe((res: ICustomer) => {
      //TODO
      //add to array  - update customer collection
      //add new reservation to collection reservations 
      //update free Dates
    // });
  }

  isEqual() : boolean{
    for(let numberofReservation of this.customer.reservations){
      if(numberofReservation == this.reservationToDelete){
        return true;
      }
    }
      return false;
  }


  delete(){
    if(this.reservationToDelete != null && this.isEqual()){

      //string for next method

      //delete from reservations collection  
      this.reservationService.deleteReservation(this.reservationToDelete).subscribe((response: IReservation) => {

        //update free Dates
      this.freeFromFreeData(response.date, true);
      }); 
      
      //delete from customer collection
      let value = this.customer.reservations.indexOf(+this.reservationToDelete);
      if(value != -1){
        this.customer.reservations.splice(value, 1)
       this.customerService.updateCustomer(this.customer.userName, this.customer).subscribe((response: ICustomer) => {
       }); 
      }

      this.showButton = !this.showButton;
      this.show();
      this.reservationToDelete = null;
      this.toastrService.success('Udało się', 'Usunięto poprawnie');
      }
      else{
        this.toastrService.error('Podaj numer rezerwacji do usunięcia', 'Błąd w usuwaniu');
      }
    }

    logOut(){
          //TODO
          //upadte new date in customer, reservations and freeDate

          //delete customer from message to unable hacking
          this.router.navigate(['home'])
          this.toastrService.info('Poprawnie', 'Wylogowano');
          this.customerService.changeMessage(null);
      
    }

    createString(week : String, day : String, hour : String) : String{
      let s = "";
       s += week;
       s += " ";
       s += day;
       s += " ";
       s += hour;
      return s;
    }

    freeFromFreeData(s : String, status : boolean){
  
      let splitted = s.split(" ", 3);
      this.weekNumber = +splitted[0];
      let hour =  +splitted[2] - 10;  //visible is 10 but in array it's 0
     
      switch(splitted[1]){
        case "monday":{
          this.freeData.mondayHours[hour] = true;
          this.dayOfWeek = "monday";
          break;
        }
        case "tuesday":{
          this.freeData.tuesdayHours[hour] = true;
          this.dayOfWeek = "tuesday";
          break;
        }
        case "wednesday":{
          this.freeData.wednesdayHours[hour] = true;
          this.dayOfWeek = "wednesday";
          break;
        }
        case "thursday":{
          this.freeData.thursdayHours[hour] = true;
          this.dayOfWeek = "thursday";
          break;
        }
        case "friday":{
          this.freeData.fridayHours[hour] = true;
          this.dayOfWeek = "friday";
          break;
        }
        case "saturday":{
          this.freeData.saturdayHours[hour] = true;
          this.dayOfWeek = "saturday";
          break;
        }
        case "sunday":{
          this.freeData.sundayHours[hour] = true;
          this.dayOfWeek = "sunday";
          break;
        }
      }
      
      this.freeDateService.updateFreeDate(this.weekNumber, this.dayOfWeek, this.freeData).subscribe((responce: IFreeDate) => {
        this.freeData = responce
      });

    }

  ngOnInit() {
    this.customerService.currentMessage.subscribe(message => this.customer = message);
    this.reservationToshow =  new Array(0);
    this.showButton = true;
    this.weekNumber = 1;
    
    
    //only for development time
    //testing only
    // this.customer = {
    //   userName: "test",
    //   password: "test",
    //   reservations: [3]
    // }
  
  }
} 
