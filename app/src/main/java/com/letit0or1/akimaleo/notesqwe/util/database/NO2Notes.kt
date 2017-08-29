package com.letit0or1.akimaleo.notesqwe.util.database

import android.content.Context
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.util.Config
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import org.dizitart.no2.tool.Exporter
import org.dizitart.no2.tool.Importer
import java.io.File


/**
 * Created by akimaleo on 16.08.17.
 */

internal class NO2Notes private constructor() {

    lateinit var repository: ObjectRepository<Note>

    init {
    }

    //FOR UNIT TEST
    constructor(context: Context) : this() {
        db = Nitrite.builder()
                .compressed()
                .filePath(context.getFilesDir().getPath() + "test" + ".db")
                .openOrCreate("test", "test")
        repository = db.getRepository(Note::class.java)
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
            repository = db.getRepository(Note::class.java)
            field = value
        }

    lateinit var db: Nitrite
        get

    fun getAllNotes(): ArrayList<Note> {
        val dd = ArrayList<Note>()
        repository.find().forEach {
            dd.add(it)
        }
        return dd
    }

    fun clearDb() {
        repository.remove(ObjectFilters.ALL)
    }

    fun clearAndSave(list: ArrayList<Note>?) {
        clearDb()

        if (list != null) {
            for (note in list) {
                updateOrInsert(note)
            }
        }
    }

    fun save(note: Note) {
        updateOrInsert(note)
    }

    fun updateOrInsert(note: Note) {
        repository.update(note, true)
    }

    fun getItem(uid: String): Note? {
        for (note in NO2Notes.instance.getAllNotes()) {
            if (note.uid == uid)
                return note
        }
        return null
    }

    fun exportSchema(file: File) {
        // Export data to a file
        val exporter = Exporter.of(db)
        exporter.exportTo(file)

    }

    fun importSchema(file: File) {
        //Import data from the file
        val importer = Importer.of(db)
        importer.importFrom(file)
    }
}

