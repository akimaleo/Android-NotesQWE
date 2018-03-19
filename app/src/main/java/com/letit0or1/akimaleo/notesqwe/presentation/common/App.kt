package com.letit0or1.akimaleo.notesqwe.presentation.common

import android.support.multidex.MultiDexApplication
import com.letit0or1.akimaleo.notesqwe.presentation.di.AppComponent
import com.letit0or1.akimaleo.notesqwe.presentation.di.AppModule
import com.letit0or1.akimaleo.notesqwe.presentation.di.DaggerAppComponent
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes


/**
 * Created by akimaleo on 16.08.17.
 */

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        NO2Notes.instance.context = applicationContext
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}
