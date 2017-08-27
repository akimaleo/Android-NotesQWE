package com.letit0or1.akimaleo.notesqwe.view.create

import android.os.Bundle
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.view.CActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : CActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val formaterDate: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formaterTime: SimpleDateFormat = SimpleDateFormat("HH:mm")

        val calDate = Calendar.getInstance().time
        date.text = formaterDate.format(calDate)
        time.text = formaterTime.format(calDate)
    }
}
