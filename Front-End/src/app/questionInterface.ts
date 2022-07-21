import {UserInterface} from "./userInterface";

export interface QuestionInterface
{
  "id": number,
  "title": string
  "questionText": string,
  "parentQuestion": QuestionInterface | undefined | null,
  "user": UserInterface
}
