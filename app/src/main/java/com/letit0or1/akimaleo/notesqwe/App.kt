package com.letit0or1.akimaleo.notesqwe

import android.app.Application
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes


/**
 * Created by akimaleo on 16.08.17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NO2Notes.instance.context = applicationContext

    }
}
