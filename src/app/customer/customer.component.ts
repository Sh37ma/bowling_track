import { Component, OnInit } from '@angular/core';
import { ICustomer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../reservation/reservation.service';
import { IReservation } from '../reservation/reservation';
import { IFreeDate } from '../freeDate/freeDate';
import { FreeDateService } from '../freeDate/free-date.service';
import { Days } from '../freeDate/enum';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  customer : ICustomer;
  freeData: IFreeDate;
  reservationToshow : IReservation[];
  showButton : boolean;
  showAdd : boolean;
  reservationToDelete : number;
  weekNumber : number;
  dayOfWeek : String;
  selectedWeek : number;
  selectedMonth : String;
  selectedDay : number;
  selectedHour : number;
  selectedTrack : number;
  dataToShow : IFreeDate;
  weekTable : String[];
  hoursTable : number[];
  addFirstName : String;
  addLastName : String;
  addTelephone : number;
  newReservation : IReservation;
  select : number;
  

  constructor( private customerService : CustomerService, private freeDateService : FreeDateService, private reservationService : ReservationService, private router: Router, private toastrService: ToastrService) {
    this.freeDateService.getFreeDate(1).subscribe((response: IFreeDate) => {
      this.freeData = response;
        console.log(response)
    });
  }

  onClick(index: number){
    this.select = index;
    console.log(index);
    this.reservationToDelete = this.reservationToshow[index].number;
    this.toastrService.warning((this.reservationToshow[index].date).toString(), 'Wybrano rezerwacje do usuniecia');
  }

  show(){
    this.reservationToshow =  new Array(0);
      for(let reservationNumber of this.customer.reservations){
        this.reservationService.getReservation(reservationNumber).subscribe((response: IReservation) => {

          response.date = this.makeReadable(response.date);
          this.reservationToshow.push(response);

        });
      }
     this.showButton = !this.showButton;
    }

    makeReadable(data : String) : String{
      let ready = "";
      let splitted = data.split(" ", 5);

      ready += "Tor nr. ";
      ready += splitted[4];
      ready += " ";
      ready += splitted[3];
      ready += " ";
      ready += "Tydzień ";
      ready += splitted[0];
      ready += " ";
      ready += Days[splitted[1]];
      ready += " ";
      ready += "godz. ";
      ready += splitted[2];
      return ready;
    }

    changeButtonAdd(){
      this.showAdd = !this.showAdd;
    }

    showTermin(){
      this.weekTable = new Array(0);
      this.freeDateService.getFreeDate(this.selectedWeek).subscribe((response: IFreeDate) => {
        this.dataToShow = response;
      
      let index = 0;
      for(let day of this.dataToShow.daysFree){
        index ++;
        if(day){
          this.weekTable.push(Days[index]);
        }
      }
    });
    }

    showHours(){
      var array = new Array(0);
      this.hoursTable = new Array(0);

      switch(+Days[this.selectedDay]){
        case 1:{
          array = this.freeData.mondayHours;
          break;
        }
        case 2:{
          array = this.freeData.tuesdayHours;
          break;
        }
        case 3:{
          array = this.freeData.wednesdayHours;
          break;
        }
        case 4:{
          array = this.freeData.thursdayHours;
          break;
        }
        case 5:{
          array = this.freeData.fridayHours;
          break;
        }
        case 6:{
          array = this.freeData.saturdayHours;
          break;
        }
        case 7:{
          array = this.freeData.sundayHours;
          break;
        }
      }

      let index = 9;
      for(let hour of array){
        index ++;
        if(hour){
          this.hoursTable.push(index);
        }
      }
    }

  book(){
    this.newReservation.firstName = this.addFirstName;
    this.newReservation.lastName = this.addLastName;
    this.newReservation.telephone = this.addTelephone;
    this.newReservation.trackNumber = this.selectedTrack;

    if(this.addFirstName !='' && this.addLastName !='' && this.addTelephone >= 500000000 && this.selectedHour != null){
      this.newReservation.date = this.createString(this.selectedWeek, +Days[this.selectedDay], 
        this.selectedHour, this.selectedMonth, this.selectedTrack);

      //add new reservation to collection reservations 
      this.reservationService.addReservation(this.newReservation).subscribe((response: IReservation) => {

      //add to array  - update customer collection
      this.customer.reservations.push(response.number);
      this.customerService.updateCustomer(this.customer.userName, this.customer) .subscribe((response: ICustomer) => {
      });

      //update free Dates
      this.freeFromFreeData(this.newReservation.date, false);

      //refresh website
      this.showButton = !this.showButton;
      this.show();
      this.toastrService.success('Dodano rezerwacje', 'Sukces');
      });
    }
    else{
      this.toastrService.error('Sprawdź czy podałeś poprawne dane oraz wybierz termin', ' Nie poprawne dane');
    }
  }

  freeFromFreeData(s : String, status : boolean){
  
    let splitted = s.split(" ", 4);
    this.weekNumber = +splitted[0];
    let hour =  +splitted[2] - 10;  //visible is 10 but in array it's 0
      console.log(hour);
    switch(+splitted[1]){
      case 1:{
        this.freeData.mondayHours[hour] = status;
        this.dayOfWeek = "monday";
        break;
      }
      case 2:{
        this.freeData.tuesdayHours[hour] = status;
        this.dayOfWeek = "tuesday";
        break;
      }
      case 3:{
        this.freeData.wednesdayHours[hour] = status;
        this.dayOfWeek = "wednesday";
        break;
      }
      case 4:{
        this.freeData.thursdayHours[hour] = status;
        this.dayOfWeek = "thursday";
        break;
      }
      case 5:{
        this.freeData.fridayHours[hour] = status;
        this.dayOfWeek = "friday";
        break;
      }
      case 6:{
        this.freeData.saturdayHours[hour] = status;
        this.dayOfWeek = "saturday";
        break;
      }
      case 7:{
        this.freeData.sundayHours[hour] = status;
        this.dayOfWeek = "sunday";
        break;
      }
    }
    
    this.freeDateService.updateFreeDate(this.weekNumber, this.dayOfWeek, this.freeData).subscribe((responce: IFreeDate) => {
      this.freeData = responce
    });

  }

  createString(week : number, day : number, hour : number, month : String, track : number) : String{
    let s = "";
     s += week;
     s += " ";
     s += day;
     s += " ";
     s += hour;
     s += " ";
     s += month;
     s += " ";
     s += track;
    return s;
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
        this.toastrService.error('Podaj poprawny numer rezerwacji do usunięcia', 'Błąd w usuwaniu');
      }
    }

    logOut(){
          //delete customer from message to unable hacking
          this.router.navigate(['home'])
          this.toastrService.info('Poprawnie', 'Wylogowano');
          this.customerService.changeMessage(null);      
    }

  ngOnInit() {
    this.customerService.currentMessage.subscribe(message => this.customer = message);
    this.reservationToshow =  new Array(0);
    this.weekTable = new Array(0);
    this.showButton = true;
    this.showAdd = true;
    this.weekNumber = 1;
    this.newReservation = {
      firstName : "test",
      lastName : "test",
      number : 1,
      date : "",
      telephone : 444333222,
      trackNumber : 1
    }
    
    //only for development time
    //testing only
    this.customer = {
      userName: "test",
      password: "test",
      reservations: [4, 5]
    }
  
  }
} 
