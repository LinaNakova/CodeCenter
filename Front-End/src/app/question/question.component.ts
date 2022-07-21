import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  tags : number [] = [1,2,3,4,5]
  constructor() { }

  ngOnInit(): void {
    console.log(this.question)

  }

}
