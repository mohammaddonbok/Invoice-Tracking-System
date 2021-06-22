import { Component, OnInit } from '@angular/core';
import {UserinfoService} from '../userinfo.service';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';
import {GlobalMethods} from '../globalMethods';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  constructor( public log: UserinfoService , public router: Router ,  private shareable: GlobalMethods) { }
  extractToken: any ;
  ngOnInit(): void {
    this.extractToken = this.shareable.getDecodedAccessToken(localStorage.getItem('token'));
  }

  getFirstName(){
    return this.extractToken.name;
  }

  // tslint:disable-next-line:typedef
  getRoleString(){
    return this.extractToken.role;
  }
  // tslint:disable-next-line:typedef
  isAdmin(){
    if (this.extractToken.role === environment.permissions_Super && this.log.loggedIn()){
      return true;
    }
    return false;
  }
  isLoggedIn(){
    if (this.log.loggedIn()){
      return true;
    }
    return false;
  }
  // tslint:disable-next-line:typedef
  logOut(){
    this.log.logout();
    this.goToSignIn();
  }
  goToSignUp(){
    if (this.isAdmin()){
      this.router.navigate(['registration']);
    }
  }
  // tslint:disable-next-line:typedef
  goToSignIn() {
    this.router.navigate(['/signIn']);
  }

}
