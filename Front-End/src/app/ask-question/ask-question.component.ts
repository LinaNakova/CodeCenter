import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CodeService} from "../code.service";
import {Route, Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, Observable, Subject, switchMap} from "rxjs";
import {TagInterface} from "../tagInterface";
import {StorageService} from "../_services/storage.service";

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {
  title = "Ask a Question"
  listOfTags: number [] = []
  userId: number = 2
  parentQuestionId = null;
  form: FormGroup;
  bold = false;
  italic = false;
  tags$!: Observable<TagInterface[]>
  tags: TagInterface [] = []
  badAnswer = false
  fiveTags = false
  private searchTerms = new Subject<string>();

  constructor(public fb: FormBuilder, private service: CodeService, private router: Router,
              private storage: StorageService) {
    this.form = this.fb.group({
      title: [""],
      questionText: [""],
      parentQuestionId: [""],
      appUserId: [""],
      tagsId: [null],
    })
  }

  getAppUserId() {
    if (this.storage.getUser() != null) {
      return this.storage.getUser().id
    }
    return undefined;
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.tags$ = this.searchTerms.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap((term: string) => this.service.searchTags(term.toLowerCase())),
    );
  }

  submitForm() {
    if (this.form.get('title')?.value == ""
      || this.form.get('questionText')?.value == ""
      || this.form.get('title')?.value == null
      || this.form.get('questionText')?.value == null) {
      this.badAnswer = true
    } else {
      this.service.postForm(
        this.form.get('title')?.value,
        this.form.get('questionText')?.value,
        this.parentQuestionId,
        this.userId,
        this.listOfTags);
      this.router.navigate(['/questions']);
    }
  }


  addToTagList(t: TagInterface) {
    if (this.listOfTags.length >= 5)
    {
      this.fiveTags = true
    }
    else if (this.tags.findIndex(tag => { return tag.name === t.name}) == -1) {
      this.tags.push(t)
      this.listOfTags.push(t?.id)
    }
  }

  deleteTag(t: TagInterface) {
    const index = this.tags.findIndex(tag => {
      return tag.name === t.name
    })
    this.tags.splice(index, 1)
    this.listOfTags.splice(index, 1)
    if (this.listOfTags.length < 5)
    {
      this.fiveTags = false
    }
  }

}
