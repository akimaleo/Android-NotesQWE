package com.letit0or1.akimaleo.notesqwe.util.database

import org.dizitart.no2.objects.Id
import java.io.Serializable

/**
 * Created by akimaleo on 16.08.17.
 */
data class Note(@Id var uid: String, var text: String, var editDate: Long) : Serializable, Comparable<Note> {
    override fun compareTo(other: Note): Int {
        return if (editDate > other.editDate) 1 else -1
    }

    constructor() : this("", "", 0)
}