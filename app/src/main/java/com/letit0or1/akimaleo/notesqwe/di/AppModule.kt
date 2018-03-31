package com.letit0or1.akimaleo.notesqwe.di

import android.app.Application
import android.content.Context
import com.letit0or1.akimaleo.notesqwe.presentation.common.App
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun provideContext(application: Application): Context

}