package com.sorsix.backendapplication.domain

import com.sorsix.backendapplication.domain.enum.AppUserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "app_users")
data class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "username")
    private val username: String,
    val name: String,
    val surname: String,
    val email: String,

    @Column(name = "password")
    private val password: String,
    @Enumerated(value = EnumType.STRING)
    val appUserRole: AppUserRole,
    @Column(name = "link_img")
    val link_img : String
) : UserDetails {
    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority(appUserRole.name))

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
