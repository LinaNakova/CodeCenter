package com.sorsix.backendapplication.repositoryTests

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.Question
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.repository.QuestionRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.sql.Timestamp
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = ["com.sorsix.backendapplication"])
@EntityScan(basePackages = ["com.sorsix.backendapplication"])
class QuestionRepositoryTest {

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var userRepository: AppUserRepository

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    private var appUser: AppUser? = null
    private var parentQuestion: Question? = null
    private var question: Question? = null

    @BeforeEach
    fun getAppUer() {
        appUser = userRepository.findById(5).get()
        parentQuestion = questionRepository.findById(1).get()
        question = Question(
            title = "Test Question",
            questionText = "Mock Question",
            parentQuestion = parentQuestion,
            views = 0,
            date = Timestamp.valueOf("2023-08-11 11:30:13.416664"),
            user = appUser
        )
        questionRepository.saveAndFlush(question!!)
    }

    @AfterEach
    fun deleteMockQuestion(){
        questionRepository.deleteById(question!!.id)

    }

    @Test
    fun findAllByParentQuestionTest() {
        val questions = questionRepository.findAllByParentQuestion(parentQuestion!!)
        assertEquals(questions.size, 5)
    }

    @Test
    @Transactional
    fun increaseViewsTest() {
        questionRepository.increaseViews(question!!.id)
        entityManager.flush()
        entityManager.clear()
        assertEquals(questionRepository.findById(question!!.id).get().views, 1)
    }
}