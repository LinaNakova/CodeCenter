import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TopQuestionsComponent} from "./top-questions/top-questions.component";
import {QuestionsComponent} from "./questions/questions.component";
import {TagsComponent} from "./tags/tags.component";
import {AskQuestionComponent} from "./ask-question/ask-question.component";
import {SearchResultsComponent} from "./search-results/search-results.component";

const routes: Routes = [
  {path:"top-questions", component:TopQuestionsComponent},
  {path:"questions", component:QuestionsComponent},
  {path:"tags", component:TagsComponent},
  {path:"askQuestion", component:AskQuestionComponent},
  {path:"questions/tagged/:tag", component: SearchResultsComponent},
  {path:"", redirectTo: '/top-questions', pathMatch:"full"},
  {path:"**", redirectTo: '/top-questions', pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
