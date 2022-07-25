import { Component, OnInit } from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-top-questions',
  templateUrl: './top-questions.component.html',
  styleUrls: ['./top-questions.component.css']
})
export class TopQuestionsComponent implements OnInit {
  title = "Top Questions"
  questions : QuestionInterface [] = []
  questionLength : number | undefined;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  constructor(private service:CodeService) { }

  ngOnInit(): void {
    this.getTopQuestions()
  }
  getTopQuestions()
  {
    this.service.getTopQuestions().subscribe({
      next:(questions) => {
        this.questions = questions;
        this.questionLength = questions.length;
        console.log(questions)
      }
    })
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.getTopQuestions();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.getTopQuestions();
  }

}
