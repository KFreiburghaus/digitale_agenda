/*
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignUpDto} from '../signup/signUpDto';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  signup(signUpDto: SignUpDto): Observable<any> {
    return this.http.post('http://localhost:8080/api/auth/signup', signUpDto,
      {responseType: 'text'});
  }

  getAllCompanies(){
    return this.http.get('http://localhost:8080/api/allCompanies');
  }
}

 */
