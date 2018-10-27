import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome-text',
  templateUrl: './welcome-text.component.html',
  styleUrls: ['./welcome-text.component.css']
})
export class WelcomeTextComponent implements OnInit {

  constructor() { }

  title = 'Bowling World';
  
  imageUrlArray =
  [
    '../assets/img/1.jpg',
    '../assets/img/2.jpg',
    '../assets/img/3.jpg'
  ];
  
  ngOnInit() {
  }

}
