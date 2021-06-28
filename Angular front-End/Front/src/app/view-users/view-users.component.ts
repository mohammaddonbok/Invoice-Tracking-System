import {Component, OnInit} from '@angular/core';
import {UserinfoService} from '../userinfo.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {ThemePalette} from '@angular/material/core';
import {MatDialog} from "@angular/material/dialog";


@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {
  ListOfUsers: object | any;
  color: ThemePalette = 'accent';
  checked: boolean | undefined;
  disabled = false;
  constructor( private user: UserinfoService , private router: Router, private toasterService: ToastrService  ) { }

  ngOnInit(): void {
    this.user.getAllUsers().subscribe(data => {
      this.ListOfUsers = data;
    }, error => console.log(error));
  }
  // tslint:disable-next-line:typedef
  showToastr(message: string, type: string) {
    if (type === 'success') {
      this.toasterService.success(message);
    } else {
      if (type === 'err') {
        this.toasterService.error(message);
      }
    }

  }
  // tslint:disable-next-line:typedef
  status(state: boolean){
    if (!state){
      return 'Non Active';
    }
    return 'Active User';
  }
  // tslint:disable-next-line:typedef
  changeStatus(id: number , status: boolean) {
    if (status) {
      if (confirm('Are you sure you want to deactivate the user?')) {
        this.user.changeStatus(id, false).subscribe((res) => {
            this.showToastr('Successfully Deactivated', 'success');
            this.ngOnInit();
          }, (error) => {
            this.showToastr('Something went wrong', 'err');
            this.ngOnInit();
          }
        );
      }
      this.ngOnInit();
    } else {
      if (confirm('Are you sure you want to activate the user?')) {
        this.user.changeStatus(id, true).subscribe((res) => {
            this.showToastr('Successfully Activated', 'success');
            this.ngOnInit();

          }, (error) => {
            this.showToastr('Something went wrong', 'err');
          }
        );
      }
      this.ngOnInit();
    }

  }
}

