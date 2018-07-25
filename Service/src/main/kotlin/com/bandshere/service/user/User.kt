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

        var password: String = "",

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        val creationDate: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        val modifiedDate: Date? = null,

        @OneToOne(mappedBy="user")
        @JsonIgnore
        val band: Band? = null
)