package com.letit0or1.akimaleo.notesqwe.util.database

import android.content.Context
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.util.Config
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters

/**
 * Created by akimaleo on 16.08.17.
 */

internal class NO2Notes private constructor() {

    //FOR UNIT TEST
    constructor(context: Context) : this() {
        db = Nitrite.builder()
                .compressed()
                .filePath(context.getFilesDir().getPath() + "test" + ".db")
                .openOrCreate("test", "test")
    }

    private object Holder {
        val instance = NO2Notes()
    }

    companion object {
        val instance: NO2Notes by lazy { Holder.instance }
    }

    var context: Context? = null
        set(value) {
            db = Nitrite.builder()
                    .compressed()
                    .filePath(value!!.getFilesDir().getPath() + Config.databaseName(value) + ".db")
                    .openOrCreate(Config.databaseLogin(value), Config.databasePassword(value))
            field = value
        }

    lateinit var db: Nitrite
        get

    fun getAllNotes(): ArrayList<Note> {
        val repository: ObjectRepository<Note> = db.getRepository(Note::class.java)
        val dd = ArrayList<Note>()
        repository.find().forEach {
            dd.add(it)
        }
        return dd
    }

    fun clearDb() {
        val repository: ObjectRepository<Note> = db.getRepository(Note::class.java)
        repository.remove(ObjectFilters.ALL)
    }

    fun save(list: ArrayList<Note>?) {
        val repository: ObjectRepository<Note> = db.getRepository(Note::class.java)

        if (list == null || list.isEmpty()) {
            clearDb()
        } else {
            repository.insert(list.toArray(Array<Note>(list.size, { i -> list.get(i) })))
        }
    }

    fun save(note: Note) {
        updateOrInsert(note)
    }

    fun updateOrInsert(note: Note) {
        val repository: ObjectRepository<Note> = db.getRepository(Note::class.java)
        repository.update(note, true)
    }

    fun getItem(uid: String): Note? {
        for (note in NO2Notes.instance.getAllNotes()) {
            if (note.uid == uid)
                return note
        }
        return null
    }

    init {

    }
}

