import {AppComponent} from './app.component';
import {SignupComponent} from './signup/signup.component';
import {AppRoutingModule} from './app-routing.module';
import {HeaderComponent} from './header/header.component';
import {LoginComponent} from './login/login.component';
import {TermineComponent} from './termine/termine.component';
import {
    DayService,
    MonthAgendaService,
    MonthService,
    RecurrenceEditorModule,
    ScheduleModule,
    WeekService,
    WorkWeekService,
    ActionEventArgs,
} from '@syncfusion/ej2-angular-schedule';
import {CalendarComponent} from './calendar/calendar.component';
import {PagenotfoundComponent} from './pagenotfound/pagenotfound.component';
import {CreateTerminComponent} from './create-termin/create-termin.component';
import {ApiModule, CalendarControllerService} from './core';
import {AuthGuard} from './auth.guard';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {SettingsComponent} from './settings/settings.component';
import {ArbeitstageControllerService} from './core/api/arbeitstageController.service';
import {SettingsControllerService} from './core/api/settingsController.service';

@NgModule({
    declarations: [
        AppComponent,
        SignupComponent,
        HeaderComponent,
        LoginComponent,
        TermineComponent,
        CalendarComponent,
        PagenotfoundComponent,
        CreateTerminComponent,
        SettingsComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule,
        ScheduleModule, RecurrenceEditorModule,
        ApiModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        MatAutocompleteModule,
        Ng2SearchPipeModule
    ],
    providers: [
        DayService,
        WeekService,
        WorkWeekService,
        MonthService,
        MonthAgendaService,
        CalendarControllerService,
        ArbeitstageControllerService,
        SettingsControllerService,
        AuthGuard
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
