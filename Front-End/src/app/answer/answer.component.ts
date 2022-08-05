import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {Subject, takeUntil} from "rxjs";
import {CodeService} from "../code.service";
import {LikeInterface} from "../likeInterface";
import {StorageService} from "../_services/storage.service";

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
  liked : boolean | undefined
  isLoggedIn : boolean | undefined
  user : any
  like : boolean | undefined
  constructor(private service : CodeService,
              private storage : StorageService) { }

  ngOnInit(): void {
    this.getLikes();
    this.isLoggedIn = this.storage.isLoggedIn()
    if (this.isLoggedIn)
    {
      this.user = this.storage.getUser()
      this.getQuestionLikedByUser()
    }
  }

  getQuestionLikedByUser()
  {
    this.service.getQuestionLiked(this.question!!.id, this.user.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next:(like) => {
          this.like = like
          console.log(like + " blabalbalalbal")
          this.isQuestionLikedByUser(this.like)
        },
        error: (err) => {
          console.log(err)
        }
      })
  }
  isQuestionLikedByUser(like : boolean)
  {
    this.liked = like
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
    let like : LikeInterface = { question_id : this.question!!.id, user_id : this.user.id, like : true}
    this.service.postLike(like)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe(() => {
        this.likes!!++;
        // this.disableUp = true;
        // this.disableDown = false;
        this.liked = true;
      })
  }
  postUnlike()
  {
    let like : LikeInterface = { question_id : this.question!!.id, user_id : this.user.id, like : false}
    this.service.postLike(like)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe(() => {
        this.likes!!--;
        this.liked = false
      })
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }


}
