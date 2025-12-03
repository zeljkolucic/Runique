package com.zeljkolucic.core.database

import android.database.sqlite.SQLiteFullException
import com.zeljkolucic.core.database.dao.RunDao
import com.zeljkolucic.core.database.mappers.toDomain
import com.zeljkolucic.core.database.mappers.toEntity
import com.zeljkolucic.core.domain.run.LocalRunDataSource
import com.zeljkolucic.core.domain.run.Run
import com.zeljkolucic.core.domain.run.RunId
import com.zeljkolucic.core.domain.util.DataError
import com.zeljkolucic.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
): LocalRunDataSource {
    override fun getRuns(): Flow<List<Run>> {
        return runDao
            .getRuns()
            .map { runEntities ->
                runEntities.map { it.toDomain() }
            }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch(e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map { it.toEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch(e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}