import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {Subject, takeUntil} from "rxjs";
import {CodeService} from "../code.service";
import {LikeInterface} from "../likeInterface";

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  tags : String [] = []
  destroySubject$ = new Subject<void>()
  likes : number | undefined
  disableUp : boolean = false
  disableDown : boolean = false
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.getTags()
    this.getLikes();
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }
  getTags()
  {
    console.log(this.question?.id)
    this.service.getQuestionTags(this.question!!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (tags ) => {
          console.log(tags)
          this.tags = tags
        }
      })
  }
  getLikes()
  {
    this.service.getLikes(this.question!!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next:(num:number) =>
        {
          this.likes = num
        }
      })
  }
  postLike()
  {
    let like : LikeInterface = { question_id : this.question!!.id, user_id : 1, like : true}
    this.service.postLike(like)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe(() => {
        this.likes!!++;
        this.disableUp = true;
        this.disableDown = false;
      })
  }
  postUnlike()
  {
    let like : LikeInterface = { question_id : this.question!!.id, user_id : 1, like : false}
    this.service.postLike(like)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe(() => {
        this.likes!!--;
        this.disableUp = false;
        this.disableDown = true;
      })
  }

}
