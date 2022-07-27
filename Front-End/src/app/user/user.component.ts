import {Component, Input, OnInit} from '@angular/core';
import {UserInterface} from "../userInterface";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input()
  user : UserInterface | undefined
  constructor() { }

  ngOnInit(): void {
  }

}
