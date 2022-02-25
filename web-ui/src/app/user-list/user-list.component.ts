import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from '@angular/common/http';
import {UserDTO} from "./user.model";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private http: HttpClient) { }

  userList!: UserDTO[];

  readonly apiURL = environment.baseURL + "/api/user/all";

  ngOnInit(): void {
    this.callUsers();
  }

  callUsers(){
    console.log("********** Call Users **********");
    console.log(this.apiURL);

    this.http.get<UserDTO[]>(this.apiURL)
      .subscribe({
        next: this.renderTable.bind(this),
        error: this.handleError.bind(this)
      });
  }

  private renderTable(users: UserDTO[]): void {
    this.userList = users;
    console.log(users);
  }

  private handleError(error: any): void {
    console.log(error.statusText);
  }
}
