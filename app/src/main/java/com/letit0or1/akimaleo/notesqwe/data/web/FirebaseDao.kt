package com.letit0or1.akimaleo.notesqwe.data.web

import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by kawa on 18.03.2018.
 * Remote Firebase database common action
 */

interface FirebaseDao {
    /**
     * Add only item
     */
    @Inject
    fun add(note: Note)

    /**
     * Set all items for user notes list
     */
    @Inject
    fun setAll(notes: List<Note>)

    /**
     * Remove only item from remote DB
     * Uses
     * @see Note
     */
    @Inject
    fun deleteItem(note: Note)

    /**
     * Retrieving all data from remote DB for current user
     */
    @Inject
    fun getAll(): Observable<List<Note>>
}