import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MainControllerService, TerminControllerService, TerminDto} from '../core';
import {ToastrService} from 'ngx-toastr';
import {ArbeitstageDTO} from '../core/model/arbeitstageDTO';
import {SettingsControllerService} from '../core/api/settingsController.service';
import {FerienDTO} from '../core/model/ferienDTO';

import {map} from 'rxjs/operators';
import {ArbeitszeitDTO} from '../core/model/arbeitszeitDTO';

@Component({
    selector: 'app-create-termin',
    templateUrl: './create-termin.component.html',
    styleUrls: ['./create-termin.component.css']
})
export class CreateTerminComponent implements OnInit {
    terminDto: TerminDto;
    createTerminForm: FormGroup;
    startZeit = [];
    endZeit = [];
    selectedTime: string;
    selectedDate: any;
    todayString: string; //heutige Datum als String
    isSelectedDate: boolean;
    isSelectedStartTime: boolean;
    isSelectedEndTime: boolean;
    email: string;
    firma: string;
    arbeitstageDto: ArbeitstageDTO;
    selectedDayOfWeek: any; //Mo, Di, Mi etc
    blockEverything: boolean;

    //Ferien
    ferienArray = Array<FerienDTO>();
    ferienStart: string;
    ferienEnd: string;
    isHoliday: boolean;

    options: any;
    arbeitszeitDto: ArbeitszeitDTO;
    firmaname: string;

    isWindows: boolean;
    isMacOs: boolean;
    operating: string;

    constructor(private terminService: TerminControllerService,
                private router: Router,
                private route: ActivatedRoute,
                private toastr: ToastrService,
                private settingsService: SettingsControllerService,
                private mainService: MainControllerService) {

        this.operating = navigator.appVersion.toString();
        if (this.operating.search('Macintosh') !== -1){
            this.isMacOs = true;
            this.isWindows = false;
        }
        else {
            this.isMacOs = false;
            this.isWindows = true;
        }

        this.terminDto = {
            datum: '',
            vorname: '',
            nachname: '',
            email: '',
            hoursStart: 0,
            minutesStart: 0,
            hoursEnd: 0,
            minutesEnd: 0
        };
        this.arbeitstageDto = {
            monday: false,
            thuesday: false,
            wednesday: false,
            thursday: false,
            friday: false,
            saturday: false,
            sunday: false,
        };
        this.arbeitszeitDto = {
            arbeitsbeginn: '',
            arbeitsende: ''
        };
        this.options = [
            {name: '--:--', value: '--:--'},
            {name: '00:00', value: '00:00'},
            {name: '00:30', value: '00:30'},
            {name: '01:00', value: '01:00'},
            {name: '01:30', value: '01:30'},
            {name: '02:00', value: '02:00'},
            {name: '02:30', value: '02:30'},
            {name: '03:00', value: '03:00'},
            {name: '03:30', value: '03:30'},
            {name: '04:00', value: '04:00'},
            {name: '04:30', value: '04:30'},
            {name: '05:00', value: '05:00'},
            {name: '05:30', value: '05:30'},
            {name: '06:00', value: '06:00'},
            {name: '06:30', value: '06:30'},
            {name: '07:00', value: '07:00'},
            {name: '07:30', value: '07:30'},
            {name: '08:00', value: '08:00'},
            {name: '08:30', value: '08:30'},
            {name: '09:00', value: '09:00'},
            {name: '09:30', value: '09:30'},
            {name: '10:00', value: '10:00'},
            {name: '10:30', value: '10:30'},
            {name: '11:00', value: '11:00'},
            {name: '11:30', value: '11:30'},
            {name: '12:00', value: '12:00'},
            {name: '12:30', value: '12:30'},
            {name: '13:00', value: '13:00'},
            {name: '13:30', value: '13:30'},
            {name: '14:00', value: '14:00'},
            {name: '14:30', value: '14:30'},
            {name: '15:00', value: '15:00'},
            {name: '15:30', value: '15:30'},
            {name: '16:00', value: '16:00'},
            {name: '16:30', value: '16:30'},
            {name: '17:00', value: '17:00'},
            {name: '17:30', value: '17:30'},
            {name: '18:00', value: '18:00'},
            {name: '18:30', value: '18:30'},
            {name: '19:00', value: '19:00'},
            {name: '20:00', value: '20:00'},
            {name: '20:30', value: '20:30'},
            {name: '21:00', value: '21:00'},
            {name: '21:30', value: '21:30'},
            {name: '22:00', value: '22:00'},
            {name: '22:30', value: '22:30'},
            {name: '23:00', value: '23:00'},
            {name: '23:30', value: '23:00'},
        ];


        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = String(today.getFullYear()).padStart(4, '0');
        this.todayString = yyyy.toString() + '-' + mm.toString() + '-' + dd.toString();
        this.isSelectedDate = false;
        this.isSelectedStartTime = false;
        this.isSelectedEndTime = false;
        this.blockEverything = false;
        this.ferienStart = '';
        this.ferienEnd = '';
        this.isHoliday = false;
    }

