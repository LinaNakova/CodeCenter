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

  constructor(private service: CodeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    console.log("called ngOnInit")
    this.getTag()
  }

  getTag() {
    console.log(this.route.snapshot.paramMap)
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
      .subscribe(ans => {
        this.allQuestions = ans;
        console.log(this.allQuestions)
      })
  }

  ngOnDestroy(): void {
    this.destroySubject$.next()
    this.destroySubject$.complete()
  }

}
