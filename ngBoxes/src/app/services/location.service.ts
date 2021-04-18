
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { Location } from '../models/location';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = 'http://localhost:8084/';
  private url = this.baseUrl + 'api/locations';

  constructor(private http: HttpClient,
    private authService: AuthService, private router: Router) { }

  index(): Observable<Location[]> {
    return this.http.get<Location[]>(this.url,this.credentials()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('LocationService.index(): error retrieving locations: ' + err);
      })
    );
  };

  show(locId): Observable<Location> {

    let httpOptions = this.credentials();


    return this.http.get<Location>(this.url + '/' + locId, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('LocationService.show(): error retrieving location: ' + err);
      })
    );

  };

  create(data: Location) {
    let httpOptions = this.credentials();

    return this.http.post<Location>(this.url, data, httpOptions)
    .pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('LocationService.create(): error creating location: ' + err);
      })
    );

  };

  update(location: Location) {
    let httpOptions = this.credentials();

    return this.http.put<Location>(this.url +'/'+ location.id, location, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('LocationService.update(): error updating location: ' + err);
      })
    );
  }

  destroy(id)  {
    let httpOptions = this.credentials();
    return this.http.delete<void>(this.url +'/'+ id, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('LocationService.destroy(): error destroying location: ' + err);
      })
    );


  }

  private credentials() {
    // Make credentials
    const credentials = this.authService.getCredentials();
    // Send credentials as Authorization header (this is spring security convention for basic auth)
    let httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    }
    return httpOptions;
  }


}
