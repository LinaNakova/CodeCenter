import { Component, OnInit } from '@angular/core';
import {CodeService} from "../code.service";
import {QuestionInterface} from "../questionInterface";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {
  questionsNum : number = 0
  allQuestions : QuestionInterface [] = []
  title = "All Questions"
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  constructor(private codeService : CodeService) { }

  ngOnInit(): void {
    this.getQuestions()
  }
  getQuestions()
  {
    this.codeService.getQuestionsWithoutAnswers().subscribe(q => {
      this.allQuestions = q;
      this.questionsNum = q.length
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
