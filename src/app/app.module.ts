import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MainNavBarComponent } from './main-navbar/main-navbar.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SlideshowModule } from 'ng-simple-slideshow';
import { WelcomeTextComponent } from './welcome-text/welcome-text.component';
import { OfferComponent } from './offer/offer.component';
import { PriceComponent } from './price/price.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ContactComponent } from './contact/contact.component'; //slider

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
    ContactComponent
  ],
  imports: [
    BrowserModule,
    SlideshowModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
