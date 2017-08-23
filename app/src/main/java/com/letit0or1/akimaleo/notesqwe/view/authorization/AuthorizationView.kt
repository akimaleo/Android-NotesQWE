package com.letit0or1.akimaleo.notesqwe.view.authorization

/**
 * Created by akimaleo on 23.08.17.
 */

interface AuthorizationView {
    fun reqRegister()
    fun loginSuccess()
    fun emailError(error: String)
    fun passwordError(error: String)
    fun invalidCredentials()
}
