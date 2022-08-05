import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of, retry, tap} from "rxjs";
import {QuestionInterface} from "./questionInterface";
import {TagInterface} from "./tagInterface";
import {FormInterface} from "./form";
import {TagFormInterface} from "./TagFormInterface";
import {UserInterface} from "./userInterface";
import {LikeInterface} from "./likeInterface";

@Injectable({
  providedIn: 'root'
})
export class CodeService {
  questionsUrl = "/api/questions";
  sortedByTitleAscending = "/api/questions/sortedByTitle"
  sortedByTitleDescending = "/api/questions/sortedByTitleDescending"
  questionsWithoutAnswersUrl = "/api/questions/withoutAnswers";
  tagsUrl = "/api/tag"
  customWordUrl = "/api/questions/tagged"
  questionDetailsUrl = "/api/questions"
  questionAnswersUrl = "/api/questions/answers"
  questionTagsUrl = "api/questions/tags"
  getUsersUrl = "/api/users"
  getQuestionsWithTag = "/api/tag/allQuestions"
  sortedByViewsAscending = "/api/questions/sortedByViewsAscending"
  sortedByViewsDescending = "/api/questions/sortedByViewsDescending"
  sortedByLikesAscending = "/api/questions/sortedByLikesAscending"
  sortedByLikesDescending = "/api/questions/sortedByLikesDescending"
  sortedByAnswersDescending = "/api/questions/sortedByAnswersDescending"
  sortedByAnswersAscending = "/api/questions/sortedByAnswersAscending"
  sortedByDateAscending = "/api/questions/sortedByTimestamp"
  sortedByDateDescending = "/api/questions/sortedByTimestampDescending"

  constructor(private httpClient: HttpClient) {
  }

  getQuestions(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsUrl}`)
  }

  getSortedByTitleAscending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByTitleAscending}`)
  }

  getSortedByTitleDescending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByTitleDescending}`)
  }

  getQuestionsWithoutAnswers(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsWithoutAnswersUrl}`)
  }

  getQuestionsWithMentionedWord(word: string): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.customWordUrl}/${word}`)
  }

  getTags(): Observable<TagInterface[]> {
    return this.httpClient.get<TagInterface[]>(`${this.tagsUrl}`);
  }

  getQuestionDetails(id: number): Observable<QuestionInterface> {
    return this.httpClient.get<QuestionInterface>(`${this.questionDetailsUrl}/${id}`)
  }

  getQuestionAnswers(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionAnswersUrl}/${id}`)
  }

  getQuestionTags(id: number): Observable<TagInterface[]> {
    return this.httpClient.get<TagInterface[]>(`${this.questionTagsUrl}/${id}`)
  }

  getUsers(): Observable<UserInterface[]> {
    return this.httpClient.get<UserInterface[]>(`http://localhost:4200${this.getUsersUrl}`)
  }

  getAllQuestionsWithTag(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.getQuestionsWithTag}/${id}`)
  }

  getById(id: number): Observable<TagInterface> {
    return this.httpClient.get<TagInterface>(`${this.tagsUrl}/${id}`)
  }


  postForm(title: any,
           questionText: any,
           parentQuestionId: number | null,
           appUserId: number,
           tagsId: number[]) {
    const formObj: FormInterface = {
      title: title,
      questionText: questionText,
      parentQuestionId: parentQuestionId,
      appUserId: appUserId,
      tagsId: tagsId
    }
    this.httpClient.post("http://localhost:4200/api/questions", formObj)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      })
  }

  postAnswer(title: any,
             questionText: any,
             parentQuestionId: number | undefined | null,
             appUserId: number,
             tagsId: number[]): Observable<QuestionInterface> {
    const formObj: FormInterface = {
      title: title,
      questionText: questionText,
      parentQuestionId: parentQuestionId,
      appUserId: appUserId,
      tagsId: tagsId
    }
    return this.httpClient.post<QuestionInterface>(`http://localhost:4200/api/questions/postAnswer/${parentQuestionId}`, formObj)
  }

  searchTags(tag: String): Observable<TagInterface[]> {
    if (!tag.trim()) {
      return of([]);
    }
    return this.httpClient.get<TagInterface[]>(`http://localhost:4200/api/tag/search/${tag}`)
  }

  postTag(name: string, description: string) {
    name = name.toLowerCase()
    // let capitalized = name[0].toUpperCase() + name.slice(1)
    const tag: TagFormInterface = {name: name, description: description}
    this.httpClient.post("http://localhost:4200/api/tag", tag)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      })
  }

  postLike(like: LikeInterface) {
    return this.httpClient.post("http://localhost:4200/api/questions/likes", like)
  }

  getLikes(id: number): Observable<number> {
    return this.httpClient.get<number>(`${this.questionsUrl}/likes/${id}`)
  }

  increaseViews(id: number) {
    return this.httpClient.post(`http://localhost:4200/api/questions/increase/${id}`, "")
  }

  getViews(id: number): Observable<number> {
    return this.httpClient.get<number>(`${this.questionsUrl}/views/${id}`)
  }

  getUserById(id: number): Observable<UserInterface> {
    return this.httpClient.get<UserInterface>(`${this.getUsersUrl}/${id}`)
  }

  getQuestionsFromUser(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsUrl}/fromUser/${id}`)
  }

  getAnswersFromUser(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.questionsUrl}/answersFromUser/${id}`)
  }

  getTagsFromUser(id: number) {
    return this.httpClient.get<TagInterface[]>(`${this.tagsUrl}/tagsFromUser/${id}`)
  }

  getSortedByViewsAscending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByViewsAscending}`)
  }

  getSortedByViewsDescending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByViewsDescending}`)
  }

  getSortedByLikesAscending(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByLikesAscending}/${id}`)
  }

  getSortedByLikesDescending(id: number): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByLikesDescending}/${id}`)
  }

  getSortedByAnswersDescending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByAnswersDescending}`)
  }

  getSortedByAnswersAscending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByAnswersAscending}`)
  }

  getSortedByDate(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByDateAscending}`)
  }

  getSortedByDateDescending(): Observable<QuestionInterface[]> {
    return this.httpClient.get<QuestionInterface[]>(`${this.sortedByDateDescending}`)
  }
  getQuestionLiked(qid : number, uid : number) : Observable<boolean>
  {
    return this.httpClient.get<boolean>(`${this.questionsUrl}/checkIfLikedByUser/${qid}/${uid}`)
  }

}
