import {Component, Input, OnInit} from '@angular/core';
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit {
  @Input()
  t : TagInterface | undefined
  constructor() { }

  ngOnInit(): void {
  }

}
