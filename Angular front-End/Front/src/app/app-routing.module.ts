import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupComponent} from './signup/signup.component';
import {InvoiceListComponent} from './invoice-list/invoice-list.component';
import {SigninComponent} from './signin/signin.component';
import {CreateInvoiceComponent} from './create-invoice/create-invoice.component';
import {ViewInvoiceComponent} from './view-invoice/view-invoice.component';
import {NavComponent} from './nav/nav.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ViewUsersComponent} from './view-users/view-users.component';
import {AbstractInvoicesComponent} from "./abstract-invoices/abstract-invoices.component";

const routes: Routes = [
  // { path: '**', redirectTo: 'signIn'},
  {path : 'registration' , component: SignupComponent  },
  {path : 'invoiceList', component: InvoiceListComponent},
  {path : '' , redirectTo : 'signIn' , pathMatch: 'full' },
  {path : 'signIn' , component : SigninComponent},
  {path: 'CreateInvoice/:id' , component: CreateInvoiceComponent},
  {path: 'CreateInvoice' , component: CreateInvoiceComponent},
  {path: 'ViewComponent/:id' , component: ViewInvoiceComponent},
  {path: 'ViewUsers' , component: ViewUsersComponent},
  {path: 'nav', component: NavComponent, outlet: 'nav' },
  {path: 'abstractInvoices' , component: AbstractInvoicesComponent},
  {path: '**', component: NotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
