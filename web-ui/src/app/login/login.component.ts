import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, AbstractControl, Validators} from "@angular/forms";
import {HttpClient} from '@angular/common/http';
import {LoginFormDTO} from './login.model';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  username: AbstractControl;
  password: AbstractControl;
  errorMessage: string;

  constructor(private http: HttpClient, fb: FormBuilder) {
    this.errorMessage = '';
    this.loginForm = fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required]
    });

    this.username = this.loginForm.controls['username'];
    this.password = this.loginForm.controls['password'];
  }

  readonly apiURL = environment.baseURL + "/api/signIn/";

  ngOnInit(): void { }

  onSubmit(formDTO: LoginFormDTO): boolean {

    this.http
        .post(this.apiURL, formDTO, { responseType: 'text'})
        .subscribe(response => console.log(response), error => {

          this.errorMessage = error.statusText === 'Unauthorized'
            ? 'Wrong username or password!'
            : error.statusText;

          console.log(error.statusText);
        });

      return false;
  }
}
