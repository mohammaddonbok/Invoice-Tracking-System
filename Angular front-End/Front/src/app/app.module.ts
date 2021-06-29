import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SigninComponent} from './signin/signin.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SignupComponent} from './signup/signup.component';
import {NgbModule, NgbNavbar} from '@ng-bootstrap/ng-bootstrap';
import {MatInputModule} from '@angular/material/input';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatIconModule} from '@angular/material/icon';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {InvoiceListComponent} from './invoice-list/invoice-list.component';
import {NgxPaginationModule} from 'ngx-pagination';
import {CreateInvoiceComponent} from './create-invoice/create-invoice.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFileUploadModule} from 'angular-material-fileupload';
import {ToastrModule} from 'ngx-toastr';
import {ViewInvoiceComponent} from './view-invoice/view-invoice.component';
import {NavComponent} from './nav/nav.component';
import {MatSelectModule} from '@angular/material/select';
import {NotFoundComponent} from './not-found/not-found.component';
import {MatButtonModule} from '@angular/material/button';
import {ViewUsersComponent} from './view-users/view-users.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {AppInterceptorService} from './app-interceptor.service';
import { AbstractInvoicesComponent } from './abstract-invoices/abstract-invoices.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {SelectionModel} from '@angular/cdk/collections';
import { SelectOwnerDialogComponent } from './select-owner-dialog/select-owner-dialog.component';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    InvoiceListComponent,
    CreateInvoiceComponent,
    ViewInvoiceComponent,
    NavComponent,
    NotFoundComponent,
    ViewUsersComponent,
    AbstractInvoicesComponent,
    SelectOwnerDialogComponent,
    ConfirmationDialogComponent,

  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    MatOptionModule,
    NgbModule,
    NgxPaginationModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatTableModule,
    MatExpansionModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    MatSelectModule,
    MatFileUploadModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatDialogModule,

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
