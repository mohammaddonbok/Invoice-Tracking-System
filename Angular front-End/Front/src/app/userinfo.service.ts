import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Register} from './register';
import {AppComponent} from './app.component';


@Injectable({
  providedIn: 'root'
})
export class UserinfoService {
  // private baseUrl = 'http://localhost:8080/api/display/signup' ;
  // private baseUrl = 'http://localhost:8080/api/createUser' ;
  private baseUrl = 'http://localhost:8080/api/invoices';
  headers = new HttpHeaders();

  // toDo: env production and dev (add enviroment variable)
  constructor(private httpClient: HttpClient) {
  }
  createUser(user: Register): Observable<any> {
    const newUser = {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      password: user.password,
      roles: [{id: user.roles}]
    };
    return this.httpClient.post(AppComponent.API_URL + 'createUser', newUser);
  }

  // tslint:disable-next-line:typedef
  getAllUsers(){

    return this.httpClient.get(AppComponent.API_URL + 'displayUsers', { headers: this.headers });
  }
  logInUser(email: string, password: string): Observable<any>  {
    const logUser = {
      email,
      password
    };
    return this.httpClient.post('http://localhost:8080/logIn', logUser);
  }

  // tslint:disable-next-line:typedef
  loggedIn() {
    if (localStorage.getItem('token') != null) {
      return true;
    } else {
      return false;
    }
  }
  changeStatus(id: number , status: boolean): Observable<any>{
    return this.httpClient.put(AppComponent.API_URL + `user/${id}`, status);

  }

  logout() {
    return localStorage.removeItem('token');
  }



// updateInvoiceById(id: number | undefined ){
//
// }
}
