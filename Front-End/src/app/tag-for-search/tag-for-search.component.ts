import {Component, Input, OnInit} from '@angular/core';
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-tag-for-search',
  templateUrl: './tag-for-search.component.html',
  styleUrls: ['./tag-for-search.component.css']
})
export class TagForSearchComponent implements OnInit {
  @Input()
  t : TagInterface | undefined
  constructor() { }

  ngOnInit(): void {
  }

}
