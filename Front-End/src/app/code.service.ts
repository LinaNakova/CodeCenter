import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {QuestionInterface} from "./questionInterface";
import {TagInterface} from "./tagInterface";
import {FormInterface} from "./form";

@Injectable({
  providedIn: 'root'
})
export class CodeService {
  questionsUrl = "/api/questions";
  questionsWithoutAnswersUrl = "/api/questions/withoutAnswers";
  tagsUrl = "/api/tag"
  customWordUrl = "/api/questions/tagged"
  questionDetailsUrl = "/api/questions"
  questionAnswersUrl = "/api/questions/answers"

  constructor(private httpClient: HttpClient) { }

  getQuestions() : Observable<QuestionInterface[]>
  {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsUrl}`)
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
             parentQuestionId: number | null,
             appUserId: number,
             tagsId: number[])
  {
    const formObj : FormInterface = {title: title,
      questionText: questionText,
      parentQuestionId: parentQuestionId,
      appUserId: appUserId,
      tagsId: tagsId}
    this.httpClient.post(`http://localhost:4200/api/questions/postAnswer/${parentQuestionId}`,formObj )
      .subscribe({
        next:(response) => console.log(response),
        error:(error) => console.log(error),
      })
  }
}
