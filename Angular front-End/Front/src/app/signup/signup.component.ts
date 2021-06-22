import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Register} from '../register';
import {UserinfoService} from '../userinfo.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {environment} from '../../environments/environment';
import {GlobalMethods} from '../globalMethods';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  errors = '';
  registerForm = new FormGroup({
    firstName: new FormControl('', [Validators.pattern('[a-zA-Z ].{3,20}'), Validators.required]),
    lastName: new FormControl('', [Validators.pattern('[a-zA-Z ].{3,20}'), Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    role: new FormControl('' , Validators.required)
  });
  // email = new FormControl('', [Validators.required, Validators.email]);
  // firstName = new FormControl('', Validators.pattern('[a-zA-Z ].{3,20}'));

  user: Register = new Register();
  extractToken: any ;

  getErrorMessage(controlName: string, validationType: string) {
    switch (validationType) {
      case 'email':
        return this.registerForm.get(controlName)!.hasError('email') ? 'Not a valid email' : '';
      case 'required':
        return this.registerForm.get(controlName)!.hasError('required') ? 'You must enter a value' : '';
      case 'pattern':
        return this.registerForm.get(controlName)!.hasError('pattern') ? 'Only letter between 3 to 20 character' : '';
      default :
        return 'undefined type';
    }
  }


  constructor(private userService: UserinfoService, private shareable: GlobalMethods, private router: Router , public toasterService: ToastrService) {
  }

  ngOnInit(): void {
    if (localStorage.getItem('token') == null){
      this.router.navigate(['signIn']);
    }
    this.extractToken = this.shareable.getDecodedAccessToken(localStorage.getItem('token'));
    if (!this.isAdmin()){
      this.router.navigate(['signIn']);
    }
  }
  isAdmin(){
    if (this.extractToken.role === environment.permissions_Super && this.userService.loggedIn()){
      return true;
    }
    return false;
  }

  // tslint:disable-next-line:typedef
  goToInvoiceList() {
    this.router.navigate(['/invoiceList']);
  }
  // tslint:disable-next-line:typedef
  goToSignUp(){
    this.router.navigate(['/registration']);
  }

  // tslint:disable-next-line:typedef
  saveUser() {
    this.user.clear();
    this.user.firstName = this.registerForm.controls.firstName.value;
    this.user.lastName = this.registerForm.controls.lastName.value;
    this.user.email = this.registerForm.controls.email.value;
    this.user.password = this.registerForm.controls.password.value;
    this.user.roles = this.registerForm.controls.role.value;
    this.userService.createUser(this.user).subscribe(data => {
      this.goToInvoiceList();
    }, a => {
      this.responseToaster(a.error.message);
    } ) ;
  }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.registerForm.valid ) {
      if (this.registerForm.controls.role.value !== '2' && this.registerForm.controls.role.value !== '3') {
        this.responseToaster('Please refresh the page');
        this.goToSignUp();
        return;
      }
      this.saveUser();
      return;
    }
    for (const x in this.registerForm.controls){
      this.registerForm.controls[x].markAsTouched();
    }
  }

  // tslint:disable-next-line:typedef
  responseToaster( msg: string){
    this.toasterService.error(msg);
  }

}
