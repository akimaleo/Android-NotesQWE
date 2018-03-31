package com.letit0or1.akimaleo.notesqwe.di

import com.letit0or1.akimaleo.notesqwe.data.web.FirebaseDaoImpl
import dagger.Component

/**
 * Created by kawa on 20.03.2018.
 */
@Component(modules = [FirebaseDaoModule::class])
interface FirebaseDaoComponent {
    fun getFirebaseDao(): FirebaseDaoImpl
}