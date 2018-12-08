import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MainNavBarComponent } from './main-navbar/main-navbar.component';
import { ScrollToModule } from '@nicky-lenaers/ngx-scroll-to';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AngularFontAwesomeModule } from 'angular-font-awesome';

import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SlideshowModule } from 'ng-simple-slideshow';
import { WelcomeTextComponent } from './welcome-text/welcome-text.component';
import { OfferComponent } from './offer/offer.component';
import { PriceComponent } from './price/price.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ContactComponent } from './contact/contact.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CustomerComponent } from './customer/customer.component';
import { CustomerService } from './customer/customer.service';
import { ReservationService } from './reservation/reservation.service';
import { FreeDateService } from './freeDate/free-date.service';
import { AuthInterceptor } from './auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    MainNavBarComponent,
    MainPageComponent,
    WelcomeTextComponent,
    OfferComponent,
    PriceComponent,
    GalleryComponent,
    ReservationComponent,
    ContactComponent,
    LoginComponent,
    HomeComponent,
    CustomerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    SlideshowModule,
    FormsModule,
    RouterModule.forRoot([
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'customer',
        component: CustomerComponent
      },
      {
        path: '', redirectTo: '/home', 
        pathMatch: 'full'
      },
      {
        path: '**', redirectTo: '/home', 
        pathMatch: 'full'
      }
    ]),
    ScrollToModule.forRoot(),
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added
    AngularFontAwesomeModule
  ],
  providers: [
    CustomerService,
    ReservationService,
    FreeDateService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
