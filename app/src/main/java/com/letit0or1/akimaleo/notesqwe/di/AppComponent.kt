package com.letit0or1.akimaleo.notesqwe.di

import android.app.Application
import com.letit0or1.akimaleo.notesqwe.presentation.common.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}