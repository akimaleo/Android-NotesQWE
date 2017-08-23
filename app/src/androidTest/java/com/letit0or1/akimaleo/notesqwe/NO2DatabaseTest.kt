package com.letit0or1.akimaleo.notesqwe

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class NO2DatabaseTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        //WRITE READ 1 ITEM
        testWriteUpdateReadNote(appContext)

    }

    private fun testWriteUpdateReadNote(appContext: Context) {
        val db = NO2Notes(appContext)

        //CLEAR OLD DATA
        db.clearDb()

        //CREATE AND WRITE
        val noteBeforeSave = Note(UUID.randomUUID().toString(), "SAMPLE TEXT", Calendar.getInstance().time)
        db.save(noteBeforeSave)
        //READ
        var noteAfterSave: Note = db.getAllNotes().get(0)

        //COMPARE
        assertEquals(noteBeforeSave, noteAfterSave)


        //CREATE AND UPDATE
        val noteBeforeUpdate = noteAfterSave.copy()
        noteBeforeUpdate.text = "ANOTHER SAMPLE TEXT"
        db.updateOrInsert(noteBeforeUpdate)
        //READ
        val noteAfterUpdate = db.getAllNotes().get(0)

        //COMPARE
        assertEquals(noteBeforeUpdate, noteAfterUpdate)

    }
}
