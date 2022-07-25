import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {concatMap, interval, Observable, startWith, Subject, switchMap} from "rxjs";

@Component({
  selector: 'app-question-answers',
  templateUrl: './question-answers.component.html',
  styleUrls: ['./question-answers.component.css']
})
export class QuestionAnswersComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  answers$ : Observable<QuestionInterface[]> | undefined
  params = location.pathname.split("/")
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.answers$ = this.service.getQuestionAnswers(Number(this.params[2]))
  }

}
