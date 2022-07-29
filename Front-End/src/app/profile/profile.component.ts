import { Component, OnInit, OnDestroy } from '@angular/core';
import {CodeService} from "../code.service";
import {ActivatedRoute} from "@angular/router";
import {UserInterface} from "../userInterface";
import {filter, map, Subject, takeUntil} from "rxjs";
import {QuestionsComponent} from "../questions/questions.component";
import {QuestionInterface} from "../questionInterface";
import {TagInterface} from "../tagInterface";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  id : number | undefined
  user : UserInterface | undefined
  destroySubject$ = new Subject<void>()
  questions : QuestionInterface [] = []
  answers : QuestionInterface [] = []
  tags : TagInterface [] = []
  constructor(private service: CodeService, private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.getId()
    this.getQuestions()
    this.getAnswers()
    this.getTags()
  }

  getId()
  {
    console.log(this.route.snapshot.paramMap)
    this.route.paramMap
      .pipe(takeUntil(this.destroySubject$),
            filter(paramMap => paramMap.has('id')),
            map(paramMap => +paramMap.get('id')!)
      ).subscribe({
      next: (num) => {
        this.id = num;
        this.getUser();
      }
    })
  }
  getUser()
  {
    this.service.getUserById(this.id!!)
      .subscribe({
        next: (user) =>
    {
      this.user = user
    }
      })
  }

  ngOnDestroy()
  {
    this.destroySubject$.next()
    this.destroySubject$.complete()
  }
  getQuestions()
  {
    this.service.getQuestionsFromUser(this.id!!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
          next : (questions) => {
            this.questions = questions
          }
        }
      )
  }
  getAnswers()
  {
    this.service.getAnswersFromUser(this.id!!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next : (answers) => {
          this.answers = answers
        }
        }
      )
  }
  getTags()
  {
    this.service.getTagsFromUser(this.id!!)
      .pipe(takeUntil(this.destroySubject$))
      .subscribe({
        next: (tags) =>
        {
          this.tags = tags
        }
      })
  }

}
