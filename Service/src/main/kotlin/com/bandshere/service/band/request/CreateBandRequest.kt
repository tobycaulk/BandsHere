package com.bandshere.service.band.request

import com.bandshere.service.band.*

data class CreateBandRequest(
        val bandInfo: BandInfo? = null,
        val userEmail: String = "",
        val imageComponent: BandImageComponent? = null,
        val aboutComponent: BandAboutComponent? = null,
        val youtubeComponent: BandYoutubeComponent? = null,
        val socialComponents: List<BandSocialComponent> = listOf()
)