import { Injectable } from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppInterceptorService implements HttpInterceptor{

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url === 'http://localhost:8080/logIn'){
      return next.handle(req);
    }
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token' )
    });
    console.log(headers);
    const clone = req.clone(
      {
        headers
      }
    );
    return next.handle(clone);
     }
  // tslint:disable-next-line:typedef

}
