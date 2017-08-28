package com.letit0or1.akimaleo.notesqwe.view.create

import android.os.Bundle
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.view.CActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : CActivity() {

    private lateinit var uid: String
    private lateinit var calDate: Date

    val formaterDate: SimpleDateFormat
    val formaterTime: SimpleDateFormat

    init {
        formaterDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formaterTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        uid = if (intent.getStringExtra("uid") == null) "" else intent.getStringExtra("uid")

        if (uid.isEmpty()) {
            calDate = Calendar.getInstance().time
        } else {

        }

        date.text = formaterDate.format(calDate)
        time.text = formaterTime.format(calDate)
    }

    override fun onBackPressed() {
        val contentText = content.text.toString().replace(" ", "", true).replace("\n", "", true).intern()

//DELETE IS EMPTY
        if (contentText.isEmpty()) {

            SyncWorker.instance.removeItem(uid)

//UPDATE OR CREATE
        } else {

            SyncWorker.instance.putItem(Note(if (uid.isEmpty()) UUID.randomUUID().toString() else uid,
                    contentText,
                    calDate))

        }
        super.onBackPressed()
    }
}
