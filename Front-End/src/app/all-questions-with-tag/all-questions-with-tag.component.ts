import { Component, OnInit, OnDestroy } from '@angular/core';
import {filter, map, Subject, takeUntil} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-all-questions-with-tag',
  templateUrl: './all-questions-with-tag.component.html',
  styleUrls: ['./all-questions-with-tag.component.css']
})
export class AllQuestionsWithTagComponent implements OnInit {
  id : number | undefined
  destroySubject$ = new Subject<void>()
  questions : QuestionInterface [] = []
  tag : TagInterface | undefined
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  constructor(private route:ActivatedRoute, private service: CodeService) { }

  ngOnInit(): void {
    this.getParameter()
    this.getQuestions()
    this.getTag()
  }
  getParameter()
  {
    this.route.paramMap.pipe(
      takeUntil(this.destroySubject$),
      filter(paramMap => paramMap.has('id')),
      map(paramMap => +paramMap.get('id')!)
    ).subscribe({
      next: (id: number) => {
        this.id = id;
        console.log(this.id)
      }
    });
  }
  getQuestions()
  {
     this.service.getAllQuestionsWithTag(this.id!!)
       .subscribe({
         next:(q) =>
    {
      this.questions = q
    }
       })
  }
  ngOnDestroy() : void
  {
    this.destroySubject$.next()
    this.destroySubject$.complete()
  }
  getTag()
  {
    this.service.getById(this.id!!)
      .subscribe({
        next:(tag) =>
        {
          this.tag = tag;
        }
      })
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.getQuestions();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.getQuestions();
  }

}
