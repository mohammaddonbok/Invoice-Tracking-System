import {Component, OnDestroy, OnInit} from '@angular/core';
import {InvoiceServiceService} from '../invoice-service.service';
import {Invoices} from '../invoices';
import {MatDialog} from '@angular/material/dialog';
import {SelectOwnerDialogComponent} from '../select-owner-dialog/select-owner-dialog.component';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-abstract-invoices',
  templateUrl: './abstract-invoices.component.html',
  styleUrls: ['./abstract-invoices.component.css']
})
export class AbstractInvoicesComponent implements OnInit, OnDestroy {
  invoicesList: Invoices | any;
  invoiceObject: Array<any> = [];
  selected = false;
  subs: Subscription | undefined;

  constructor(private router: Router, private invoiceServies: InvoiceServiceService, public dialog: MatDialog) {

  }

  ngOnInit(): void {
    this.invoiceServies.getAbstractInvoices().subscribe(data => {
      this.invoicesList = data;
      this.invoiceObject = this.invoicesList.map((v: any) => Object.assign(v, {isActive: this.selected}));
      console.log(this.invoiceObject);
    });
  }

  setAll(selected: boolean) {
    console.log(selected);
    this.selected = selected;
    this.invoiceObject.forEach(t => t.isActive = selected);
    console.log(this.invoiceObject);
  }

  selectOne(checked: boolean, index: number) {
    console.log(checked);
    this.invoiceObject[index].isActive = checked;
    console.log(this.invoiceObject);
  }

  openDialog() {
    const dialogRef = this.dialog.open(SelectOwnerDialogComponent, {data: this.invoiceObject});

    dialogRef.afterClosed().subscribe(result => {
this.router.navigate(['invoiceList']);
    });
  }

  ngOnDestroy() {
    this.subs?.unsubscribe();
  }
}
