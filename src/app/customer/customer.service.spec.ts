import { TestBed } from '@angular/core/testing';
import { CustomerService } from './customer.service';
import { ICustomer } from '../customer/customer';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('CustomerService', () => {
  let service: CustomerService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    
    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [ CustomerService ]
    })

    service = TestBed.get(CustomerService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() =>{
    httpMock.verify(); 
  })

  it('should get customer', () => {
    const testingCustomer : ICustomer = {
      userName: "test",
      password: "",
      reservations: []
    }

    service.getCustomer(testingCustomer.userName).subscribe(customer =>{
      expect(customer.userName).toBe('test');
      expect(customer.password).toBe('test');
      expect(customer.reservations.length).toBe(2);
    })

    const request = httpMock.expectOne(`api/customers/test`);

    expect(request.request.method).toBe('GET');

  });


  it('should change customer', () => {

    let originalCustomerName = 'test';
    const testingCustomer : ICustomer = {
      userName: "test2",
      password: "test2",
      reservations: [1, 2, 3]
    }

    service.updateCustomer(originalCustomerName, testingCustomer).subscribe(customer =>{
      expect(customer.userName).toBe('');
      expect(customer.password).toBe('');
      expect(customer.reservations.length).toBe(3);
    })

    const request = httpMock.expectOne(`api/customers/test`);

    expect(request.request.method).toBe('PUT');

  });
})



