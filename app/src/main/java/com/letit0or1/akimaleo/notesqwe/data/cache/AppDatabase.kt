package com.letit0or1.akimaleo.notesqwe.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by kawa on 18.03.2018.
 */

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}