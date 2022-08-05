import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CodeService} from "../code.service";
import {QuestionInterface} from "../questionInterface";
import {filter, map, Observable, Subject, take, takeUntil} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {TagInterface} from "../tagInterface";
import {StorageService} from "../_services/storage.service";

@Component({
  selector: 'app-question-details',
  templateUrl: './question-details.component.html',
  styleUrls: ['./question-details.component.css']
})
export class QuestionDetailsComponent implements OnInit, OnDestroy {
  input1: String | undefined
  input2: String | undefined
  question: QuestionInterface | undefined
  form: FormGroup;
  userId = 1;
  answers: QuestionInterface[] = [];
  id: number | undefined;
  destroySubject$ = new Subject<void>();
  tags: TagInterface [] = []
  views: number | undefined
  badAnswer = false
  loaded: boolean = false;

  constructor(public fb: FormBuilder,
              private service: CodeService,
              private route: ActivatedRoute,
              private storage : StorageService) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }

  ngOnInit(): void {
    this.getId()
    this.getQuestionDetails()
    this.getQuestionAnswers()
    this.getQuestionTags()
    this.increaseViews()
  }

  getId() {
    this.route.paramMap.pipe(
      takeUntil(this.destroySubject$),
      filter(paramMap => paramMap.has('id')),
      map(paramMap => +paramMap.get('id')!)
    ).subscribe({
      next: (id: number) => {
        this.id = id;
      },
      error: (any) => {
        console.log(any)
      }
    });
  }

  getQuestionDetails() {
    this.service.getQuestionDetails(this.id!).pipe(
      takeUntil(this.destroySubject$)).subscribe({
      next: (q) => {
        this.question = q
        this.loaded = true
      },
      error: (any) => {
        console.log(any)
      }
    })
  }

  getQuestionAnswers() {
    this.service.getQuestionAnswers(this.id!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (answers) => {
          this.answers = answers;
        },
        error: (any) => {
          console.log(any)
        }
      })
  }

  getQuestionTags() {
    this.service.getQuestionTags(this.id!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (tags) => {
          this.tags = tags;
        },
        error: (any) => {
          console.log(any)
        }
      })
  }
  getAppUserId() {
    if (this.storage.getUser() != null) {
      return this.storage.getUser().id
    }
    return undefined;
  }


  submitForm() {
    if (this.form.get('title')?.value == ""
      || this.form.get('questionText')?.value == ""
      || this.form.get('title')?.value == null
      || this.form.get('questionText')?.value == null) {
      this.badAnswer = true;
    } else {
      this.service.postAnswer(
        this.form.get('title')?.value,
        this.form.get('questionText')?.value,
        this.id,
        this.getAppUserId(),
        []
      ).pipe(take(1)).subscribe((response: QuestionInterface) => {
        this.answers.push(response);
      })
      this.input1 = ""
      this.input2 = ""
    }
  }

  increaseViews() {
    this.service.increaseViews(this.id!!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (any) => {
          this.getViews()
        },
        error: (any) => {
          console.log(any)
        }
      })
  }

  getViews() {
    this.service.getViews(this.id!!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (num) => {
          this.views = num
        },
        error: (any) => {
          console.log(any)
        }
      })
  }

  ngOnDestroy() {
    this.destroySubject$.next();
    this.destroySubject$.complete();
  }

  getAscendingQuestions() {
    this.service.getSortedByLikesAscending(this.question!!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (sorted) => {
          this.answers = sorted
        }
      })
  }

  getDescendingQuestions() {
    this.service.getSortedByLikesDescending(this.question!!.id)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (sorted) => {
          this.answers = sorted
        }
      })
  }


}
