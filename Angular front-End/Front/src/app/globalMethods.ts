import jwt_decode from 'jwt-decode';
import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class GlobalMethods{
  // tslint:disable-next-line:typedef
  getDecodedAccessToken(token: any) {
    try{
      return jwt_decode(token);
    }
    catch (Error){
      return null;
    }
  }
}
