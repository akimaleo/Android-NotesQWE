package com.letit0or1.akimaleo.notesqwe.presentation.di

import com.letit0or1.akimaleo.notesqwe.presentation.common.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app
}