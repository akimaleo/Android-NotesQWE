package com.letit0or1.akimaleo.notesqwe.view.user.authorization

/**
 * Created by akimaleo on 23.08.17.
 */

interface AuthorizationPresenter {
    fun login(email: String, password: String)
    fun register(email: String, password: String)
    fun restore(email: String)
}
