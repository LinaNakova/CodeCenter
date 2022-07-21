import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CodeService} from "../code.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-answer',
  templateUrl: './post-answer.component.html',
  styleUrls: ['./post-answer.component.css']
})
export class PostAnswerComponent implements OnInit {
  form: FormGroup;
  userId = 1;
  parentQuestionId : number | null = null;
  constructor(public fb: FormBuilder, private service: CodeService, private router: Router) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }

  ngOnInit(): void {
    let params = location.pathname.split("/")
    this.parentQuestionId = Number(params[2])
  }
  submitForm() {
    this.service.postAnswer(
      this.form.get('title')?.value,
      this.form.get('questionText')?.value,
      this.parentQuestionId,
      this.userId,
      []);
    this.router.navigate(['/questions']);
  }

}
