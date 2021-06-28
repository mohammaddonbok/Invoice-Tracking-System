import {Component, OnInit} from '@angular/core';
import {Invoices} from '../invoices';
import {Router} from '@angular/router';
import {InvoiceServiceService} from '../invoice-service.service';
import {ToastrService} from 'ngx-toastr';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {GlobalMethods} from '../globalMethods';
import {UserinfoService} from "../userinfo.service";

@Component({
  selector: 'app-invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.css'],
})
export class InvoiceListComponent implements OnInit {
  page!: number;
  pageNumber: number | any = 0;
  InvoicesList: Invoices[] | any;
  searchValue = '';
  pageSize: number | undefined;
  total: number | undefined;
  subject: Subject<any> = new Subject();
  extractToken: any;

  // tslint:disable-next-line:max-line-length
  constructor(private invoiceList: InvoiceServiceService, private router: Router, public toasterService: ToastrService, private shareable: GlobalMethods , private  user: UserinfoService) {
  }

  ngOnInit(): void {
    if (!this.user.loggedIn()){
      this.router.navigate(['signIn']);
    }
    this.extractToken = this.shareable.getDecodedAccessToken(localStorage.getItem('token'));
    // if(this.extractToken.role === 'Super_User' || 'Auditor') {
    this.getInvoices();
    this.subject.pipe(debounceTime(2000)).subscribe((d) => console.log(d));
  }

  // tslint:disable-next-line:typedef
  getInvoices(pageNum?: any) {
    console.log(pageNum);
    this.invoiceList.searching(pageNum, ).subscribe(data => {
      console.log(data);
      this.InvoicesList = data.content;
      this.pageSize = data.pageable.pageSize;
      this.pageNumber = data.pageable.pageNumber + 1;
      this.total = data.totalElements;

    }, error => console.log(error));
  }

// continue with optional values
  search(event: any) {
    this.searchValue = event.target.value;
    this.invoiceList.searching(undefined, this.searchValue).subscribe(data => {
      this.InvoicesList = data.content;
    });
  }
  // tslint:disable-next-line:typedef
  invoicesWithoutOwner(){
    this.router.navigate(['abstractInvoices']);
  }

  // const searchSubscription = fromEvent(this.searchInput.nativeElement, 'keyup').pipe(
  //   debounceTime(150),
  //   distinctUntilChanged()
  // ); );
  // tslint:disable-next-line:typedef
  pageChange(event: any) {
    this.getInvoices(event);
  }

  // tslint:disable-next-line:typedef
  InvoiceOperatons(id?: number) {
    if (id){
      this.router.navigate(['/CreateInvoice', id]);
    }
    else{
      this.router.navigate(['/CreateInvoice']);

    }
  }
  // createInvoice(id: number){
  // }

  // tslint:disable-next-line:typedef
  viewInvoice(id: number) {
    this.router.navigate(['/ViewComponent', id]);
  }

  // tslint:disable-next-line:typedef
  deleteInvoice(id: number) {
    if (confirm('Are you sure you want to delete?')) {
      this.invoiceList.deleteInvoice(id).subscribe((res) => {
          this.showToastr('Successfully Deleted', 'success');
          this.getInvoices();
        }, (error) => {
          this.showToastr('Something went wrong', 'err');
        }
      );
    }
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
  hasAccess() {
    return this.extractToken.role === environment.permissions_Super || this.extractToken.role === environment.permission_Support;
  }
  // tslint:disable-next-line:typedef
  isAdmin(){
    return this.extractToken.role === environment.permissions_Super;
  }

  // tslint:disable-next-line:typedef
  viewOwner() {
    return this.extractToken.role === environment.permissions_Super || this.extractToken.role === environment.permission_Auditor;
  }
  // tslint:disable-next-line:typedef
  showUsers(){
    this.router.navigate(['ViewUsers']);
  }
}
