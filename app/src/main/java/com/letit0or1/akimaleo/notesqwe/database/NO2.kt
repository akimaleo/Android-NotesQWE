package com.letit0or1.akimaleo.notesqwe.database

import android.content.Context
import org.dizitart.no2.Nitrite

/**
 * Created by akimaleo on 16.08.17.
 */

internal class NO2 private constructor() {

    init {
        val db = Nitrite.builder()
                .compressed()
                .filePath(Config.getConfigValue(context,""))
                .openOrCreate("user", "password")
    }

    private object Holder {
        val INSTANCE = NO2()
    }

    companion object {
        val instance: NO2 by lazy { Holder.INSTANCE }
    }

    private lateinit var context: Context
        set
    private lateinit var db: Nitrite
        get
}

