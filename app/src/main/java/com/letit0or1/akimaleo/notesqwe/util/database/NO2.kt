package com.letit0or1.akimaleo.notesqwe.util.database

import android.content.Context
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.util.Config
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectFilter
import org.dizitart.no2.objects.ObjectRepository

/**
 * Created by akimaleo on 16.08.17.
 */

internal class NO2 private constructor() {

    private object Holder {
        val INSTANCE = NO2()
    }

    companion object {
        val instance: NO2 by lazy { Holder.INSTANCE }
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
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
        val dd = ArrayList<Note>()
        repository.find().forEach {
            dd.add(it)
        }
        return dd
    }

    fun saveItem(note: Note) {
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
        repository.insert(note)
    }

    fun updateItem(note: Note) {
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
        repository.update(note)
    }
    fun getItem(uid: String) {
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
//        repository.find(ObjectFilter())
    }

    init {

    }
}

