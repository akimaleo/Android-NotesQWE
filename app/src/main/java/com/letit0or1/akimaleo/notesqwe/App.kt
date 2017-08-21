package com.letit0or1.akimaleo.notesqwe

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.letit0or1.akimaleo.notesqwe.database.NO2
import com.letit0or1.akimaleo.notesqwe.view.UserDataUtil


/**
 * Created by akimaleo on 16.08.17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NO2.instance.context = applicationContext

    }
}
