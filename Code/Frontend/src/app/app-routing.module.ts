import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SignupComponent} from './signup/signup.component';
import {LoginComponent} from './login/login.component';
import {TermineComponent} from './termine/termine.component';
import {CalendarComponent} from './calendar/calendar.component';
import {PagenotfoundComponent} from './pagenotfound/pagenotfound.component';
import {CreateTerminComponent} from './create-termin/create-termin.component';
import {AuthGuard} from './auth.guard';
import {SettingsComponent} from './settings/settings.component';


const routes: Routes = [
  {path: '', redirectTo: '/termine', pathMatch: 'full'},
  {path: 'sign-up', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'termine', component: TermineComponent},
  {path: 'termine/terminErstellen/:id', component: CreateTerminComponent},

  {path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard]},
  {path: 'settings', component: SettingsComponent},
  {path: '**', component: PagenotfoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
