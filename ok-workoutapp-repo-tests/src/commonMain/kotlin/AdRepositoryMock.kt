package com.github.hamlet_rt.workoutapp.backend.repo.tests

import com.github.hamlet_rt.workoutapp.common.repo.*

class TngRepositoryMock(
    private val invokeCreateTng: (DbTngRequest) -> DbTngResponse = { DbTngResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadTng: (DbTngIdRequest) -> DbTngResponse = { DbTngResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateTng: (DbTngRequest) -> DbTngResponse = { DbTngResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteTng: (DbTngIdRequest) -> DbTngResponse = { DbTngResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchTng: (DbTngFilterRequest) -> DbTngsResponse = { DbTngsResponse.MOCK_SUCCESS_EMPTY },
): ITngRepository {
    override suspend fun createTng(rq: DbTngRequest): DbTngResponse {
        return invokeCreateTng(rq)
    }

    override suspend fun readTng(rq: DbTngIdRequest): DbTngResponse {
        return invokeReadTng(rq)
    }

    override suspend fun updateTng(rq: DbTngRequest): DbTngResponse {
        return invokeUpdateTng(rq)
    }

    override suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse {
        return invokeDeleteTng(rq)
    }

    override suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse {
        return invokeSearchTng(rq)
    }
}
