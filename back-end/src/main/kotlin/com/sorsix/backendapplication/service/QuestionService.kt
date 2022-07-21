package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.repository.QuestionRepository
import com.sorsix.backendapplication.repository.QuestionTagRepository
import com.sorsix.backendapplication.repository.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

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

    fun createQuestion(
        title: String,
        questionText: String,
        parentQuestionId: Long?,
        appUserId: Long,
        tagsId: List<Long>?,
    ): QuestionResult {
        val tags = tagsId?.let { tagRepository.findAllById(it) }
        println(tags)
        //val appUser = appUserRepository.findByIdOrNull(appUserId);
        val appUser: AppUser? = appUserId.let {
            appUserRepository.findByIdOrNull(it)
        }
        println(appUser)
        //val parentQuestion = questionRepository.findByIdOrNull(parentQuestionId);
        val parentQuestion: Question? = parentQuestionId.let {
            if (it != null) {
                questionRepository.findByIdOrNull(it)
            } else {
                null;
            }
        }
        println(parentQuestion)

        return if (tags == null || appUser == null) {
            QuestionFailed("error :)")
        } else {
            val question = Question(title = title, questionText = questionText,
                parentQuestion = parentQuestion, user = appUser)
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

}