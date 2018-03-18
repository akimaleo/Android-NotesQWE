package com.letit0or1.akimaleo.notesqwe.data.web

import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import io.reactivex.Observable

/**
 * Created by kawa on 18.03.2018.
 */
interface SyncWorker {
    fun add(note: Note)

    fun setAll(notes: List<Note>)

    fun deleteItem(note: Note)

    fun getAll(): Observable<List<Note>>
}