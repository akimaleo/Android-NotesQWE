package com.letit0or1.akimaleo.notesqwe.data.web

import com.letit0or1.akimaleo.notesqwe.data.cache.Note

/**
 * Created by akimaleo on 22.08.17.
 */
interface SyncHandler {
    fun success(list: ArrayList<Note>)
    fun error(exception: Exception)
}