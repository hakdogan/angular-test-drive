import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from './login.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  loginUser(username: HTMLInputElement, password: HTMLInputElement): boolean {
      console.log(`Login attempt: ${username.value} and link: ${password.value}`);
      this.http.get('http://localhost:8080/api/signIn/' + username.value + '/' + password.value, { responseType: 'text' })
        .subscribe(response => console.log(response));
      return false;
  }
}
