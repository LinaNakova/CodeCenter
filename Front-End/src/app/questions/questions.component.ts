import { Component, OnInit } from '@angular/core';
import {CodeService} from "../code.service";
import {Observable} from "rxjs";
import {QuestionInterface} from "../questionInterface";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {
  questionsNum : number = 0
  allQuestions : QuestionInterface [] = []
  title = "All Questions"
  constructor(private codeService : CodeService) { }

  ngOnInit(): void {
    this.codeService.getQuestionsWithoutAnswers().subscribe(q => {
      this.allQuestions = q;
      this.questionsNum = q.length
    })
  }

}
