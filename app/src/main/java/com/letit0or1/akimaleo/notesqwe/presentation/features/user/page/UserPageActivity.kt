package com.letit0or1.akimaleo.notesqwe.view.user.page

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes
import kotlinx.android.synthetic.main.activity_user_page.*

class UserPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)

        email.text = FirebaseUtil.instance.firebaseAuth.currentUser!!.email
        symbols_count.text = "${NO2Notes.instance.getAllNotes().sumBy { it.text.replace('\n', ' ').length }}"
        words_count.text = "${NO2Notes.instance.getAllNotes().sumBy { countWords(it.text) }}"

        log_out.setOnClickListener {
            FirebaseUtil.instance.firebaseAuth.signOut()
            finish()
        }
    }

    fun countWords(s: String): Int {

        var wordCount = 0

        var word = false
        val endOfLine = s.length - 1

        for (i in 0 until s.length) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s[i]) && i != endOfLine) {
                word = true
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s[i]) && word) {
                wordCount++
                word = false
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s[i]) && i == endOfLine) {
                wordCount++
            }
        }
        return wordCount
    }
}
