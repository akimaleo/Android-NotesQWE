package com.letit0or1.akimaleo.notesqwe.util.database

import android.content.Context
import com.letit0or1.akimaleo.notesqwe.util.Config
import org.dizitart.no2.Nitrite

/**
 * Created by akimaleo on 16.08.17.
 */

internal class NO2 private constructor() {

    private object Holder {
        val INSTANCE = NO2()
    }

    companion object {
        val instance: NO2 by lazy { Holder.INSTANCE }
    }

    var context: Context? = null
        set(value) {
            db = Nitrite.builder()
                    .compressed()
                    .filePath(value!!.getFilesDir().getPath() + Config.databaseName(value) + ".db")
                    .openOrCreate(Config.databaseLogin(value), Config.databasePassword(value))
            field = value
        }

    lateinit var db: Nitrite
        get

    init {

    }
}