    ngOnInit(): void {
        this.getOneCompany();
        this.createTerminForm = new FormGroup({
            datum: new FormControl('', Validators.required),
            vorname: new FormControl('', Validators.required),
            nachname: new FormControl('', Validators.required),
            email: new FormControl('', [Validators.required, Validators.email]),
            startTime: new FormControl('', Validators.required),
            endTime: new FormControl('', Validators.required)
        });

        this.mainService.getAllCompaniesUsingGET().subscribe(value => {
            for (let i = 0; i < value.length; i++) {

                if (value[i].ownerId.toString() === this.route.snapshot.params['id']) {
                    this.email = value[i].email;
                    this.firma = value[i].firma;

                    this.settingsService.getDaysUsingGET(this.email).subscribe(value => {
                        this.arbeitstageDto = value;
                    });

                    this.settingsService.getAllFerienUsingGET(this.email).subscribe(value => {
                        if (value.length !== 0) {
                            for (let i = 0; i < value.length; i++) {
                                this.ferienArray.push(value[i]);
                            }
                        }
                    });

                    this.settingsService.getArbeitszeitUsingGET(this.email).subscribe(value1 => {
                        this.arbeitszeitDto.arbeitsbeginn = value1.arbeitsbeginn;
                        this.arbeitszeitDto.arbeitsende = value1.arbeitsende;
                    });
                }
            }
        });

    }

    selectChangeHandlerDatum(event: any) {
        this.startZeit = []; //termine
        this.endZeit = []; //termine
        this.selectedDate = event.target.value;
        this.isSelectedDate = true;
        this.isSelectedStartTime = false;


        //pruefe ob Ferien sind
        for (let i = 0; i < this.ferienArray.length; i++) {
            if (this.selectedDate >= this.ferienArray[i].ferienStart && this.selectedDate <= this.ferienArray[i].ferienEnde) {
                this.isHoliday = true;
                this.ferienStart = this.ferienArray[i].ferienStart;
                this.ferienEnd = this.ferienArray[i].ferienEnde;
                return;
            } else {
                this.isHoliday = false;
            }
        }
        this.terminService.getAllTimesUsingGET(this.route.snapshot.params['id']).subscribe(value => {
            for (let i = 0; i < value.length; i++) {
                var year = value[i].year.toString();
                var month = value[i].month.toString();
                var day = value[i].day.toString();
                if (month.length === 1) {
                    month = '0' + month.toString();
                }
                if (day.length === 1) {
                    day = '0' + day.toString();
                }
                var datum = year + '-' + month + '-' + day;
                if (this.selectedDate === datum) {
                    let hoursStart = value[i].hoursStart.toString();
                    let hoursEnd = value[i].hoursEnd.toString();

                    if (hoursStart.length === 1) {
                        hoursStart = '0' + hoursStart;
                    }
                    if (hoursEnd.length === 1) {
                        hoursEnd = '0' + hoursEnd;
                    }

                    let minutesStart = value[i].minutesStart.toString();
                    let minutesEnd = value[i].minutesEnd.toString();

                    if (minutesStart.length === 1) {
                        minutesStart = minutesStart + '0';
                    }
                    if (minutesEnd.length === 1) {
                        minutesEnd = minutesEnd + '0';
                    }
                    this.startZeit.push(hoursStart + ':' + minutesStart);
                    this.endZeit.push(hoursEnd + ':' + minutesEnd);
                }
            }
        });
        var i = this.options.length;
        this.arbeitsBeginnUndEndeSetzen(i);
        this.pruefeObDieserTagFrei(event);
    }

    private arbeitsBeginnUndEndeSetzen(i) {
        while (i--) {
            if (this.options[i].value === '--:--') {
                continue;
            } else {
                if (!(this.options[i].value >= this.arbeitszeitDto.arbeitsbeginn && this.options[i].value <= this.arbeitszeitDto.arbeitsende)) {
                    this.options.splice(i, 1);
                }
            }
        }
    }

    private pruefeObDieserTagFrei(event: any) {
        this.selectedDayOfWeek = new Date(event.target.value);

        if (this.arbeitstageDto.monday === false && this.selectedDayOfWeek.getDay() === 1) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }

