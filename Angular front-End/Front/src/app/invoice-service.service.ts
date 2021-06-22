import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Invoices} from './invoices';
import {Observable} from 'rxjs';
import {AppComponent} from './app.component';

@Injectable({
  providedIn: 'root'
})
export class InvoiceServiceService {
  private baseUrl = 'http://localhost:8080/api/invoices';
  constructor(private httpClient: HttpClient ) {
  }

  createInvoices( invoice1: Invoices , owner: string | any , id?: number ): Observable<any> {
    const headers = new HttpHeaders().set('Authorization' , 'Bearer ' + localStorage.getItem('token') );
    const params = new HttpParams().set('email' , owner);
    if (id){
      return this.httpClient.put(AppComponent.API_URL + `invoice/${id}`, invoice1 , {headers , params});
    }else {
      return this.httpClient.post(AppComponent.API_URL + 'createInvoice', invoice1 , {headers , params});
    }
  }
  getInvoiceById(id: number | undefined): Observable<Invoices> {
    return this.httpClient.get<Invoices>( AppComponent.API_URL + `invoice/${id}`);
  }

  getInvoicesList(): Observable<Invoices[]> {
    return this.httpClient.get<Invoices[]>(`${this.baseUrl}`);
    // toDo: handel errors
  }
  deleteInvoice(id: number): Observable<any>{
     return this.httpClient.delete(AppComponent.API_URL + `invoice/${id}`);
  }
  searching(pageNum?: number | any, customerName?: string | any  ): Observable<any> {
    const headers = new HttpHeaders().set('Authorization' , 'Bearer ' + localStorage.getItem('token'));
    let params;
    if (customerName){   params = new HttpParams()
      .set('customerName', customerName);
    }else
    if (pageNum){
      params = new HttpParams().set('pageNum' , (pageNum - 1).toString());
    }else {
      if (!pageNum && !customerName) {
        params = new HttpParams().set('pageNum', '0');
      }
    }
    return this.httpClient.get<Invoices[]>(AppComponent.API_URL + 'pageAble' , {headers , params});
  }



  getOwnerInvoices(): Observable<any> {
    const headers = new HttpHeaders().set('Authorization' , 'Bearer ' + localStorage.getItem('token'));
    return this.httpClient.get<Invoices[]>(AppComponent.API_URL + 'OwnerInvoices' , {headers });
  }


}
