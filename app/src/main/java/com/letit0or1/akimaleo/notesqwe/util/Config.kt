package com.letit0or1.akimaleo.notesqwe.util

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.letit0or1.akimaleo.notesqwe.R
import java.io.IOException
import java.util.*

/**
 * Created by akimaleo on 16.08.17.
 */

object Config {

    private val TAG = "Config"

    fun getConfigValue(context: Context, name: String): String? {
        val resources = context.resources

        try {
            val rawResource = resources.openRawResource(R.raw.config)
            val properties = Properties()
            properties.load(rawResource)
            return properties.getProperty(name)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to open config file.")
        }

        return null
    }

    fun databaseLogin(context: Context): String? {
        return getConfigValue(context, "nitrate_user_id")
    }

    fun databasePassword(context: Context): String? {
        return getConfigValue(context, "nitrate_password")
    }

    fun databaseName(context: Context): String? {
        return getConfigValue(context, "nitrate_name")
    }
}
