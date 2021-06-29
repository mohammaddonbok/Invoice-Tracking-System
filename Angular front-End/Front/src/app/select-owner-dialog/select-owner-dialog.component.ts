import {Component, Inject, OnInit} from '@angular/core';
import {UserinfoService} from '../userinfo.service';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {InvoiceServiceService} from "../invoice-service.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

export interface DialogData {
  email: string;
  listOfInvoice: object;
}

@Component({
  selector: 'app-select-owner-dialog',
  templateUrl: './select-owner-dialog.component.html',
  styleUrls: ['./select-owner-dialog.component.css']
})
export class SelectOwnerDialogComponent implements OnInit {
  ListOfUsers: object | any;
  invoiceOwner: FormGroup | any;
  email: string | any;
  invoiceList: object | any;

  constructor(  private router: Router,
                public dialogRef: MatDialogRef<any>,
                private invoice: InvoiceServiceService,
                private user: UserinfoService,
                @Inject(MAT_DIALOG_DATA) public data: DialogData, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.user.getAllUsers().subscribe(data => {
      this.ListOfUsers = data;
      this.invoiceList = this.data;
      console.log(this.data);
    }, error => console.log(error));
    this.invoiceOwner = this.formBuilder.group({
      owner: new FormControl('')
    });
  }

  onSubmit() {
    this.email = this.invoiceOwner.controls.owner.value;
    this.invoice.attachOwner(this.email, this.data).subscribe(data =>     this.dialogRef.close(true) );
  }

}
