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
  userNameLogin : string;
  passwordLogin : string;
  userNameRegister : string;
  passwordRegister : string;
  passwordRegister2 : string;
  customer : ICustomer;

  constructor(private service : CustomerService, private router: Router, private toastrService: ToastrService) {
  }

  login(){
    if(this.userNameLogin != null && this.passwordLogin != null){
      //TODO chandle error when user not found
      this.service.getCustomer(this.userNameLogin).subscribe((response: ICustomer) => {
        this.customer = response;
        console.log(response)
        if(this.passwordLogin == this.customer.password){
          this.service.changeMessage(this.customer);
          this.router.navigate(['customer'])
          this.toastrService.success('Witaj ' + this.customer.userName, 'Zalogowano');
         // info, errorm warning
        }
      })
    }
     else{
        this.toastrService.error('Wprowadź dane ', 'Pola logowania są puste');
     }
  }
  

  register(){
    if(this.passwordRegister == this.passwordRegister2 && this.userNameRegister != null && this.passwordRegister != null){
      this.customer.userName = this.userNameRegister;
      this.customer.password = this.passwordRegister;
      this.customer.reservations = [];
      
      this.service.addCustomer(this.customer).subscribe(responce => this.customer = responce);
      this.toastrService.success('Zaloguj się nowymi danymi', 'Rejestracja przebiegła pomyślnie');
    }
    else{
      this.toastrService.error('Hasła muszą się zgadzać i dane nie mogą być puste', 'Upsss coś poszło nie tak - spróbuj jeszcze raz');
    }
  }

  ngOnInit() {
    this.service.currentMessage.subscribe(message => this.customer = message);
    this.customer = {} as ICustomer ;
  }
}
