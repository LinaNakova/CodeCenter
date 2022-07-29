import {Component, Input, OnInit, OnDestroy} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  tags : String [] = []
  answers : number | undefined
  destroySubject$ = new Subject<void>()
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.getQuestionTags()
    this.getQuestionAnswers()
  }
  getQuestionTags()
  {
    this.service.getQuestionTags(this.question!!.id)
        .pipe(takeUntil(this.destroySubject$))
        .subscribe({
          next: (tags ) => {
            console.log(tags)
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
