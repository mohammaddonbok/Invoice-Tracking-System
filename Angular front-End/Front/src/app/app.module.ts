import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SigninComponent} from './signin/signin.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SignupComponent} from './signup/signup.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MatInputModule} from '@angular/material/input';
import {HttpClientModule} from '@angular/common/http';
import {MatIconModule} from '@angular/material/icon';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {InvoiceListComponent} from './invoice-list/invoice-list.component';
import {NgxPaginationModule} from 'ngx-pagination';
import {CreateInvoiceComponent} from './create-invoice/create-invoice.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatFileUploadModule } from 'angular-material-fileupload';
import {ToastrModule} from 'ngx-toastr';
import {ViewInvoiceComponent} from './view-invoice/view-invoice.component';
import { NavComponent } from './nav/nav.component';
import {MatSelectModule} from '@angular/material/select';
import { NotFoundComponent } from './not-found/not-found.component';
import {MatButtonModule} from '@angular/material/button';
import { ViewUsersComponent } from './view-users/view-users.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';


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
    MatSlideToggleModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
