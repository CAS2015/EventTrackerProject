import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError} from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Box } from '../models/box';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BoxService {

  // private boxes: Box[] = [];
  private baseUrl = 'http://localhost:8084/api/';

  ///////////////////////CHANGE THIS LATER!!!!!!//////////////////////
  private url: string;
  ////////////////////////////////////////////////////////////////////

  constructor(private http: HttpClient,
    private authService: AuthService, private router: Router) { }

  index(locId: number): Observable<Box[]> {
    this.url = `${this.baseUrl}locations/${locId}/boxes`;
    console.log(this.url);

    return this.http.get<Box[]>(this.url,this.credentials()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('BoxService.index(): error retrieving boxes: ' + err);
      })
    );
  }


  create(data: Box) {
    let httpOptions = this.credentials();

    return this.http.post<Box>(this.url, data, httpOptions)
    .pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('BoxService.create(): error creating box: ' + err);
      })
    );

  };

  update(box: Box) {
    let httpOptions = this.credentials();

    return this.http.put<Box>(this.url +'/'+ box.id, box, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('BoxService.update(): error updating box: ' + err);
      })
    );
  }

  destroy(id)  {
    let httpOptions = this.credentials();
    return this.http.delete<void>(this.url +'/'+ id, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('BoxService.destroy(): error destroying box: ' + err);
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
