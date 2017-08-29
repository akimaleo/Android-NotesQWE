package com.letit0or1.akimaleo.notesqwe.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger

/**
 * Created by akimaleo on 21.08.17.
 */

internal class FirebaseUtil private constructor() {

    private object Holder {
        val INSTANCE = FirebaseUtil()
    }

    companion object {
        val instance: FirebaseUtil by lazy { Holder.INSTANCE }
    }


    var firebaseAuth: FirebaseAuth
        get
    var firebaseDatabase: FirebaseDatabase
        get


    init {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setLogLevel(Logger.Level.DEBUG)
    }

}
