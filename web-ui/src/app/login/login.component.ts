import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  loginUser(username: HTMLInputElement, password: HTMLInputElement): boolean {
      console.log(`Login attempt: ${username.value} and link: ${password.value}`);
      return false;
  }
}
