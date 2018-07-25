package com.bandshere.service.band

import org.springframework.data.repository.CrudRepository

interface BandInfoRepository: CrudRepository<BandInfo, Int> {
    fun findOneByUsername(username: String): BandInfo?
}