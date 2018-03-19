package com.letit0or1.akimaleo.notesqwe.presentation.di

import com.letit0or1.akimaleo.notesqwe.data.web.FirebaseDaoImpl
import dagger.Module
import dagger.Provides

/**
 * Created by kawa on 19.03.2018.
 */
@Module
class FirebaseDaoModule {

    @Provides
    fun provideSyncWorker(uuid: String) = FirebaseDaoImpl(uuid)
}