import {SignUpDto} from './signUpDto';
import {AuthControllerService} from '../core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css'],
    styles: [`
      .error {
        background-color: #fff0f0;
      }
`]
})

export class SignupComponent implements OnInit {
    signUpDto: SignUpDto;
    signupForm: FormGroup;

    constructor(private authService: AuthControllerService,
                private router: Router,
                private formBuilder: FormBuilder,
                private toastr: ToastrService) {
        this.signUpDto = {
            vorname: '',
            nachname: '',
            firma: '',
            email: '',
            password: '',
            confirmPassword: ''
        };
    }

    verifyEmailPopup() {
        this.toastr.warning('Überprüfe bitte dein Posteingang und bestätige das E-Mail', 'E-Mail bestätigen');
    }

    ngOnInit(): void {
        this.signupForm = this.formBuilder.group({
            vorname: ['', [Validators.required]],
            nachname: ['', [Validators.required]],
            firma: ['', [Validators.required]],
            email: ['', [Validators.required, Validators.email, emailValid()]],
            password: ['', [Validators.required]],
            confirmPassword: ['', [Validators.required]],
        }, {
            validator: matchingFields('password', 'confirmPassword')
        });
    }

    signup() {
        this.signUpDto.vorname = this.signupForm.get('vorname').value;
        this.signUpDto.nachname = this.signupForm.get('nachname').value;
        this.signUpDto.firma = this.signupForm.get('firma').value;
        this.signUpDto.email = this.signupForm.get('email').value;
        this.signUpDto.password = this.signupForm.get('password').value;

        this.authService.signupUsingPOST(this.signUpDto)
            .subscribe(value => {
                this.router.navigate(['/login']);
                this.verifyEmailPopup();
            });
    }

    isValid(control) {
        return this.signupForm.controls[control].invalid && this.signupForm.controls[control].touched;
    }
}

function matchingFields(field1, field2) {
    return form => {
        if (form.controls[field1].value !== form.controls[field2].value) {
            return {mismatchedFields: true};
        }
    };
}

function emailValid() {
    return control => {
        const regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return regex.test(control.value) ? null : {invalidEmail: true};
    };

}
