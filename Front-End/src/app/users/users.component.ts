import { Component, OnInit } from '@angular/core';
import {UserInterface} from "../userInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  title="All Users"
  users : UserInterface[] = []
  loaded = false
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.getUsers()
  }
  getUsers()
  {
    this.service.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loaded = true
      }
    })
  }

}
