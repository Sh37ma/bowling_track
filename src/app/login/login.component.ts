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

  title : string = "logowanie";
  userNameLogin : string;
  passwordLogin : string;
  userNameRegister : string;
  passwordRegister : string;
  passwordRegister2 : string;
  customer : ICustomer;
  customerCheck : string;
  disableButton :boolean = true;

  constructor(private service : CustomerService, private router: Router, private toastrService: ToastrService) {
  }

  login(){
    if(this.userNameLogin != null && this.passwordLogin != null){
      
      this.service.getCustomer(this.userNameLogin).subscribe(
        (response: ICustomer) => {
        this.customer = response;
        console.log(response)
        //base64
        this.passwordLogin = btoa(this.passwordLogin);
        console.log(this.customer.password);
        console.log(this.passwordLogin);
        if(this.passwordLogin == this.customer.password){
          this.service.changeMessage(this.customer);
          this.router.navigate(['customer'])
          this.toastrService.success('Witaj ' + this.customer.userName, 'Zalogowano');
         // info, errorm warning
        }
        else{
          this.toastrService.error('Nie zalogowano', 'Błędne dane');
        }
      },
      error => {this.toastrService.error('Wprowadź ponownie ', 'Dane są niepoprawne');}, // error path
      )
    }
     else{
        this.toastrService.error('Wprowadź dane ', 'Pola logowania są puste');
     }
  }
  
    checkIfCustomerAlreadyExist(){
      this.service.getCustomer(this.userNameRegister).subscribe((response: ICustomer) => {
        if(response.userName != null){
          this.customerCheck = 'taken'
        }
          else{
            this.customerCheck = 'free'
          }
        })
    }

    async delay(ms: number) {
      return new Promise( resolve => setTimeout(resolve, ms) );
    }

    resolved(captchaResponse: string) {
      this.disableButton = false;
    }

    register(){
    if(this.passwordRegister == this.passwordRegister2 && this.userNameRegister != null && this.passwordRegister != null){
      this.customer.userName = this.userNameRegister;
      this.customer.password = this.passwordRegister;
      this.customer.reservations = [];
      
      this.checkIfCustomerAlreadyExist();
      this.delay(1000);
       //clean in view
       this.userNameRegister = "";
       this.passwordRegister = "";
       this.passwordRegister2 = "";

       this.customer.password = btoa(this.customer.password); 
       console.log("Encripted: " + this.customer.password); 
       console.log("Decripted: " + atob(this.customer.password)); 
       

      if(this.customerCheck != 'taken'){
        this.service.addCustomer(this.customer).subscribe(responce => this.customer = responce);
        this.toastrService.success('Zaloguj się nowymi danymi', 'Rejestracja przebiegła pomyślnie');
      }
      else{
        this.toastrService.error('Taaki użytkownik już istnieje', 'Rejestracja nie powiodła się');
      }     
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
