import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CodeService} from "../code.service";
import {QuestionInterface} from "../questionInterface";
import {LoadingService} from "../loading.service";
import { filter, map, Observable, Subject, take, takeUntil} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-question-details',
  templateUrl: './question-details.component.html',
  styleUrls: ['./question-details.component.css']
})
export class QuestionDetailsComponent implements OnInit, OnDestroy {
  question: QuestionInterface | undefined
  answers$: Observable<QuestionInterface[]> | undefined;
  params = location.pathname.split("/")
  form: FormGroup;
  userId = 1;
  answers: QuestionInterface[] = [];
  id: number | undefined;
  destroySubject$ = new Subject<void>();
  tags : String [] = []

  constructor(public fb: FormBuilder,
              private service: CodeService,
              public loader: LoadingService,
              private _route: ActivatedRoute) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }

  ngOnInit(): void {
    console.log(this._route.snapshot.paramMap);
    this._route.paramMap.pipe(
      takeUntil(this.destroySubject$),
      filter(paramMap => paramMap.has('id')),
      map(paramMap => +paramMap.get('id')!)
    ).subscribe({
      next: (id: number) => {
        this.id = id;
      }
    });
    this.service.getQuestionDetails(this.id!).pipe(
      takeUntil(this.destroySubject$)).subscribe({
      next: (q) => {
        this.question = q
      }
    })
    this.service.getQuestionAnswers(this.id!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
      next: (answers) => {
        this.answers = answers;
      }
    })
    this.service.getQuestionTags(this.id!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (tags) => {
          this.tags = tags;
        }
      })
  }

  submitForm() {
    this.service.postAnswer(
      this.form.get('title')?.value,
      this.form.get('questionText')?.value,
      this.id,
      this.userId,
      []
    ).pipe(take(1)).subscribe((response: QuestionInterface) => {
      console.log(response);
      this.answers.push(response);
    })
  }

  ngOnDestroy() {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }

}
