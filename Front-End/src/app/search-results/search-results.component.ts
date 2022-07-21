import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  title = 'Search Results for "'
  allQuestions : QuestionInterface [] = []
  constructor(private location:Location, private service: CodeService) { }

  ngOnInit(): void {
    let params = location.pathname.split("/")
    this.title += params[3] + '"'
    this.service.getQuestionsWithMentionedWord(params[3])
      .subscribe(ans=>{
        this.allQuestions= ans;
      })
  }

}
