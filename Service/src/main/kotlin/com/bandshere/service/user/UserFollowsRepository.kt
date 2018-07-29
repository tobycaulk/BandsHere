package com.bandshere.service.user

import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Component
class UserFollowsRepository(private val entityManager: EntityManager) {

    fun getFollowerCountByBandId(bandId: Int): Int {
        val sql = "SELECT * FROM user_follows as uf WHERE uf.band_id=$bandId"
        val query = entityManager.createNativeQuery(sql)
        val resultSet = query.resultList

        return resultSet.size
    }

    @Transactional
    fun saveFollow(userId: Int, bandId: Int) {
        val sql = "INSERT INTO user_follows VALUES($userId, $bandId)"
        val query = entityManager.createNativeQuery(sql)
        entityManager.joinTransaction()
        query.executeUpdate()
    }

    @Transactional
    fun removeFollow(userId: Int, bandId: Int) {
        val sql = "DELETE FROM user_follows WHERE user_id=$userId AND band_id=$bandId"
        val query = entityManager.createNativeQuery(sql)
        entityManager.joinTransaction()
        query.executeUpdate()
    }
}