package com.letit0or1.akimaleo.notesqwe.data.web

import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import io.reactivex.Observable

/**
 * Created by kawa on 18.03.2018.
 * Remote Firebase database common action
 */

interface FirebaseDao {
    /**
     * Add only item
     */
    fun add(note: Note)

    /**
     * Set all items for user notes list
     */
    fun setAll(notes: List<Note>)

    /**
     * Remove only item from remote DB
     * Uses
     * @see Note
     */
    fun deleteItem(note: Note)

    /**
     * Retrieving all data from remote DB for current user
     */
    fun getAll(): Observable<List<Note>>
}