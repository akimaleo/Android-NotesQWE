package com.letit0or1.akimaleo.notesqwe.view.authorization

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil

/**
 * Created by akimaleo on 23.08.17.
 */

class AuthorizationPresenterImpl(var view: AuthorizationView) : AuthorizationPresenter {

    val mAuth: FirebaseAuth
    val context: Context

    init {
        mAuth = FirebaseUtil.instance.firebaseAuth
        context = view as Context
    }

    override fun login(email: String, password: String) {
        if (!email.matches(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))) {
            view.emailError(context.getString(R.string.email_is_incorrect))
        } else if (password.isEmpty()) {
            view.passwordError(context.getString(R.string.value_cant_by_blank))
        } else {
            view.showLoading()
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        view.hideLoading()
                        if (task.isSuccessful) {
                            Log.e("FIREBASE", "sight in success")
                            view.loginSuccess()
                        } else {
                            if (!task.isSuccessful) {
                                Log.e("FIREBASE", "sight in error")
                                try {
                                    throw task.getException()!!
                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    view.invalidCredentials()
                                } catch (e: FirebaseAuthInvalidUserException) {
                                    view.reqRegister()
                                }
                            }
                        }
                    }
        }
    }

    override fun register(email: String, password: String) {
        view.showLoading()
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    view.hideLoading()
                    if (!task.isSuccessful) {
                        try {
                            throw task.getException()!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            view.passwordError(context.getString(R.string.error_weak_password))
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            view.emailError(context.getString(R.string.error_invalid_email))
                        } catch (e: FirebaseAuthUserCollisionException) {

                        } catch (e: Exception) {
                            Log.e("FIREBASE AUTH SIGN UP", e.message)
                        }
                    } else {
                        view.loginSuccess()
                    }
                }
    }

    override fun restore(email: String) {
        view.showLoading()
        val restore = mAuth.sendPasswordResetEmail(email)

        restore.addOnSuccessListener {
            view.successRestore()
        }
        restore.addOnFailureListener {
            view.failureRestore()
        }
        restore.addOnCompleteListener {
            view.hideLoading()
        }

    }
}