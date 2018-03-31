package com.letit0or1.akimaleo.notesqwe.di

import com.letit0or1.akimaleo.notesqwe.view.view.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by kawa on 24.03.2018.
 */
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(FirebaseDaoModule::class))
    internal abstract fun bindMainActivity(): BaseActivity

}