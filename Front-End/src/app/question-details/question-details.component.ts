import {Component, Input, OnInit} from '@angular/core';
import {CodeService} from "../code.service";
import {QuestionInterface} from "../questionInterface";

@Component({
  selector: 'app-question-details',
  templateUrl: './question-details.component.html',
  styleUrls: ['./question-details.component.css']
})
export class QuestionDetailsComponent implements OnInit {
  question : QuestionInterface | undefined
  constructor(private service: CodeService) { }

  ngOnInit(): void {
    let params = location.pathname.split("/")
    this.service.getQuestionDetails(Number(params[2])).subscribe(q => {
      this.question = q
    })
  }

}
