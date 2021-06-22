import {Component} from '@angular/core';
import {UserinfoService} from './userinfo.service';
import {Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],

})
export class AppComponent {
  constructor(public log: UserinfoService , public router: Router ) {
  }
  static API_URL = 'http://localhost:8080/api/';


  isloggedin(){
    if (this.log.loggedIn()){
      return true;
    }
    return false;
  }
}


