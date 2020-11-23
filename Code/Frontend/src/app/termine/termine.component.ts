import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {MainControllerService} from '../core';

@Component({
  selector: 'app-termine',
  templateUrl: './termine.component.html',
  styleUrls: ['./termine.component.css']
})
export class TermineComponent implements OnInit {
  owner: any;
  owner2 = [];

  searchText;

  constructor(
      private authService: MainControllerService) {
    this.owner = {
      vorname: '',
      nachname: '',
      firma: '',
    };
  }

  ngOnInit(): void {
    this.getAllCompanies();
  }

  getAllCompanies() {
    this.authService.getAllCompaniesUsingGET()
      .pipe(
        map(value => {
          for (const key in value) {
            this.owner = {
              vorname: '',
              nachname: '',
              firma: '',
              ownerId: '',
              email: ''
            };
            this.owner.vorname = value[key].vorname;
            this.owner.nachname = value[key].nachname;
            this.owner.firma = value[key].firma;
            this.owner.ownerId = value[key].ownerId;
            this.owner.email = value[key].email;

            this.owner2.push(this.owner);
          }
        })
      )
      .subscribe();
  }
}
