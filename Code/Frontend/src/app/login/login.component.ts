import {Component, OnInit} from '@angular/core';
import {AuthControllerService, LoginRequest, MainControllerService} from '../core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    styles: [`
        .error {
            background-color: #fff0f0;
        }
    `]
})

export class LoginComponent implements OnInit {
    loginDto: LoginRequest;
    loginForm: FormGroup;
    loginResponse: any;
    username: string;

    constructor(
        private loginService: AuthControllerService,
        private router: Router,
        private toastr: ToastrService,
        private mainService: MainControllerService) {
        this.loginDto = {
            username: '',
            password: '',
        };

        this.loginResponse = {
            username: '',
            jwt: '',
        };
    }

    ngOnInit(): void {
        this.loginForm = new FormGroup({
            username: new FormControl('', [Validators.required, emailValid()]),
            password: new FormControl('', Validators.required),
        });
    }

    login() {
        this.loginDto.username = this.loginForm.get('username').value;
        this.loginDto.password = this.loginForm.get('password').value;

        this.loginService.loginUsingPOST(this.loginDto)
            .subscribe(value => {
                this.loginResponse = value;
                if (this.loginResponse.username === 'authenticationIsFail') {
                    this.toastr.warning('Bitte noch E-Mail-Adresse bestätigen', 'E-Mail bestätigen');
                    this.ngOnInit();
                } else if (this.loginResponse.username != null) {
                    this.mainService.getAllCompaniesUsingGET().subscribe(value => {
                        for (let i = 0; i < value.length; i++) {
                            if (value[i].email.toString() === localStorage.getItem('username')) {
                                localStorage.setItem('vorname', value[i].vorname);
                                localStorage.setItem('nachname', value[i].nachname);
                            }
                        }
                    });
                    this.username = this.loginResponse.username;
                    localStorage.setItem('token', this.loginResponse.jwt);
                    localStorage.setItem('username', this.loginResponse.username);
                    this.router.navigate(['/calendar']);
                    this.toastr.success('Login erfolgreich');
                } else {
                    this.toastr.error('E-Mail oder Passwort falsch', 'Falsche Logindaten');
                    this.ngOnInit();
                }
            });
    }

    isValid(control) {
        return this.loginForm.controls[control].invalid && this.loginForm.controls[control].touched;
    }
}

function emailValid() {
    return control => {
        const regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return regex.test(control.value) ? null : {invalidEmail: true};
    };
}
