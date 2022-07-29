import {Component, Input, OnInit} from '@angular/core';
import {QuestionInterface} from "../questionInterface";
import {Subject, takeUntil} from "rxjs";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-small-answer-card',
  templateUrl: './small-answer-card.component.html',
  styleUrls: ['./small-answer-card.component.css']
})
export class SmallAnswerCardComponent implements OnInit {
  @Input()
  question : QuestionInterface | undefined
  previewAnswer : string | undefined
  constructor(private service : CodeService) { }


  ngOnInit(): void {
    if (this.question!!.questionText.length > 10)
    {
      this.previewAnswer = this.question!!.questionText.substring(0,10) + "...";
    }
    else {
      this.previewAnswer = this.question!!.questionText
    }
  }

}
