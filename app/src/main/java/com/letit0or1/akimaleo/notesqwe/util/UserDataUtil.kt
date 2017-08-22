package com.letit0or1.akimaleo.notesqwe.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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

    var firebaseAuth: FirebaseAuth
        get
    var firebaseDatabase: FirebaseDatabase
        get


    init {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

}
