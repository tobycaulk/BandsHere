package com.bandshere.service.user

import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class UserFollowsRepository(private val entityManager: EntityManager) {

    fun getFollowerCountByBandId(bandId: Int): Int {
        val sql = "SELECT * FROM user_follows as uf WHERE uf.band_id=$bandId"
        var query = entityManager.createNativeQuery(sql)
        var resultSet = query.resultList

        return resultSet.size
    }
}