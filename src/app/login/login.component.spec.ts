import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { ICustomer } from '../customer/customer';
import { FormsModule } from '@angular/forms';
import { RecaptchaModule} from 'ng-recaptcha';
import { CustomerService } from '../customer/customer.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let customer : ICustomer;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, RecaptchaModule, CustomerService ],
      declarations: [ LoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should register', () => {
  //   this.passwordRegister = 'test';
  //   this.passwordRegister2 = 'test';
  //   this.userNameRegister = 'test';
  //   this.register();

  //   this.userNameLogin = 'test';
  //   this.passwordLogin = 'test';
  //   customer = this.login();
  //   expect(customer.userName).toBe('test');
  //   });



  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
