package com.letit0or1.akimaleo.notesqwe.presentation.common

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes


/**
 * Created by akimaleo on 16.08.17.
 */

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        NO2Notes.instance.context = applicationContext
    }
}
