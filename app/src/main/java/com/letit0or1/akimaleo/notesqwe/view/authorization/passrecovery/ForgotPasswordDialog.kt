package com.letit0or1.akimaleo.notesqwe.view.authorization.passrecovery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.authorization.AuthorizationPresenter
import kotlinx.android.synthetic.main.dialog_password_recovery.*

/**
 * Created by akimaleo on 02.09.17.
 */

class ForgotPasswordDialog(context: Context, private var presenter: AuthorizationPresenter) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_password_recovery)
        val email = findViewById(R.id.email) as EditText

        submit_action.setOnClickListener { presenter.restore(email.text.toString()) }
        cancel_action.setOnClickListener { dismiss() }
    }

}
