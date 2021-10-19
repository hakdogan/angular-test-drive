import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from './login.model';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient) { }

  readonly apiURL = environment.baseURL + "/api/signIn/";

  ngOnInit(): void {
  }

  loginUser(username: HTMLInputElement, password: HTMLInputElement): boolean {
      this.http
        .get(this.apiURL + username.value + '/' + password.value, { responseType: 'text' })
        .subscribe(response => console.log(response));
      return false;
  }
}
