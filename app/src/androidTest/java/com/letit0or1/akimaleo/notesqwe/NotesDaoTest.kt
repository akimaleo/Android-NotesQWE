package com.letit0or1.akimaleo.notesqwe

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.letit0or1.akimaleo.notesqwe.data.cache.AppDatabase
import org.junit.Before
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import com.letit0or1.akimaleo.notesqwe.data.web.SyncWorkerImpl.Companion.mergeNotes
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.util.*


/**
 * Created by kawa on 19.03.2018.
 */

@RunWith(AndroidJUnit4::class)
class NotesDaoTest {

    lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                AppDatabase::class.java,
                "test_name")
                .allowMainThreadQueries()
                .build()
    }

    @Test
    fun testWriteRead() {
        val originData: ArrayList<Note> = arrayListOf(
                Note(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Calendar.getInstance().timeInMillis),
                Note(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Calendar.getInstance().timeInMillis),
                Note(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Calendar.getInstance().timeInMillis),
                Note(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Calendar.getInstance().timeInMillis)
        )
        db.noteDao().insertAll(originData)
        val resultData = db.noteDao().getAll().blockingFirst()
        assertTrue(resultData == originData)
    }

    @Test
    @Throws(Exception::class)
    fun merge() {
        val first = arrayOf(
                Note("1", "DO", Calendar.getInstance().time.time),
                Note("2", "NOT", Calendar.getInstance().time.time),
                Note("3", "DO", Calendar.getInstance().time.time),
                Note("4", "NOT", Calendar.getInstance().time.time)
        ).toCollection(ArrayList())

        val second = arrayOf(
                Note("5", "DO", Calendar.getInstance().time.time),
                Note("2", "DO", Calendar.getInstance().time.time),
                Note("6", "DO", Calendar.getInstance().time.time),
                Note("4", "DO", Calendar.getInstance().time.time)
        ).toCollection(ArrayList())

        val newList = mergeNotes(first, second)
        for (item in newList) {
            Assert.assertEquals("DO", item.text)
        }
    }

    @Test
    @After
    fun closeDb() {
        db.noteDao().dropTable()
        if (!db.isOpen)
            db.close()
    }
}