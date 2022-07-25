import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AskQuestionComponent} from './ask-question/ask-question.component';
import {BaseComponent} from './base/base.component';
import {HeadingComponent} from './heading/heading.component';
import {QuestionComponent} from './question/question.component';
import {QuestionsComponent} from './questions/questions.component';
import {SearchBarComponent} from './search-bar/search-bar.component';
import {TagComponent} from './tag/tag.component';
import {TagsComponent} from './tags/tags.component';
import {TopQuestionsComponent} from './top-questions/top-questions.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
<<<<<<< HEAD
import {SearchResultsComponent} from './search-results/search-results.component';
import {QuestionDetailsComponent} from './question-details/question-details.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
=======
import { SearchResultsComponent } from './search-results/search-results.component';
import { QuestionDetailsComponent } from './question-details/question-details.component';
import { QuestionAnswersComponent } from './question-answers/question-answers.component';
import { PostAnswerComponent } from './post-answer/post-answer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {NetworkInterceptor} from "./network.interceptor";
import { NgxPaginationModule } from 'ngx-pagination';
import { AddTagComponent } from './add-tag/add-tag.component';
>>>>>>> 44b9539ff8304705bd6053b48ac4a021a2db74ac

@NgModule({
  declarations: [
    AppComponent,
    AskQuestionComponent,
    BaseComponent,
    HeadingComponent,
    QuestionComponent,
    QuestionsComponent,
    SearchBarComponent,
    TagComponent,
    TagsComponent,
    TopQuestionsComponent,
    SearchResultsComponent,
    QuestionDetailsComponent,
<<<<<<< HEAD
=======
    QuestionAnswersComponent,
    PostAnswerComponent,
    AddTagComponent
>>>>>>> 44b9539ff8304705bd6053b48ac4a021a2db74ac
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
<<<<<<< HEAD
=======
    MatButtonModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    NgxPaginationModule
>>>>>>> 44b9539ff8304705bd6053b48ac4a021a2db74ac
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
