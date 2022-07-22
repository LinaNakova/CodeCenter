package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.repository.QuestionRepository
import com.sorsix.backendapplication.repository.QuestionTagRepository
import com.sorsix.backendapplication.repository.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QuestionService(
    val questionRepository: QuestionRepository,
    val tagRepository: TagRepository,
    val questionTagRepository: QuestionTagRepository,
    val appUserRepository: AppUserRepository,
) {

    fun findAllQuestionsWithoutAnswers(): List<Question>? {
        return questionRepository.findAll().filter { it.parentQuestion == null };
    }

    fun findAll(): List<Question>? {
        return questionRepository.findAll();
    }

    @Transactional
    fun createQuestion(
        title: String,
        questionText: String,
        parentQuestionId: Long?,
        appUserId: Long,
        tagsId: List<Long>?,
    ): QuestionResult {
        val tags = tagsId?.let { tagRepository.findAllById(it) }
        val appUser: AppUser? = appUserId.let {
            appUserRepository.findByIdOrNull(it)
        }
        val parentQuestion: Question? = parentQuestionId.let {
            if (it != null) {
                questionRepository.findByIdOrNull(it)
            } else {
                null;
            }
        }
        return if (tags == null || appUser == null) {
            QuestionFailed("error :)")
        } else {

            val question = Question(title = title, questionText = questionText,
                parentQuestion = parentQuestion, user = appUser)
            println(question)
            println(tags);
            questionRepository.save(question);
            tags.forEach { it -> questionTagRepository.save(QuestionTag(0, question = question, tag = it)) }
            QuestionCreated(question = question);
        }


    }

    @Transactional
    fun postAnswer(
        title: String,
        questionText: String,
        parentQuestionId: Long,
        appUserId: Long,
    ): QuestionResult {
        val appUser: AppUser? = appUserId.let {
            appUserRepository.findByIdOrNull(it)
        }
        val parentQuestion: Question? = parentQuestionId.let { questionRepository.findByIdOrNull(it) }
        return if (appUser == null || parentQuestion == null) {
            QuestionFailed("error creating answer:)")
        } else {
            val question = Question(title = title, questionText = questionText,
                parentQuestion = parentQuestion, user = appUser)
            println(question)
            questionRepository.save(question);
            QuestionCreated(question = question);
        }
    }

    fun findById(id: Long): Question? {
        return questionRepository.findByIdOrNull(id);
    }

    fun getAnswersForQuestion(id: Long): List<Question>? {
        return questionRepository.findAll().filter { id == it.parentQuestion?.id };
    }

    fun findAllQuestionsWithMentionedWord(word: String): List<Question>? {
        return questionRepository.findAll().filter { it.title.contains(word) || it.questionText.contains(word) }
    }
    fun getAllQuestionTags(id : Long) : List<QuestionTag>
    {
        return questionTagRepository.findAll()
    }
    fun getQuestionTags(id : Long) : List<QuestionTag>
    {
        return getAllQuestionTags(id).filter { it.question.id == id }
    }

}