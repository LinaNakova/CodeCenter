export interface FormInterface
{
   title: String,
   questionText: String,
   parentQuestionId: number | null,
   appUserId: number,
   tagsId: number[]
}
