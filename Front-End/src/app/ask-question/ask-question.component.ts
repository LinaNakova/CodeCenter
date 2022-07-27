import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CodeService} from "../code.service";
import {Route, Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, Observable, Subject, switchMap} from "rxjs";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {
  title = "Ask a Question"
  listOfTags: number [] = []
  userId: number = 1
  parentQuestionId = null;
  form: FormGroup;
  bold = false;
  italic = false;
  tags$! :Observable<TagInterface[]>
  tags : string [] = []
  private searchTerms = new Subject<string>();

  constructor(public fb: FormBuilder, private service: CodeService, private router: Router) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.tags$ = this.searchTerms.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.service.searchTags(term)),
    );
  }

  submitForm() {
    this.service.postForm(
      this.form.get('title')?.value,
      this.form.get('questionText')?.value,
      this.parentQuestionId,
      this.userId,
      this.listOfTags);
    this.router.navigate(['/questions']);
  }

  makeBold() {
    this.bold = !this.bold;
  }
  makeItalic()
  {
    this.italic = !this.italic;
  }
  addToTagList(id : number, name:string)
  {
    console.log(id)
    this.tags.push(name)
    this.listOfTags.push(id)
  }

}
