package com.letit0or1.akimaleo.notesqwe.view.user.authorization

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.view.BaseActivity
import kotlinx.android.synthetic.main.activity_authorization.*


class AuthorizationActivity : BaseActivity(), AuthorizationView {

    private lateinit var presenter: AuthorizationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        presenter = AuthorizationPresenterImpl(this)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.login -> {
                presenter.login(email.text.toString(), password.text.toString())
            }
            R.id.restore_password -> {
                val ll = LinearLayout(this)
                val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                ll.layoutParams = lp
                val editText = EditText(this)
                editText.setHint(R.string.email)
                editText.inputType = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                editText.layoutParams = lp
                ll.addView(editText)

                val builder = AlertDialog.Builder(this)
                builder.setView(ll)
                builder.setMessage(R.string.enter_password_resote)
                        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                            presenter.restore(editText.text.toString())
                            dialog.dismiss()
                        })
                        .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id ->

                        })
                builder.create().show()
            }
        }
    }

    override fun reqRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.req_register_new_user)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                    presenter.register(email.text.toString(), password.text.toString())
                })
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id ->

                })
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

    override fun emailError(error: Int) {
        password.error = getString(error)
    }

    override fun passwordError(error: Int) {
        password.error = getString(error)
    }

    override fun failureRestore() {
        val builder = AlertDialog.Builder(this@AuthorizationActivity)
        builder.setMessage(R.string.error)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }

    override fun successRestore() {
        val builder = AlertDialog.Builder(this@AuthorizationActivity)
        builder.setMessage(R.string.email_was_sent)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }
}
