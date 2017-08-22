package com.letit0or1.akimaleo.notesqwe.util.webdata

import com.letit0or1.akimaleo.notesqwe.Note

/**
 * Created by akimaleo on 22.08.17.
 */
interface SyncHandler {
    fun success(list: ArrayList<Note>)
    fun error(exception: Exception)
}