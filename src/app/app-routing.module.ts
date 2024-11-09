import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './components/auth/users/users.component';
import { LoginComponent } from './components/auth/login/login.component';
import { MainComponent } from './main/main.component';
import { authGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { SelectorComponent } from './components/roles/selector/selector.component';
import { RestauranteComponent } from './components/roles/restaurante/restaurante.component';
import { GestorComponent } from './components/roles/gestor/gestor.component';
import { DetallesComponent } from './components/detalles/detalles.component';


const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  { path: 'monitorear', component: UsersComponent, canActivate: [authGuard, RoleGuard], data: { roles: ['admin'] } },
  { path: 'login', component: LoginComponent },

  { path: 'main', component: MainComponent },
  { path: 'detalles/:identificador', component: DetallesComponent },

  { path: 'selector', component: SelectorComponent, canActivate: [authGuard, RoleGuard], data: { roles: ['admin', 'gestor', 'comensal'] } },
  { path: 'restaurante', component: RestauranteComponent, canActivate: [authGuard, RoleGuard], data: { roles: ['admin', 'gestor'] } },
  { path: 'gestor', component: GestorComponent, canActivate: [authGuard, RoleGuard], data: { roles: ['admin', 'gestor', 'comensal'] } },

  





];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
