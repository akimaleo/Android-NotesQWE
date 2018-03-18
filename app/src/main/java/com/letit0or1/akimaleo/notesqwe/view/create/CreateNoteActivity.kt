package com.letit0or1.akimaleo.notesqwe.view.create

import android.app.Activity
import android.os.Bundle
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.view.BaseActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : BaseActivity() {

    private lateinit var uid: String
    private lateinit var calDate: Date

    private lateinit var editNote: Note

    private val formatterDate: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val formatterTime: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        arrow_back.setOnClickListener { onBackPressed() }
        uid = if (intent.getStringExtra("uid") == null) "" else intent.getStringExtra("uid")

        calDate = if (uid.isEmpty()) {
            editNote = Note()
            Calendar.getInstance().time

        } else {

            editNote = NO2Notes.instance.getItem(uid)!!
            content.setText(editNote.text)
            Date(editNote.editDate)

        }

        date.text = formatterDate.format(calDate)
        time.text = formatterTime.format(calDate)

        //Cursor to end of the text
        content.setSelection(content.text.length)
    }

    override fun onBackPressed() {
        val contentText = content.text.toString().replace(" ", "", true).replace("\n", "", true).intern()

//DELETE IS EMPTY
        if (contentText.isEmpty()) {

            SyncWorker.instance.removeItem(uid)

//UPDATE OR CREATE
        } else {

            if (editNote.equals(Note(editNote.uid, content.text.toString(), calDate.time))) {
                SyncWorker.instance.putItem(Note(if (uid.isEmpty()) UUID.randomUUID().toString() else uid,
                        content.text.toString(),
                        calDate.time))
            } else {
                SyncWorker.instance.putItem(Note(if (uid.isEmpty()) UUID.randomUUID().toString() else uid,
                        content.text.toString(),
                        Calendar.getInstance().time.time))
            }

        }
        setResult(Activity.RESULT_OK)
        finish()
    }
}
