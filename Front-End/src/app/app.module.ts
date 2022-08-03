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
import {SearchResultsComponent} from './search-results/search-results.component';
import {QuestionDetailsComponent} from './question-details/question-details.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AddTagComponent} from './add-tag/add-tag.component';
import {NgxPaginationModule} from "ngx-pagination";
import {UserComponent} from './user/user.component';
import {UsersComponent} from './users/users.component';
import {AllQuestionsWithTagComponent} from './all-questions-with-tag/all-questions-with-tag.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardModeratorComponent} from './board-moderator/board-moderator.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {httpInterceptorProviders} from './_helpers/http.interceptor';

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
    AddTagComponent,
    UserComponent,
    UsersComponent,
    AllQuestionsWithTagComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxPaginationModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
