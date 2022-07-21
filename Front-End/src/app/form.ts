export interface FormInterface
{
   title: String,
   questionText: String,
   parentQuestionId: number | null | undefined,
   appUserId: number,
   tagsId: number[]
}
