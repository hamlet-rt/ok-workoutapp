package com.github.hamlet_rt.workoutapp.backend.repository.inmemory

import com.github.hamlet_rt.workoutapp.common.models.WrkTngType
import com.github.hamlet_rt.workoutapp.common.repo.*
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub

class TngRepoStub() : ITngRepository {
    override suspend fun createTng(rq: DbTngRequest): DbTngResponse {
        return DbTngResponse(
            data = WrkTngStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readTng(rq: DbTngIdRequest): DbTngResponse {
        return DbTngResponse(
            data = WrkTngStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateTng(rq: DbTngRequest): DbTngResponse {
        return DbTngResponse(
            data = WrkTngStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse {
        return DbTngResponse(
            data = WrkTngStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse {
        return DbTngsResponse(
            data = WrkTngStub.prepareSearchList(filter = "", WrkTngType.POWER),
            isSuccess = true,
        )
    }
}
