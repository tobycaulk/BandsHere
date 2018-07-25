package com.bandshere.service.band

import org.springframework.data.repository.CrudRepository

interface BandRepository: CrudRepository<Band, Int> {
    fun findOneByBandId(bandId: String): Band?
}