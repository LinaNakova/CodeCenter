import {Component, Input, OnInit, OnDestroy} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {Subject, takeUntil} from "rxjs";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  tags : TagInterface [] = []
  answers : number | undefined
  destroySubject$ = new Subject<void>()
  date : string | undefined

  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.getQuestionTags()
    this.getQuestionAnswers()
    this.getDate()
  }
  getDate()
  {
    this.date = this.question?.date.substring(0,10);
  }
  getQuestionTags()
  {
    this.service.getQuestionTags(this.question!!.id)
        .pipe(takeUntil(this.destroySubject$))
        .subscribe({
          next: (tags ) => {
            this.tags = tags
          }
        })
  }
  getQuestionAnswers()
  {
    this.service.getQuestionAnswers(this.question!.id)
        .pipe(takeUntil(this.destroySubject$))
        .subscribe({
          next: (answers) =>
          {
            this.answers = answers.length
          }
        })
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }

}
