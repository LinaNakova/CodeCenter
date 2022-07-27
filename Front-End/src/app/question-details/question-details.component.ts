import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CodeService} from "../code.service";
import {QuestionInterface} from "../questionInterface";
import { filter, map, Observable, Subject, take, takeUntil} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-question-details',
  templateUrl: './question-details.component.html',
  styleUrls: ['./question-details.component.css']
})
export class QuestionDetailsComponent implements OnInit, OnDestroy {
  input1:String|undefined
  input2:String|undefined
  question: QuestionInterface | undefined
  form: FormGroup;
  userId = 1;
  answers: QuestionInterface[] = [];
  id: number | undefined;
  destroySubject$ = new Subject<void>();
  tags : String [] = []

  constructor(public fb: FormBuilder,
              private service: CodeService,
              private route: ActivatedRoute) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }

  ngOnInit(): void {
    console.log(this.route.snapshot.paramMap);
    this.route.paramMap.pipe(
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
      this.answers.push(response);
    })
    this.input1=""
    this.input2=""
  }

  ngOnDestroy() {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }

}
