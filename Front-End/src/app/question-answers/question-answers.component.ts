import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-question-answers',
  templateUrl: './question-answers.component.html',
  styleUrls: ['./question-answers.component.css']
})
export class QuestionAnswersComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  answers : QuestionInterface [] | undefined
  constructor(private service : CodeService) { }

  ngOnInit(): void {
    let params = location.pathname.split("/")
    this.service.getQuestionAnswers(Number(params[2])).subscribe(q => {
      this.answers = q
      console.log(this.answers)
    })
  }

}
