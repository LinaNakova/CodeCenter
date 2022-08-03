import {Component, OnInit, OnDestroy} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {filter, map, queue, Subject, takeUntil} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  title : string | undefined
  allQuestions: QuestionInterface [] = []
  destroySubject$ = new Subject<void>();
  searchItem: string | undefined
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: any = [10, 15, 20, 25];
  loaded = false
  constructor(private service: CodeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getTag()
  }

  getTag() {
    this.route.paramMap
      .pipe(takeUntil(this.destroySubject$),
        filter(paramMap => paramMap.has('tag')),
        map(paramMap => paramMap.get('tag')!)
      ).subscribe({
      next: (tag: string) => {
        this.searchItem = tag
        this.title = 'Search Results for \" ' + tag + '\"';
        this.getQuestions();
      }
    })
  }
  getQuestions()
  {
    this.service.getQuestionsWithMentionedWord(this.searchItem!!)
      .subscribe({
        next:(ans) => {
          this.allQuestions = ans;
          this.loaded = true
        }
      })
  }

  ngOnDestroy(): void {
    this.destroySubject$.next()
    this.destroySubject$.complete()
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.getQuestions();
  }

  onTableSizeChange(event: any): void {
    console.log(event.target.value)
    this.tableSize = event.target.value;
    this.page = 1;
    this.getQuestions();
  }

}
