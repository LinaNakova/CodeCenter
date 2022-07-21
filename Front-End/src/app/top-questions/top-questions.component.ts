import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-questions',
  templateUrl: './top-questions.component.html',
  styleUrls: ['./top-questions.component.css']
})
export class TopQuestionsComponent implements OnInit {
  questions : number [] = [1,2,3,4,5];
  title = "Top Questions"
  constructor() { }

  ngOnInit(): void {
  }

}
