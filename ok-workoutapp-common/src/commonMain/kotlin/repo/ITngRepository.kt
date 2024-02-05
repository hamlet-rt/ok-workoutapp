package com.github.hamlet_rt.workoutapp.common.repo

interface ITngRepository {
    suspend fun createTng(rq: DbTngRequest): DbTngResponse
    suspend fun readTng(rq: DbTngIdRequest): DbTngResponse
    suspend fun updateTng(rq: DbTngRequest): DbTngResponse
    suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse
    suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse
    companion object {
        val NONE = object : ITngRepository {
            override suspend fun createTng(rq: DbTngRequest): DbTngResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readTng(rq: DbTngIdRequest): DbTngResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateTng(rq: DbTngRequest): DbTngResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
