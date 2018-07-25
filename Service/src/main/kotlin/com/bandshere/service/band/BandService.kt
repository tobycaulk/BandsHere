package com.bandshere.service.band

import com.bandshere.service.band.request.CreateBandRequest
import com.bandshere.service.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BandService(
        private val bandRepository: BandRepository,
        private val userRepository: UserRepository,
        private val bandInfoRepository: BandInfoRepository
) {
    fun create(request: CreateBandRequest): Band? {
        var user = userRepository.findOneByEmail(request.userEmail)
        if(user != null) {
            return bandRepository.save(Band(
                    bandId = UUID.randomUUID().toString(),
                    bandInfo = request.bandInfo,
                    user = user,
                    imageComponent = request.imageComponent,
                    aboutComponent = request.aboutComponent,
                    youtubeComponent = request.youtubeComponent,
                    socialComponents = request.socialComponents))
        } else {
            throw Exception("User email not registered ${request.userEmail}")
        }
    }

    fun get(username: String): Band {
        var bandInfo = bandInfoRepository.findOneByUsername(username)
        if(bandInfo != null) {
            return bandInfo.band!!
        } else {
            throw Exception("Band username not registered $username")
        }
    }
}