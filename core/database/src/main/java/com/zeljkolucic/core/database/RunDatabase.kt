package com.zeljkolucic.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeljkolucic.core.database.dao.RunDao
import com.zeljkolucic.core.database.entitiy.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
abstract class RunDatabase: RoomDatabase() {
    abstract val runDao: RunDao
}