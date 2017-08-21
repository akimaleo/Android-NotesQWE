package com.letit0or1.akimaleo.notesqwe.view.authorization

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.UserDataUtil
import kotlinx.android.synthetic.main.activity_authorization.*


class AuthorizationActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        mAuth = UserDataUtil.instance.firebase
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
//                Log.d(FragmentActivity.TAG, "onAuthStateChanged:signed_in:" + user.uid)
            } else {
                // User is signed out
//                Log.d(FragmentActivity.TAG, "onAuthStateChanged:signed_out")
            }
        }
        UserDataUtil.instance.firebase.addAuthStateListener(mAuthListener)
    }

    public fun signIn(view: View) {
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        if (emailStr.isEmpty()) email.setError("This field can not be blank");
        else if (passwordStr.isEmpty()) password.setError("This field can not be blank");
        else
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            successLogin()
                            Log.e("FIREBASE", "sight in success")
                        } else {
                            signUp()
                            Log.e("FIREBASE", "sight in error")
                        }
                    }
    }

    private fun signUp() {
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        UserDataUtil.instance.firebase.createUserWithEmailAndPassword(emailStr, passwordStr)
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

    private fun successLogin() {
        finish()
    }

    public override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    public override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
}