        if (this.arbeitstageDto.thuesday === false && this.selectedDayOfWeek.getDay() === 2) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
        if (this.arbeitstageDto.wednesday === false && this.selectedDayOfWeek.getDay() === 3) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
        if (this.arbeitstageDto.thursday === false && this.selectedDayOfWeek.getDay() === 4) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
        if (this.arbeitstageDto.friday === false && this.selectedDayOfWeek.getDay() === 5) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
        if (this.arbeitstageDto.saturday === false && this.selectedDayOfWeek.getDay() === 6) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
        if (this.arbeitstageDto.sunday === false && this.selectedDayOfWeek.getDay() === 0) {
            this.blockEverything = true;
            return;

        } else {
            this.blockEverything = false;
        }
    }

    getOneCompany() {
        this.mainService.getAllCompaniesUsingGET()
            .pipe(
                map(value => {
                    for (const key in value) {
                        if (value[key].ownerId === parseInt(this.route.snapshot.url[2].path)) {
                            this.firmaname = value[key].firma;
                        }
                    }
                })
            )
            .subscribe();
    }

    selectChangeHandlerStartTime(event: any) {
        for (let i = 0; i < event.target.length; i++) {
            if (event.target[i].value === '--:--') {
                event.target[i].disabled = true;
            } else {
                event.target[i].disabled = false;
                event.target[i].style.color = 'black';
            }
        }
        this.selectedTime = event.target.value;
        this.blendeZeitenaus(event);
        if (event.target.value !== '') {
            this.isSelectedStartTime = true;
        }
    }
    selectChangeHandlerStartTime2(event: any){
        for (let i = 0; i < event.target.length; i++) {
            if (event.target[i].value === '--:--') {
                event.target[i].disabled = true;
            } else {
                event.target[i].disabled = false;
                event.target[i].style.color = 'black';
            }
        }
        this.blendeZeitenaus(event);
    }
    selectChangeHandlerStartTime3(event: any){
        this.selectedTime = event.target.value;
        if (event.target.value !== '') {
            this.isSelectedStartTime = true;
        }
    }

    selectChangeHandlerEndTime(event: any) {
        for (let i = 0; i < event.target.length; i++) {
            event.target[i].disabled = false;
            event.target[i].style.color = 'black';
        }

        for (let i = 0; i < event.target.length; i++) {
            if (event.target[i].value > this.selectedTime) {
                break;
            }
            event.target[i].disabled = true;
            event.target[i].style.color = 'red';
        }

        this.blendeZeitenaus(event);
        this.blendeZeitenausDieGebuchteZeitenUeberschreiben(event);
        if (event.target.value !== '') {
            this.isSelectedDate = false;
            this.isSelectedEndTime = true;
        }
    }

    private blendeZeitenausDieGebuchteZeitenUeberschreiben(event: any) {
        for (let j = 0; j < this.startZeit.length; j++) {
            if (this.selectedTime < this.startZeit[j]) {
                for (let i = event.target.length - 1; i > 0; i--) {
                    if (event.target[i].value === this.startZeit[j]) {
                        break;
                    }
                    event.target[i].disabled = true;
                    event.target[i].style.color = 'red';
                }
            }
        }
    }

    verifyEmailPopUp() {
        this.toastr.warning('Terminbestätigung wurde Ihnen per Mail zugestellt');
    }

    createTermin() {
        this.terminDto.datum = this.createTerminForm.get('datum').value.toString();
        this.terminDto.vorname = this.createTerminForm.get('vorname').value;
        this.terminDto.nachname = this.createTerminForm.get('nachname').value;
        this.terminDto.email = this.createTerminForm.get('email').value;
        this.terminDto.hoursStart = this.createTerminForm.get('startTime').value.substr(0, 2);
        this.terminDto.minutesStart = this.createTerminForm.get('startTime').value.substr(3, 4);
        this.terminDto.hoursEnd = this.createTerminForm.get('endTime').value.substr(0, 2);
        this.terminDto.minutesEnd = this.createTerminForm.get('endTime').value.substr(3, 4);

        this.terminService.createTerminUsingPOST(this.route.snapshot.params['id'], this.terminDto).subscribe();
        this.createTerminForm.reset();
        this.verifyEmailPopUp();
    }

    private blendeZeitenaus(event: any) {
        for (let i = 0; i < event.target.length; i++) {
            for (let j = 0; j < this.startZeit.length; j++) {
                if (event.target[i].value === this.startZeit[j]) {
                    event.target[i].disabled = true;
                    event.target[i].style.color = 'red';
                }
                if (event.target[i].value < this.endZeit[j] && event.target[i].value > this.startZeit[j]) {
                    event.target[i].disabled = true;
                    event.target[i].style.color = 'red';
                } else {
                    continue;
                }
            }
        }
    }
}
