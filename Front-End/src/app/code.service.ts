import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of, retry, tap} from "rxjs";
import {QuestionInterface} from "./questionInterface";
import {TagInterface} from "./tagInterface";
import {FormInterface} from "./form";
import {TagFormInterface} from "./TagFormInterface";
import {UserInterface} from "./userInterface";

@Injectable({
  providedIn: 'root'
})
export class CodeService {
  questionsUrl = "/api/questions";
  topQuestionsUrl = "/api/questions/sorted"
  questionsWithoutAnswersUrl = "/api/questions/withoutAnswers";
  tagsUrl = "/api/tag"
  customWordUrl = "/api/questions/tagged"
  questionDetailsUrl = "/api/questions"
  questionAnswersUrl = "/api/questions/answers"
  questionTagsUrl = "api/questions/tags"
  getUsersUrl="/api/users"
  getQuestionsWithTag = "/api/tag/allQuestions"

  constructor(private httpClient: HttpClient) { }

  getQuestions() : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsUrl}`)
  }
  getTopQuestions() : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.topQuestionsUrl}`)
  }
  getQuestionsWithoutAnswers() : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsWithoutAnswersUrl}`)
  }
  getQuestionsWithMentionedWord(word:string) : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.customWordUrl}/${word}`)
  }
  getTags() : Observable<TagInterface[]>
  {
    return this.httpClient.get<TagInterface[]>(`${this.tagsUrl}`);
  }
  getQuestionDetails(id : number) : Observable<QuestionInterface>
  {
    return this.httpClient.get<QuestionInterface>(`${this.questionDetailsUrl}/${id}`)
  }
  getQuestionAnswers(id : number) : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionAnswersUrl}/${id}`)
  }
  getQuestionTags(id : number) : Observable<String[]>
  {
    return this.httpClient.get<String[]>(`${this.questionTagsUrl}/${id}`)
  }
  getUsers() : Observable<UserInterface[]>
  {
    return this.httpClient.get<UserInterface[]>(`http://localhost:4200${this.getUsersUrl}`)
  }
  getAllQuestionsWithTag(id : number) : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.getQuestionsWithTag}/${id}`)
  }
  getById(id:number) : Observable<TagInterface>
  {
    return this.httpClient.get<TagInterface>(`${this.tagsUrl}/${id}`)
  }


  postForm(title: any,
           questionText: any,
           parentQuestionId: number | null,
           appUserId: number,
           tagsId: number[])
  {
    const formObj : FormInterface = {title: title,
      questionText: questionText,
      parentQuestionId: parentQuestionId,
      appUserId: appUserId,
      tagsId: tagsId}
    this.httpClient.post("http://localhost:4200/api/questions",formObj )
      .subscribe({
        next:(response) => console.log(response),
        error:(error) => console.log(error),
      })
  }
  postAnswer(title: any,
             questionText: any,
             parentQuestionId: number | undefined | null,
             appUserId: number,
             tagsId: number[]) : Observable<QuestionInterface>
  {
    const formObj : FormInterface = {title: title,
      questionText: questionText,
      parentQuestionId: parentQuestionId,
      appUserId: appUserId,
      tagsId: tagsId}
    return this.httpClient.post<QuestionInterface>(`http://localhost:4200/api/questions/postAnswer/${parentQuestionId}`,formObj )
  }
  searchTags(tag:String) : Observable<TagInterface[]>
  {
    if (!tag.trim()) {
      return of([]);
    }
    return this.httpClient.get<TagInterface[]>(`http://localhost:4200/api/tag/search/${tag}`)
  }
  postTag(name:string, description:string)
  {
    let capitalized = name[0].toUpperCase() + name.slice(1)
    const tag : TagFormInterface = {name : capitalized, description : description}
    this.httpClient.post("http://localhost:4200/api/tag",tag )
      .subscribe({
        next:(response) => console.log(response),
        error:(error) => console.log(error),
      })
  }

}
