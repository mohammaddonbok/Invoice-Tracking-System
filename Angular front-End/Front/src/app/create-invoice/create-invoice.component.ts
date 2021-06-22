import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserinfoService} from '../userinfo.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Invoices} from '../invoices';
import {Items} from '../items';
import {InvoiceServiceService} from '../invoice-service.service';
import {Subscription} from 'rxjs';
import {environment} from '../../environments/environment';
import {GlobalMethods} from '../globalMethods';
import {Register} from "../register";

@Component({
  selector: 'app-create-invoice',
  templateUrl: './create-invoice.component.html',
  styleUrls: ['./create-invoice.component.css']
})
export class CreateInvoiceComponent implements OnInit, OnDestroy {
  id: number | undefined;
  invoiceItems: FormArray | any;
  invoice: FormGroup | any;
  items: FormGroup | any;
  inv: Invoices = new Invoices();
  itemObject: Items = new Items();
  createdItems: Items[] = [];
  userId: number | undefined;
  customObsSubscription: Subscription | undefined;
  ListOfUsers: object | any;
  extractToken: any;
  status = 'create';
  InvoiceOwner: Register | any;
  edit = false;
  itemId: number | undefined;
  email: string | undefined;
  selectedFile: File | any;

  constructor(public log: UserinfoService, private cdr: ChangeDetectorRef, private shareable: GlobalMethods, private user: UserinfoService, private route: ActivatedRoute, private formBuilder: FormBuilder, private newInvoice: InvoiceServiceService, private router: Router) {
  }

  ngOnDestroy(): void {
    this.customObsSubscription?.unsubscribe();
  }

