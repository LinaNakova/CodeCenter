import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AskQuestionComponent } from './ask-question/ask-question.component';
import { BaseComponent } from './base/base.component';
import { HeadingComponent } from './heading/heading.component';
import { QuestionComponent } from './question/question.component';
import { QuestionsComponent } from './questions/questions.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { TagComponent } from './tag/tag.component';
import { TagsComponent } from './tags/tags.component';
import { TopQuestionsComponent } from './top-questions/top-questions.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
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
    QuestionAnswersComponent,
    PostAnswerComponent,
    AddTagComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    NgxPaginationModule
  ],
  providers: [ {
    provide: HTTP_INTERCEPTORS,
    useClass: NetworkInterceptor,
    multi: true,
  },],
  bootstrap: [AppComponent]
})
export class AppModule { }
