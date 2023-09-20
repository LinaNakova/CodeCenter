package com.sorsix.backendapplication.repositoryTests

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.LikeUnlike
import com.sorsix.backendapplication.domain.Question
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.repository.LikeUnlikeRepository
import com.sorsix.backendapplication.repository.QuestionRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.sql.Timestamp
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.assertEquals

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = ["com.sorsix.backendapplication"])
@EntityScan(basePackages = ["com.sorsix.backendapplication"])
class LikeUnlikeRepositoryTest {
    @Autowired
    private lateinit var likeUnlikeRepository: LikeUnlikeRepository

    @Autowired
    private lateinit var userRepository: AppUserRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private var user: AppUser? = null
    private var question: Question? = null
    private var parentQuestion: Question? = null
    private var likeUnlike: LikeUnlike? = null

    @BeforeEach
    fun initDataForTests() {
        user = userRepository.findByUsername("test")
        parentQuestion = questionRepository.findById(1).get()
        question = Question(
            title = "Test Question",
            questionText = "Mock Question",
            parentQuestion = parentQuestion,
            views = 0,
            date = Timestamp.valueOf("2023-08-11 11:30:13.416664"),
            user = user
        )
        questionRepository.saveAndFlush(question!!)
        likeUnlike = LikeUnlike(
            appUser = user!!,
            question = question!!,
            likeUnlike = true
        )
        likeUnlikeRepository.saveAndFlush(likeUnlike!!)
    }

    @Test
    fun findByAppUserAndQuestionTest() {
        assertEquals(likeUnlikeRepository.findByAppUserAndQuestion(user!!, question!!), likeUnlike!!)
    }

    @Test
    fun changeLikeTest(){
        likeUnlikeRepository.changeLike(likeUnlike!!.id, !likeUnlike!!.likeUnlike)
        entityManager.flush()
        entityManager.clear()
        assertEquals(likeUnlikeRepository.findById(likeUnlike!!.id).get().likeUnlike, !likeUnlike!!.likeUnlike)
    }
}