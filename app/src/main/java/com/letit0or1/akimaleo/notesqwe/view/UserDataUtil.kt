package com.letit0or1.akimaleo.notesqwe.view

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by akimaleo on 21.08.17.
 */

internal class UserDataUtil private constructor() {

    private object Holder {
        val INSTANCE = UserDataUtil()
    }

    companion object {
        val instance: UserDataUtil by lazy { Holder.INSTANCE }
    }

    lateinit var firebase: FirebaseAuth
        get

    init {
        firebase = FirebaseAuth.getInstance()
    }

}
