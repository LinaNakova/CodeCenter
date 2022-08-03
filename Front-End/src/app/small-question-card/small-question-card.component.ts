import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {Subject, takeUntil} from "rxjs";
import {CodeService} from "../code.service";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-small-question-card',
  templateUrl: './small-question-card.component.html',
  styleUrls: ['./small-question-card.component.css']
})
export class SmallQuestionCardComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  tags : TagInterface [] = []
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
