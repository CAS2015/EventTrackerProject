import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BoxService } from './services/box.service';
import { BoxListComponent } from './components/box-list/box-list.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { LocationListComponent } from './components/location-list/location-list.component';
import { FormsModule } from '@angular/forms';
import { LocationService } from './services/location.service';
import { AuthService } from './services/auth.service';


@NgModule({
  declarations: [
    AppComponent,
    BoxListComponent,
    NavigationComponent,
    HomeComponent,
    AboutComponent,
    ContactComponent,
    NotFoundComponent,
    RegisterComponent,
    LoginComponent,
    LogoutComponent,
    LocationListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    BoxService,
    LocationService,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
