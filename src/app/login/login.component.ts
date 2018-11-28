import { Component, OnInit } from '@angular/core';
import { ICustomer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title : string = "login";
  userName : string = "user";
  password : string = "user";
  phoneNumber : number;
  customer : ICustomer;

  constructor(private service : CustomerService, private router: Router, private toastrService: ToastrService) {
  }

  login(){
      this.service.getCustomer(this.userName).subscribe((res: ICustomer) => {
        this.customer = res;
        this.service.changeMessage(this.customer);

        if(this.password == this.customer.password){
          this.router.navigate(['customer'])
          this.toastrService.success('Witaj ' + this.customer.userName, 'Zalogowano');
         // this.toastrService.info('Hello world!', 'Toastr fun!');
         // this.toastrService.error('Hello world!', 'Toastr fun!');
         // this.toastrService.warning('Hello world!', 'Toastr fun!');
          
        }
      });
  }
  
//TODO
  register(){
    this.service.getCustomer(this.userName).subscribe((res: ICustomer) => {
      this.customer = res;

      if(true){
        this.toastrService.success('Rejestracja przebiegła pomyślnie', 'Udało się');
        
      }
    });
}

  ngOnInit() {
    this.service.currentMessage.subscribe(message => this.customer = message);
  }

}