  // tslint:disable-next-line:typedef
  ngOnInit() {
    if (!this.log.loggedIn()) {
      this.router.navigate(['signIn']);
    }
    this.extractToken = this.shareable.getDecodedAccessToken(localStorage.getItem('token'));
    if (this.route.snapshot.params.id) {
      this.id = this.route.snapshot.params.id;
      this.status = 'edit';
      this.newInvoice.getInvoiceById(this.id).subscribe(data => {
        console.log(data);
        this.inv = data;
        this.InvoiceOwner = data.owner;
        this.createdItems = data.invoiceItems;
      }, error => console.log(error));
    }
    this.user.getAllUsers().subscribe(data => {
      this.ListOfUsers = data;
    }, error => console.log(error));
    this.invoice = this.formBuilder.group({
      customerName: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z ].{3,20}')]),
      totalPrice: new FormControl(''),
      invoiceDate: new FormControl('', Validators.required),
      invoiceNumber: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
      invoiceItems: new FormControl(''),
      owner: new FormControl(null),
      // invoicePicture: new FormControl('')
    });
    this.items = this.formBuilder.group({
      itemName: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z ].{3,20}')]),
      pricePerOneItem: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
      quantity: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')])
    });
  }

  // tslint:disable-next-line:typedef
  addItemToArry() {
    if (this.items.valid) {
      if (!this.edit) {
        const pushedItems: Items = new Items();
        pushedItems.itemName = this.items.get('itemName').value;
        pushedItems.pricePerOneItem = this.items.get('pricePerOneItem').value;
        pushedItems.quantity = this.items.get('quantity').value;
        this.createdItems.push(pushedItems);
        this.calculateToatalPrice('sum');
        this.items.get('itemName').reset();
        this.items.get('pricePerOneItem').reset();
        this.items.get('quantity').reset();
      } else {
        if (this.edit) {
          console.log(this.createdItems);
          // @ts-ignore
          this.createdItems[this.itemId].itemName = this.items.get('itemName').value;
          // @ts-ignore
          this.createdItems[this.itemId].pricePerOneItem = this.items.get('pricePerOneItem').value;
          // @ts-ignore
          this.createdItems[this.itemId].quantity = this.items.get('quantity').value;
          this.calculateToatalPrice('sum');
          this.items.get('itemName').reset();
          this.items.get('pricePerOneItem').reset();
          this.items.get('quantity').reset();
          this.edit = false;
          this.itemId = undefined;
        }
      }
    } else {
      for (const x in this.items.controls) {
        this.items.controls[x].markAsTouched();
      }
    }
  }


  // tslint:disable-next-line:typedef
  getErrorMessage(controlName: string, validationType: string) {
    switch (validationType) {
      case 'required':
        return this.invoice.get(controlName)!.hasError('required') ? 'You must enter a value' : '';
      case 'pattern':
        return this.invoice.get(controlName)!.hasError('pattern') ? 'Only letter between 3 to 20 character' : '';
      default :
        return 'undefined type';
    }
  }

  // tslint:disable-next-line:typedef
  getItemsError(controlName: string, validationType: string) {
    switch (validationType) {
      case 'required':
        return this.items.get(controlName)!.hasError('required') ? 'You must enter a value' : '';
      case 'patternNumber':
        return this.items.get(controlName)!.hasError('pattern') ? 'Only numbers ' : '';
      case 'patternChar':
        return this.items.get(controlName)!.hasError('pattern') ? 'Only character more than 3 character' : '';
      case 'patternCharInvoice':
        return this.invoice.get(controlName)!.hasError('pattern') ? 'Only numbers' : '';
      default :
        return 'undefined type';
    }
  }

  // tslint:disable-next-line:typedef
  getValue(controlName: string, status: string) {
    if (status === 'create') {
      switch (controlName) {
        case 'h1':
          return 'Add Invoice';
        case 'customerName':
          return 'Customer Name';
        case 'totalPrice':
          return 'Total Price';
        case 'invoiceDate':
          return 'Invoice Date';
        case 'owner':
          return 'Choose Owner';
        case 'invoiceNumber':
          return 'Invoice Number';
        case 'itemName':
          return 'Item Name';
        case 'pricePerOneItem':
          return 'Unit Price';
        case 'quantity':
          return 'Quantity';

      }
    } else if (status === 'edit') {
      switch (controlName) {
        case 'h1':
          return 'Edit Invoice';
        case 'customerName':
          return this.inv.customerName;
        case 'totalPrice':
          return this.inv.totalPrice;
        case 'invoiceDate':
          return this.inv.invoiceDate;
        case 'owner':
          return this.InvoiceOwner.firstName;
        case 'invoiceNumber':
          return this.inv.invoiceNumber;
        case 'itemName':
          return 'Item Name';
        case 'pricePerOneItem':
          return 'Unit Price';
        case 'quantity':
          return 'Quantity';

      }
    }
    return null;
  }

  // onFileSelected(event: any) {
  //   if (event.target.files.length > 0) {
  //     this.selectedFile = event.target.files[0] as File;
  //     const file = new FormData();
  //     this.invoice.invoicePicture = file.append('file', this.selectedFile, this.selectedFile.name);
  //
  //   }
  // }

  saveInvoice() {
    const obj = this.invoice.controls.invoiceDate.value;
    const myJSON = obj.toString();
    this.inv.customerName = this.invoice.controls.customerName.value;
    this.inv.totalPrice = this.invoice.totalPrice;
    this.inv.invoiceDate = myJSON;
    this.inv.invoiceNumber = this.invoice.controls.invoiceNumber.value;
    this.inv.invoiceItems = this.createdItems;
    // this.inv.invoicePicture = this.invoice.controls.invoicePicture.value;

    if (this.isAdmin()) {
      this.email = this.invoice.controls.owner.value;
    } else {
      this.email = this.extractToken.sub;
    }
    if (this.route.snapshot.params.id) {
      this.id = this.route.snapshot.params.id;
      this.customObsSubscription = this.newInvoice.createInvoices(this.inv, this.email, this.id).subscribe(data => {
        this.goToInvoiceList();
      }, error => console.log(error));
    } else {
      this.customObsSubscription = this.newInvoice.createInvoices(this.inv, this.email).subscribe(data => {
        this.goToInvoiceList();
        // this.handleItems();
      }, error => console.log(error));
    }
    if (this.status === 'edit') {
      this.status = 'create';
    }
  }

  onSubmit() {
    if (this.invoice.valid === true) {
      this.saveInvoice();
      return;
    }
    for (const x in this.invoice.controls) {
      this.invoice.controls[x].markAsTouched();
    }
  }

  goToInvoiceList() {
    this.router.navigate(['/invoiceList']);
  }

  onDelete(i: number) {
    this.createdItems.splice(i, 1);
    this.calculateToatalPrice('sub');
  }

  calculateToatalPrice(type: string) {
    const prices = this.createdItems.map((p) => p.pricePerOneItem);
    const quantities = this.createdItems.map((q) => q.quantity);
    let sum = 0;
    if (type === 'sum') {
      for (let i = 0; i < prices.length; i++) {
        sum += prices[i] * quantities[i];
      }

    }
    this.invoice.totalPrice = sum;
  }

  isAdmin() {
    if (this.extractToken.role === environment.permissions_Super && this.log.loggedIn()) {
      return true;
    }
    return false;
  }

  // tslint:disable-next-line:typedef
  getStatus() {
    return this.status;
  }

  generateEdit(id: number | undefined) {
    this.itemId = id;
    return this.edit = true;
  }

  getId() {
    return this.itemId;

  }

  getClickedValue(id: number | undefined, controlName: string) {
    if (id === undefined) {
      return;
    } else {
      switch (controlName) {
        case 'itemName':
          return this.createdItems[id].itemName;
        case 'pricePerOneItem':
          return this.createdItems[id].pricePerOneItem;
        case 'quantity':
          return this.createdItems[id].quantity;
      }
    }
    return null;
  }

  isItEdit() {
    if (this.edit === true) {
      return true;
    }
    return false;
  }

  onEdit() {
    if (this.edit === true) {
      return 'Update Item';
    } else {
      return 'Add Item';
    }
  }
}

