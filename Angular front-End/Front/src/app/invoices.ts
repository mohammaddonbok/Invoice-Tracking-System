// import {Items} from './items';

import {Items} from './items';

export class Invoices {
  id!: number ;
  customerName!: string ;
  totalPrice!: number;
  invoiceDate!: string ;
  invoiceNumber!: number ;
  owner!: string ;
  invoicePicture!: FormData;
  invoiceItems!: Array<Items>;
}
