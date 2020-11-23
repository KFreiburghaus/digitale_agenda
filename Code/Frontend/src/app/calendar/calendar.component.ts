import {Component, OnInit, ViewChild} from '@angular/core';
import {ActionEventArgs, EventSettingsModel, ScheduleComponent} from '@syncfusion/ej2-angular-schedule';
import {CalendarDto2} from './calendarDto2';
import {CalendarControllerService} from '../core';
import {CalendarDto} from './calendarDto';


@Component({
    selector: 'app-calendar',
    template: '<ejs-schedule #scheduleObj (popupOpen)="disableAddPopup($event)" (actionBegin)="onActionBegin($event)" style="margin-left: 5em; margin-right: 5em"' +
        '[eventSettings]="eventObject"></ejs-schedule>',
    styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

    calendarDto: CalendarDto;
    calendarDto2: CalendarDto2;
    eventObject: EventSettingsModel;
    allTermins = [];
    eventData2 = [];
    editButton: any;
    @ViewChild('scheduleObj') scheduleObj: ScheduleComponent;


    constructor(private calendarService: CalendarControllerService) {
    }

    ngOnInit(): void {
        this.calendarService.getAllTerminsUsingGET().subscribe(value => {
                for (const key in value) {
                    this.calendarDto = {
                        year: 0,
                        month: 0,
                        day: 0,
                        hoursStart: 0,
                        minutesStart: 0,
                        hoursEnd: 0,
                        minutesEnd: 0,
                        vorname: '',
                        nachname: '',
                        email: '',
                        id: -1
                    };
                    this.calendarDto2 = {
                        Subject: '',
                        StartTime: new Date(2020, 1, 1,
                            10, 0),
                        EndTime: new Date(220, 1, 1,
                            11, 0),
                    };
                    this.calendarDto.year = value[key].year;
                    this.calendarDto.month = value[key].month;
                    this.calendarDto.day = value[key].day;
                    this.calendarDto.hoursStart = value[key].hoursStart;
                    this.calendarDto.minutesStart = value[key].minutesStart;
                    this.calendarDto.hoursEnd = value[key].hoursEnd;
                    this.calendarDto.minutesEnd = value[key].minutesEnd;
                    this.calendarDto.vorname = value[key].vorname;
                    this.calendarDto.nachname = value[key].nachname;
                    this.calendarDto.email = value[key].email;
                    this.calendarDto.id = value[key].id;

                    this.allTermins.push(this.calendarDto);

                    this.calendarDto2.Subject = this.allTermins[key].vorname + ' ' + this.allTermins[key].nachname + ' ' + this.allTermins[key].email + ' Id: ' + this.allTermins[key].id;
                    this.calendarDto2.StartTime = new Date(this.allTermins[key].year, this.allTermins[key].month - 1, this.allTermins[key].day,
                        this.allTermins[key].hoursStart, this.allTermins[key].minutesStart);
                    this.calendarDto2.EndTime = new Date(this.allTermins[key].year, this.allTermins[key].month - 1, this.allTermins[key].day,
                        this.allTermins[key].hoursEnd, this.allTermins[key].minutesEnd);

                    this.eventData2.push(this.calendarDto2);
                }
            }
        );
        this.eventObject = {
            dataSource: this.eventData2
        };
    }

    onActionBegin(event: ActionEventArgs): void {

        if (event.requestType === 'eventRemove') {
            let subject = event.data[0].Subject.toString();
            let search = subject.search(' Id');
            var id = subject.substr(search + 5, search * 50);
            this.calendarService.deleteTerminUsingDELETE(id).subscribe(value => console.log(value));
            window.location.reload();
        }
    }

    disableAddPopup(event: any): void {
        var buttonElement = event.type === 'QuickInfo' ? '.e-event-popup .e-edit' : '.e-schedule-dialog .e-event-edit';
        this.editButton = document.querySelector(buttonElement);
        this.editButton.disabled = true;

        if (event.data.Subject === undefined) {
            event.cancel = true;

        } else {
            this.scheduleObj.readonly = false;
        }
    }
}


