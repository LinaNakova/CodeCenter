import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CodeService} from "../code.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {
  title = "Ask a Question"
  listOfTags : number [] = [323,54353,231,5454]
  userId : number = 1
  parentQuestionId = null;
  form : FormGroup;
  constructor(public fb: FormBuilder, private service : CodeService, private router : Router) {
    this.form = this.fb.group({
      title:[""],
      questionText:[""],
      parentQuestionId:[""],
      appUserId:[""],
      tagsId:[null],
    })
  }

  ngOnInit(): void {
  }
  submitForm()
  {

    this.service.postForm(
      this.form.get('title')?.value,
      this.form.get('questionText')?.value,
      this.parentQuestionId,
      this.userId,
      this.listOfTags  );
    this.router.navigate(['/questions']);
  }

}
