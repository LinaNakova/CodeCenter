package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.api.dto.LikeRequest
import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.repository.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant.now
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class QuestionService(
    val questionRepository: QuestionRepository,
    val tagRepository: TagRepository,
    val questionTagRepository: QuestionTagRepository,
    val appUserRepository: AppUserRepository,
    val likeUnlikeRepository: LikeUnlikeRepository
) {

    fun findAllQuestionsWithoutAnswers(): List<Question>? {
        return questionRepository.findAll().filter { it.parentQuestion == null }
    }

    fun findAll(): List<Question>? {
        return questionRepository.findAll().toList()
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

            val question = Question(
                title = title, questionText = questionText,
                parentQuestion = parentQuestion, user = appUser,
                views = 0, date = Timestamp.valueOf(LocalDateTime.now())
            )
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
            val question = Question(
                title = title, questionText = questionText,
                parentQuestion = parentQuestion, user = appUser,
                views = 0, date = Timestamp.valueOf(LocalDateTime.now())
            )
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

    fun getAllQuestionTags(id: Long): List<QuestionTag> {
        return questionTagRepository.findAll()
    }

    fun getQuestionTags(id: Long): List<QuestionTag> {
        return getAllQuestionTags(id).filter { it.question.id == id }
    }

    fun getSortedByTitle(): List<Question> {
        return this.questionRepository.findAll(Sort.by("title")).filter { it.parentQuestion == null }
    }

    fun getSortedByTitleDescending(): List<Question> {
        return this.questionRepository.findAll(Sort.by("title").descending()).filter { it.parentQuestion == null }
    }

    fun getSortedByViewsDescending(): List<Question> {
        return this.questionRepository.findAll(Sort.by("views").descending()).filter { it.parentQuestion == null }
    }

    fun getSortedByViewsAscending(): List<Question> {
        return this.questionRepository.findAll(Sort.by("views").ascending()).filter { it.parentQuestion == null }
    }

    fun getLikes(id: Long): Int {
        val (first, second) = this.likeUnlikeRepository.findAll()
            .filter { likeUnlike -> likeUnlike.question.id == id }
            .partition { likeUnlike -> likeUnlike.like_unlike }
        return first.count() - second.count();
    }

    @Transactional
    fun postLike(body: LikeRequest): LikeResult {
        val q = questionRepository.findByIdOrNull(body.question_id)
        val u = appUserRepository.findByIdOrNull(body.user_id)
        print(q)
        print(u)
        return if (q == null || u == null) {
            LikeFailed("error liking")
        } else {
            if (likeUnlikeRepository.findByAppUserAndQuestion(u, q) == null) {
                val entry = LikeUnlike(question = q, appUser = u, like_unlike = body.like)
                likeUnlikeRepository.save(entry)
                LikeCreated(like = entry);
            } else {
                val entry = likeUnlikeRepository.findByAppUserAndQuestion(u, q);
                this.likeUnlikeRepository.changeLike(entry!!.id, body.like)
                LikeCreated(like = entry);
            }
        }


    }

    @Transactional
    fun increaseViews(id: Long) {
        println("Inside backend service call\n")
        this.questionRepository.increaseViews(id)
    }

    fun getViews(id: Long): Int {
        return this.questionRepository.findByIdOrNull(id = id)!!.views
    }

    fun getQuestionsFromUser(id: Long): List<Question>? {
        return this.questionRepository.findAll()
            .filter { question -> question.user?.id == id }
            .filter { question -> question.parentQuestion?.id == null }
    }

    fun getAnswersFromUser(id: Long): List<Question>? {
        return this.questionRepository.findAll()
            .filter { question -> question.user?.id == id }
            .filter { question -> question.parentQuestion?.id !== null }
    }

    fun sortByLikes(id: Long): List<Question>? {
        return this.getAnswersForQuestion(id)?.sortedBy { answer -> this.getLikes(answer.id) }
    }

    fun sortByLikesDescending(id: Long): List<Question>? {
        return this.getAnswersForQuestion(id)?.sortedByDescending { answer -> this.getLikes(answer.id) }
    }

    fun sortByAnswersAscending(): List<Question>? {
        return this.questionRepository
            .findAll().filter { it.parentQuestion == null }
            .sortedBy { question -> this.getAnswersForQuestion(question.id)?.size }
    }

    fun sortByAnswersDescending(): List<Question>? {
        return this.questionRepository
            .findAll().filter { it.parentQuestion == null }
            .sortedByDescending { question -> this.getAnswersForQuestion(question.id)?.size }
    }

    fun sortByDateAscending(): List<Question>? {
        return this.questionRepository
            .findAll(Sort.by("date"))
            .filter { it.parentQuestion == null }

    }

    fun sortByDateDescending(): List<Question>? {
        return this.questionRepository
            .findAll(Sort.by("date").descending())
            .filter { it.parentQuestion == null }

    }
    fun checkIfLikedByUser(qid:Long, uid:Long): Boolean?
    {
        return this.likeUnlikeRepository.findAll()
            .filter { it.question.id == qid && it.appUser.id==uid }[0].like_unlike

    }
//     likeUnlikeRepository.findBy(
//            appUserService.findAppUserByIdOrNull(uid)!!,
//            findById(qid)!!
//        ).like_unlike

}