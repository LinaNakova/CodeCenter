import {Component, OnInit, OnDestroy} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-top-questions',
  templateUrl: './top-questions.component.html',
  styleUrls: ['./top-questions.component.css']
})
export class TopQuestionsComponent implements OnInit {
  title = "Top Questions"
  questions: QuestionInterface [] = []
  questionLength: number | undefined;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  tableSizes: any = [3, 6, 9, 12];
  destroySubject$ = new Subject<void>()
  loaded = false

  constructor(private service: CodeService) {
  }

  ngOnInit(): void {
    this.getAlphabeticalAscending()
  }

  getAlphabeticalAscending() {
    this.service.getSortedByTitleAscending().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
        this.loaded = true
      }
    })
  }

  onTableDataChange(event: any) {
    this.page = event;
  }

  getDescendingQuestions() {
    this.service.getSortedByViewsDescending()
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (sorted) => {
          this.questions = sorted
          this.page = 1
        }
      })
  }

  getAscendingQuestions() {
    this.service.getSortedByViewsAscending()
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (sorted) => {
          this.questions = sorted
          this.page = 1
        }
      })
  }

  getAlphabeticalDescending() {
    this.service.getSortedByTitleDescending().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
      }
    })
  }

  getSortedByAnswersAscending() {
    this.service.getSortedByAnswersAscending().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
      }
    })
  }

  getSortedByAnswersDescending() {
    this.service.getSortedByAnswersDescending().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
      }
    })
  }

  getSortedByDateAscending() {
    this.service.getSortedByDate().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
      }
    })
  }

  getSortedByDateDescending() {
    this.service.getSortedByDateDescending().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.page = 1
      }
    })
  }

  ngOnDestroy() {
    this.destroySubject$.next()
    this.destroySubject$.complete()
  }

}
