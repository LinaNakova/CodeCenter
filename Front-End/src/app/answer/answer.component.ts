import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {Subject, takeUntil} from "rxjs";
import {CodeService} from "../code.service";
import {LikeInterface} from "../likeInterface";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  destroySubject$ = new Subject<void>()
  likes : number | undefined
  disableUp : boolean = false
  disableDown : boolean = false
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    this.getLikes();
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next();
    this.destroySubject$.complete();
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
