import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MainInterfaceComponent } from './main-interface/main-interface.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SlideshowModule } from 'ng-simple-slideshow';
import { WelcomeTextComponent } from './welcome-text/welcome-text.component'; //slider

@NgModule({
  declarations: [
    AppComponent,
    MainInterfaceComponent,
    MainPageComponent,
    WelcomeTextComponent
  ],
  imports: [
    BrowserModule,
    SlideshowModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
