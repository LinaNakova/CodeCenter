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
    console.log(this.question?.id)
    this.service.getQuestionTags(this.question!!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (tags ) => {
          console.log(tags)
          this.tags = tags
        }
      })
    this.service.getQuestionAnswers(this.question!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (answ) =>
      {
      this.answers = answ.length
      }
      })
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }

}
