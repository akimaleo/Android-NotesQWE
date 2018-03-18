package com.letit0or1.akimaleo.notesqwe.data.cache

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by akimaleo on 16.08.17.
 */
@Entity(tableName = "note")
data class Note(
        @PrimaryKey
        var uid: String = "",

        @ColumnInfo(name = "text")
        var text: String = "",

        @ColumnInfo(name = "edit_date")
        var editDate: Long = 0L) : Serializable, Comparable<Note> {

    override fun compareTo(other: Note): Int {
        return if (editDate > other.editDate) 1 else -1
    }
}