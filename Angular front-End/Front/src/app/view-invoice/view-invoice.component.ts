import { Component, OnInit } from '@angular/core';
import {InvoiceServiceService} from '../invoice-service.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Invoices} from '../invoices';

@Component({
  selector: 'app-view-invoice',
  templateUrl: './view-invoice.component.html',
  styleUrls: ['./view-invoice.component.css']
})
export class ViewInvoiceComponent implements OnInit {
  id: number | undefined;
  currentInvoice: Invoices = new Invoices();
  constructor(private invoiceView: InvoiceServiceService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params.id;
    this.invoiceView.getInvoiceById(this.id).subscribe(data => {
      this.currentInvoice = data;
    });
  }
  print(){
    window.print();
  }
}
