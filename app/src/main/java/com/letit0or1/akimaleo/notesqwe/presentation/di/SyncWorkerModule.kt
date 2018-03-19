package com.letit0or1.akimaleo.notesqwe.presentation.di

import com.letit0or1.akimaleo.notesqwe.data.web.SyncWorkerImpl
import dagger.Module
import dagger.Provides

/**
 * Created by kawa on 19.03.2018.
 */
@Module
class SyncWorkerModule {

    @Provides
    fun provideSyncWorker(uuid: String) = SyncWorkerImpl(uuid)
}