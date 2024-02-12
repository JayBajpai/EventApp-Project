import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EventlistComponent } from './eventlist/eventlist.component';
import { AddWishlistComponent } from './add-wishlist/add-wishlist.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'events', component: EventlistComponent, canActivate:[AuthGuard] },
  { path: 'addWishlist', component: AddWishlistComponent, canActivate:[AuthGuard] }
  // Add other routes as needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
