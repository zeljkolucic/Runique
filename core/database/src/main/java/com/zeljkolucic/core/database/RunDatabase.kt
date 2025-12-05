package com.zeljkolucic.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeljkolucic.core.database.dao.RunDao
import com.zeljkolucic.core.database.dao.RunPendingSyncDao
import com.zeljkolucic.core.database.entitiy.DeletedRunSyncEntity
import com.zeljkolucic.core.database.entitiy.RunEntity
import com.zeljkolucic.core.database.entitiy.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
    ],
    version = 1
)
abstract class RunDatabase: RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}