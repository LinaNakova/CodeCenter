import { Component, OnInit } from '@angular/core';
import {TagInterface} from "../tagInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.css']
})
export class TagsComponent implements OnInit {
  // tags : number [] = [1,2,3,4,5,6,7,8,9]
  tags : TagInterface[] =[]
  tagNum : number = 0
  title="Tags"
  constructor(private codeService : CodeService) { }

  ngOnInit(): void {
    this.codeService.getTags().subscribe(t => {
      this.tags = t
      this.tagNum = t.length
    })
  }

}
