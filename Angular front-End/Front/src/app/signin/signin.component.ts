import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserinfoService} from '../userinfo.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  errors: string | undefined;
  profileForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  constructor(private userService: UserinfoService, private router: Router , private toasterService: ToastrService) {
  }

  ngOnInit(): void {
    if (localStorage.getItem('token') !== null){
      this.router.navigate(['invoiceList']);
    }
  }

  // this.getErrorMessage('name', 'required');
  getErrorMessage(controlName: string, validationType: string) {
    switch (validationType) {
      case 'email':
        return this.profileForm.get(controlName)!.hasError('email') ? 'Not a valid email' : '';
      case 'required':
        return this.profileForm.get(controlName)!.hasError('required') ? 'You must enter a value' : '';
      case 'pattern':
        return this.profileForm.get(controlName)!.hasError('pattern') ? 'Only letter between 3 to 20 character' : '';
      default :
        return 'undefined type';
    }
  }

  goToInvoiceList() {
    this.router.navigate(['/invoiceList']);
  }

  onSubmit() {
    console.warn(this.profileForm.value);
    this.userService.logInUser(this.profileForm.controls.email.value, this.profileForm.controls.password.value)
      .subscribe(res => {
        console.log(res);
        localStorage.setItem('token' , '' + res.token);
        this.goToInvoiceList();
        }, a => {
        this.responseToaster(a.error.message);
  });

}
  // tslint:disable-next-line:typedef
  responseToaster( msg: string){
    this.toasterService.error(msg);
  }
}
