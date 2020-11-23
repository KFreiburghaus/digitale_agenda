import {Component, OnInit} from '@angular/core';
import {AuthControllerService, MainControllerService, OwnerDto} from '../core';
import {ActivatedRoute, Router} from '@angular/router';
import {delay, map} from 'rxjs/operators';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
    vorname: string;
    nachname: string;

    constructor(public loginService: AuthControllerService,
                private router: Router,
                private route: ActivatedRoute,
                private mainService: MainControllerService) { }

    ngOnInit(): void {

    }

    get getVorname() {
        return localStorage.getItem('vorname');
    }

    get getNachname() {
        return localStorage.getItem('nachname');
    }

    logoutUser() {
        localStorage.clear();
        this.router.navigate(['/login']);
    }

    get getLoggedUser() {
        return localStorage.getItem('username');
    }
}

/*
*
        this.mainService.getAllCompaniesUsingGET().subscribe(value => {
                for (let i = 0; i < value.length; i++) {
                    if (value[i].email.toString() === localStorage.getItem('username')) {
                        this.vorname = value[i].vorname;
                        this.nachname = value[i].nachname;
                    }
                }
            });
*
* */
