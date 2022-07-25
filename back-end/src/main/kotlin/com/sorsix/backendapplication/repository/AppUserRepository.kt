package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.enum.AppUserRole
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {

    fun findAppUserByEmail(email: String): AppUser?

    fun findByUsername(username: String): AppUser?

    fun findAppUserByUsername(username: String): AppUser?


    fun findAllByAppUserRole(pageable: Pageable, appUserRole: AppUserRole): Page<AppUser>

//    @Modifying
//    @Query(value = "update AppUser a set a.appUserRole = :userRole where a.id = :userId")
//    fun updateAppUserRole(userId: Long, userRole: AppUserRole): Int
//
//    @Query(
//        value = "select au from AppUser au where lower(concat(au.firstName, ' ', au.lastName, '-', au.id)) like %:query%"
//    )
//    fun searchAppUserByFirstNameOrLastName(query: String): List<AppUser>
//
//    @Modifying
//    @Query(value = "update AppUser a set a.profilePhoto = :profilePhoto where a.id = :userId")
//    fun updateProfilePhoto(userId: Long, profilePhoto: String): Int

}