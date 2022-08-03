import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TopQuestionsComponent} from "./top-questions/top-questions.component";
import {QuestionsComponent} from "./questions/questions.component";
import {TagsComponent} from "./tags/tags.component";
import {AskQuestionComponent} from "./ask-question/ask-question.component";
import {SearchResultsComponent} from "./search-results/search-results.component";
import {QuestionDetailsComponent} from "./question-details/question-details.component";
import {AddTagComponent} from "./add-tag/add-tag.component";
import {UsersComponent} from "./users/users.component";
import {AllQuestionsWithTagComponent} from "./all-questions-with-tag/all-questions-with-tag.component";
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {BoardUserComponent} from "./board-user/board-user.component";
import {BoardModeratorComponent} from "./board-moderator/board-moderator.component";
import {BoardAdminComponent} from "./board-admin/board-admin.component";

const routes: Routes = [
  {path: "top-questions", component: TopQuestionsComponent},
  {path: "questions", component: QuestionsComponent},
  {path: "tags", component: TagsComponent},
  {path: "askQuestion", component: AskQuestionComponent},
  {path: "questions/tagged/:tag", component: SearchResultsComponent},
  {path: "questions/:id", component: QuestionDetailsComponent},
  {path: "addTag", component: AddTagComponent},
  {path: "users", component: UsersComponent},
  {path: "allQuestionsWithTag/:id", component: AllQuestionsWithTagComponent},
  {path: "", redirectTo: '/top-questions', pathMatch: "full"},

  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'user', component: BoardUserComponent},
  {path: 'mod', component: BoardModeratorComponent},
  {path: 'admin', component: BoardAdminComponent},

  {path: "**", redirectTo: '/top-questions', pathMatch: "full"},

// const routes: Routes = [
//   {path:"top-questions", component:TopQuestionsComponent},
//   {path:"questions", component:QuestionsComponent},
//   {path:"tags", component:TagsComponent},
//   {path:"askQuestion", component:AskQuestionComponent},
//   {path:"questions/tagged/:tag", component: SearchResultsComponent},
//   {path:"questions/:id", component: QuestionDetailsComponent},
//   {path:"addTag", component: AddTagComponent},
//   {path:"users", component: UsersComponent},
//   {path:"allQuestionsWithTag/:id", component: AllQuestionsWithTagComponent},
//   {path:"profile/:id", component: ProfileComponent},
//   {path:"", redirectTo: '/top-questions', pathMatch:"full"},
//   {path:"**", redirectTo: '/top-questions', pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
