package com.letit0or1.akimaleo.notesqwe.view.authorization

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import kotlinx.android.synthetic.main.activity_authorization.*


class AuthorizationActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        mAuth = FirebaseUtil.instance.firebaseAuth
//        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//            val user = firebaseAuth.currentUser
//            if (user != null) {
//                // User is signed in
////                Log.d(FragmentActivity.TAG, "onAuthStateChanged:signed_in:" + user.uid)
//            } else {
//                // User is signed out
////                Log.d(FragmentActivity.TAG, "onAuthStateChanged:signed_out")
//            }
//        }
//        FirebaseUtil.instance.firebase.addAuthStateListener(mAuthListener)
    }

    public fun signIn(view: View) {
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        if (!emailStr.matches(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))) {
            email.error = getString(R.string.email_is_incorrect)
        } else if (passwordStr.isEmpty()) {
            email.error = getString(R.string.value_cant_by_blank)
        } else {
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.e("FIREBASE", "sight in success")
                            successLogin()
                        } else {
                            if (!task.isSuccessful) {
                                Log.e("FIREBASE", "sight in error")
                                try {
                                    throw task.getException()!!
                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    invalidCredentials()
                                } catch (e: FirebaseAuthInvalidUserException) {
                                    reqRegister()
                                }
                            }
                        }
                    }
        }
    }

    private fun signUp() {
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (!task.isSuccessful) {
                        try {
                            throw task.getException()!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            password.setError(getString(R.string.error_weak_password))
                            password.requestFocus()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            email.setError(getString(R.string.error_invalid_email))
                            email.requestFocus()
                        } catch (e: FirebaseAuthUserCollisionException) {
                            signUp()
                        } catch (e: Exception) {
                            Log.e("FIREBASE AUTH SIGN UP", e.message)
                        }
                    } else {
                        successLogin()
                    }
                }
    }

    private fun reqRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.req_register_new_user)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                    signUp()
                })
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun invalidCredentials() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.invalid_credentials)
        builder.create().show()
    }

    private fun successLogin() {
        setResult(Activity.RESULT_OK)
        finish()
    }
//
//    public override fun onStart() {
//        super.onStart()
//        mAuth.addAuthStateListener(mAuthListener)
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        mAuth.removeAuthStateListener(mAuthListener)
//    }
}
