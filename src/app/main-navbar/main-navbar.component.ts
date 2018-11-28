import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main-navbar',
  templateUrl: './main-navbar.component.html',
  styleUrls: ['./main-navbar.component.css']
})
export class MainNavBarComponent implements OnInit {

  currentDate = new Date();
  dataMessage: string = this.currentDate.toDateString() + ' ' + this.currentDate.toLocaleTimeString();;
  
  constructor() {
    setInterval(()=>{
      let currentDate = new Date();
      this.dataMessage = currentDate.toDateString() + ' ' + currentDate.toLocaleTimeString();
    }, 1000)
   }

  ngOnInit() {
  }


}
