/*
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TerminDto} from '../create-termin/terminDto';
import {CalendarDto} from '../calendar/calendarDto';

@Injectable({
  providedIn: 'root'
})
export class TerminService {

  constructor(private http: HttpClient) {

  }

  createTermin(terminDto: TerminDto, id: any): Observable<any> {
    return this.http.post('http://localhost:8080/termine/terminErstellen/' + id, terminDto,
      {responseType: 'text'});
  }

  getAllTermins(): Observable<any> {
    return this.http.get('http://localhost:8080/calendar', {responseType: 'text'});
  }
}
 */
