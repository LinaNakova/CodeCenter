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
  limitedDescription : string | undefined
  constructor() { }

  ngOnInit(): void {
    this.getLimitedDescription()
  }
  getLimitedDescription()
  {
    if (this.t!!.description.length > 31)
    {
      this.limitedDescription = this.t?.description.substring(0,31) + "..."
    }
    else {
      this.limitedDescription = this.t?.description
    }
  }

}
