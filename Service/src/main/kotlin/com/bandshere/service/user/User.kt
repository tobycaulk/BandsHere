package com.bandshere.service.user

import com.bandshere.service.band.Band
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date
import javax.persistence.*

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener::class)
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(unique=true)
        val userId: String = "",

        @Column(unique=true)
        var email: String = "",

        @Column(unique=true)
        var username: String = "",

        @JsonIgnore
        var password: String = "",

        @ManyToMany(cascade=[CascadeType.ALL])
        @JoinTable(name="user_follows", joinColumns=[JoinColumn(name="user_id", referencedColumnName="id")], inverseJoinColumns=[JoinColumn(name="band_id", referencedColumnName="id")])
        var follows: List<Band> = listOf(),

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        val creationDate: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedDate: Date? = null,

        @OneToOne(mappedBy="user")
        @JsonIgnore
        val band: Band? = null,

        @JsonIgnore
        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="user_session_id")
        var session: UserSession? = null
)

@Entity
@Table(name="userSession")
@EntityListeners(AuditingEntityListener::class)
data class UserSession(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(unique=true)
        val sessionId: String = "",

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        val creationDate: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedDate: Date? = null,

        @OneToOne(mappedBy="session")
        var user: User? = null
)