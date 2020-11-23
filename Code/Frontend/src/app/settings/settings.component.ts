import {Component, OnInit} from '@angular/core';
import {ArbeitstageDTO} from '../core/model/arbeitstageDTO';
import {FerienDTO} from '../core/model/ferienDTO';
import {SettingsControllerService} from '../core/api/settingsController.service';
import {ArbeitszeitDTO} from '../core/model/arbeitszeitDTO';

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
    arbeitstageDto: ArbeitstageDTO;
    arbeitszeitDto: ArbeitszeitDTO;
    email: string = localStorage.getItem('username');
    ferienDto: FerienDTO;
    ferienArray = Array<FerienDTO>();
    startDate: any;
    endDate: any;
    todayString: string;
    ferienFehler: boolean;
    speichernEnabled = [];
    arbeitstageSaved: boolean;
    arbeitszeitSaved: boolean;

    options: any;
    selectedStart: string;
    selectedEnd: string;
    arbeitszeitFehler: boolean;
    isSelectedEndTime: boolean;
    isSelectedStartTime: boolean;

    constructor(private settingsService: SettingsControllerService) {
        this.arbeitstageDto = {
            monday: true,
            thuesday: true,
            wednesday: true,
            thursday: true,
            friday: true,
            saturday: true,
            sunday: true
        };

        this.arbeitszeitDto = {
            arbeitsbeginn: this.selectedStart,
            arbeitsende: this.selectedEnd
        },
            this.options = [
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
                {name: '19:30', value: '19:30'},
                {name: '20:00', value: '20:00'},
                {name: '20:30', value: '20:30'},
                {name: '21:00', value: '21:00'},
                {name: '21:30', value: '21:30'},
                {name: '22:00', value: '22:00'},
                {name: '22:30', value: '22:30'},
                {name: '23:00', value: '23:00'},
                {name: '23:30', value: '23:00'},
            ];

        this.ferienFehler = false;
        this.arbeitszeitFehler = false;
        this.arbeitstageSaved = false;
        this.arbeitszeitSaved = false;
        this.isSelectedEndTime = false;
        this.isSelectedStartTime = false;
    }

    ngOnInit(): void {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = String(today.getFullYear()).padStart(4, '0');
        this.todayString = yyyy.toString() + '-' + mm.toString() + '-' + dd.toString();

        this.settingsService.getDaysUsingGET(this.email).subscribe(value => {
            this.arbeitstageDto.monday = value.monday;
            this.arbeitstageDto.thuesday = value.thuesday;
            this.arbeitstageDto.wednesday = value.wednesday;
            this.arbeitstageDto.thursday = value.thursday;
            this.arbeitstageDto.friday = value.friday;
            this.arbeitstageDto.saturday = value.saturday;
            this.arbeitstageDto.sunday = value.sunday;
        });

        this.settingsService.getAllFerienUsingGET(this.email).subscribe(value => {
            if (value.length === 0) {
                this.onAdd();
            } else {
                for (let i = 0; i < value.length; i++) {
                    this.ferienArray.push(value[i]);
                    this.speichernEnabled.push(true);
                }
            }
        });

        this.settingsService.getArbeitszeitUsingGET(this.email).subscribe(value => {
            this.selectedStart = value.arbeitsbeginn;
            this.selectedEnd = value.arbeitsende;
        });
    }

    submitArbeitstage() {
        this.settingsService.updateDaysUsingPUT(this.email, this.arbeitstageDto).subscribe();
        this.arbeitstageSaved = true;
    }

    onAdd() {
        this.ferienDto = {
            id: '',
            ferienStart: '',
            ferienEnde: ''
        };
        this.ferienArray.push(this.ferienDto);
    }

    submitFerien() {
        if (this.ferienDto.ferienStart < this.ferienDto.ferienEnde && !(this.ferienDto.ferienStart === '' || this.ferienDto.ferienEnde === '')) {
            this.settingsService.setFerienUsingPOST(this.email, this.ferienDto).subscribe();
            this.speichernEnabled.push(true);
            this.ferienFehler = false;
        } else {
            this.ferienFehler = true;
        }

    }

    deleteFerien(i: number) {
        this.settingsService.deleteFerienUsingDELETE(this.ferienArray[i].id).subscribe();
        window.location.reload();
    }

    selectChangeHandlerStartTime(event: any) {
        this.selectedStart = event.target.value;
        this.arbeitszeitDto.arbeitsbeginn = this.selectedStart;
        this.isSelectedStartTime = true;
    }

    selectChangeHandlerEndTime(event: any) {
        this.selectedEnd = event.target.value;
        this.arbeitszeitDto.arbeitsende = this.selectedEnd;
        this.isSelectedEndTime = true;
    }

    submitArbeitszeit() {
        if (this.arbeitszeitDto.arbeitsbeginn < this.arbeitszeitDto.arbeitsende) {
            this.settingsService.updateArbeitszeitUsingPUT(this.email, this.arbeitszeitDto).subscribe();
            this.arbeitszeitSaved = true;
            this.arbeitszeitFehler = false;
        } else {
            this.arbeitszeitFehler = true;
        }
    }
}
