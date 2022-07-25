import { Component, OnInit } from '@angular/core';
import {TagInterface} from "../tagInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.css']
})
export class TagsComponent implements OnInit {
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
