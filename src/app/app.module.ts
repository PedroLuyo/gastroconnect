import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularFireDatabaseModule } from '@angular/fire/compat/database';
import { AngularFirestoreModule } from '@angular/fire/compat/firestore';
import { environment } from '../environments/environment';
import { UsersComponent } from './components/auth/users/users.component';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';
import { Auth } from '@angular/fire/auth';
import { LoginComponent } from './components/auth/login/login.component';
import { MainComponent } from './main/main.component';
import { FirebaseUIModule, firebase, firebaseui } from 'firebaseui-angular';
import { AngularFireModule } from '@angular/fire/compat';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { SelectorComponent } from './components/roles/selector/selector.component';
import { GestorComponent } from './components/roles/gestor/gestor.component';
import { RestauranteComponent } from './components/roles/restaurante/restaurante.component';
import { MatIconModule } from '@angular/material/icon';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxPaginationModule } from 'ngx-pagination';
import { DetallesComponent } from './components/detalles/detalles.component';

const firebaseUiAuthConfig: firebaseui.auth.Config = {
  signInFlow: 'popup',
  signInOptions: [
    firebase.auth.EmailAuthProvider.PROVIDER_ID,
    firebase.auth.GoogleAuthProvider.PROVIDER_ID,
  ],
  credentialHelper: firebaseui.auth.CredentialHelper.NONE
};

class CustomErrorHandler implements ErrorHandler {
  handleError(error: any): void {
    if (!(error instanceof Error) || error.message.indexOf('ExpressionChangedAfterItHasBeenCheckedError') === -1) {
      console.error(error);
    }
  }
}

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    LoginComponent,
    MainComponent,
    GestorComponent,
    RestauranteComponent,
    SelectorComponent,
    DetallesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFirestoreModule,
    AngularFireAuthModule,
    AngularFireDatabaseModule,
    FirebaseUIModule.forRoot(firebaseUiAuthConfig),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    MatFormFieldModule,
    MatAutocompleteModule,
    MatInputModule,
    MatIconModule,
    NgSelectModule,
    NgxPaginationModule,
  ],
  providers: [
    { provide: Auth, useValue: getAuth(initializeApp(environment.firebase)) },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
