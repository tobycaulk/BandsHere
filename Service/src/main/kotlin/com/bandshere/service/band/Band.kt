package com.bandshere.service.band

import com.bandshere.service.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

enum class BandSocialType {
    FACEBOOK,
    INSTAGRAM,
    YOUTUBE,
    TWITTER,
    SPOTIFY
}

@Entity
@Table(name="band")
@EntityListeners(AuditingEntityListener::class)
data class Band(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(unique=true)
        val bandId: String = "",

        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="band_info_id")
        var bandInfo: BandInfo? = null,

        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="band_image_component_id")
        var imageComponent: BandImageComponent? = null,

        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="band_about_component_id")
        var aboutComponent: BandAboutComponent? = null,

        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="band_youtube_component_id")
        var youtubeComponent: BandYoutubeComponent? = null,

        @ManyToMany(cascade=[CascadeType.ALL])
        @JoinTable(name="band_social_components", joinColumns=[JoinColumn(name="band_id", referencedColumnName="id")], inverseJoinColumns=[JoinColumn(name="band_social_component_id", referencedColumnName="id")])
        var socialComponents: List<BandSocialComponent> = listOf(),

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        val creationDate: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        val modifiedDate: Date? = null,

        @OneToOne(cascade=[CascadeType.ALL])
        @JoinColumn(name="user_id")
        var user: User? = null,

        @JsonIgnore
        @ManyToMany(mappedBy="follows")
        var userFollows: List<User> = listOf()
)

@Entity
@Table(name="bandInfo")
data class BandInfo(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int = 0,

        @JsonIgnore
        @OneToOne(mappedBy="bandInfo")
        val band: Band? = null,

        var name: String = "",

        @Column(unique=true)
        var username: String = ""
)

@Entity
@Table(name="bandImageComponent")
data class BandImageComponent(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int = 0,

        var image: ByteArray? = null,

        @JsonIgnore
        @OneToOne
        val band: Band? = null
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as BandImageComponent

                if (id != other.id) return false
                if (!Arrays.equals(image, other.image)) return false
                if (band != other.band) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id
                result = 31 * result + (image?.let { Arrays.hashCode(it) } ?: 0)
                result = 31 * result + (band?.hashCode() ?: 0)
                return result
        }
}

@Entity
@Table(name="bandSocialComponent")
data class BandSocialComponent(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int = 0,

        @Enumerated(EnumType.STRING)
        var type: BandSocialType? = null,

        var link: String = "",

        @JsonIgnore
        @ManyToMany(mappedBy="socialComponents")
        val bands: List<Band> = listOf()
)

@Entity
@Table(name="bandAboutComponent")
data class BandAboutComponent(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int = 0,

        @JsonIgnore
        @OneToOne(mappedBy="aboutComponent")
        val band: Band? = null,

        @Column(length=5000)
        var description: String = ""
)

@Entity
@Table(name="bandYoutubeComponent")
data class BandYoutubeComponent(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int = 0,

        @JsonIgnore
        @OneToOne(mappedBy="youtubeComponent")
        val band: Band? = null,

        var link: String = ""
)