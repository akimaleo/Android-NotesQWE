package com.letit0or1.akimaleo.notesqwe.view.authorization

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.view.CActivity
import kotlinx.android.synthetic.main.activity_authorization.*


class AuthorizationActivity : CActivity(), AuthorizationView {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var presenter: AuthorizationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        presenter = AuthorizationPresenterImpl(this)
    }

    public fun signIn(view: View) {
        presenter.login(email.text.toString(), password.text.toString())
    }


    override fun reqRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.req_register_new_user)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                    presenter.register(email.text.toString(), password.text.toString())
                })
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }


    override fun loginSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun invalidCredentials() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.invalid_credentials)
        builder.create().show()
    }

    override fun emailError(error: String) {
        email.error = error
    }

    override fun passwordError(error: String) {
        password.error = error
    }

}
