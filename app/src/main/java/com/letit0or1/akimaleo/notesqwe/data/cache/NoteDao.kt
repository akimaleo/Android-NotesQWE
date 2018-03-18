package com.letit0or1.akimaleo.notesqwe.data.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by kawa on 18.03.2018.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM `note`")
    fun getAll(): Flowable<List<Note>>

    @Insert
    fun insertAll(vararg note: Note)

    @Insert
    fun insertAll(notes: Iterable<Note>)
}