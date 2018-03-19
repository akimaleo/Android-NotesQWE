package com.letit0or1.akimaleo.notesqwe.presentation.di

import com.letit0or1.akimaleo.notesqwe.presentation.common.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: App)
}