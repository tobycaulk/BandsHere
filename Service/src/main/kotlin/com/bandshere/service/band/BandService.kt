package com.bandshere.service.band

import com.bandshere.service.band.request.CreateBandRequest
import com.bandshere.service.common.InternalServerErrorException
import com.bandshere.service.common.InvalidSessionException
import com.bandshere.service.user.UserFollowsRepository
import com.bandshere.service.user.UserRepository
import com.bandshere.service.user.UserSessionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BandService(
        private val bandRepository: BandRepository,
        private val bandInfoRepository: BandInfoRepository,
        private val userFollowsRepository: UserFollowsRepository,
        private val userSessionRepository: UserSessionRepository
) {
    fun create(request: CreateBandRequest, sessionId: String): Band? {
        val session = userSessionRepository.findOneBySessionId(sessionId)
        session ?: throw InvalidSessionException()

        val user = session.user
        user ?: throw InternalServerErrorException()

        return bandRepository.save(Band(
                bandId = UUID.randomUUID().toString(),
                bandInfo = request.bandInfo,
                user = user,
                imageComponent = request.imageComponent,
                aboutComponent = request.aboutComponent,
                youtubeComponent = request.youtubeComponent,
                socialComponents = request.socialComponents))
    }

    fun get(username: String): Band {
        var bandInfo = bandInfoRepository.findOneByUsername(username)
        if(bandInfo != null) {
            return bandInfo.band!!
        } else {
            throw InternalServerErrorException("Username not registered $username")
        }
    }

    fun getFollowerCount(username: String): Int {
        val band = bandInfoRepository.findOneByUsername(username)
        band ?: throw InternalServerErrorException("Band name not registered $username")
        return userFollowsRepository.getFollowerCountByBandId(band.id)
    }
}